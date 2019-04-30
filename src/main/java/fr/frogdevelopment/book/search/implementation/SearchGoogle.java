package fr.frogdevelopment.book.search.implementation;

import static java.lang.Math.max;
import static java.lang.Math.round;
import static java.lang.String.join;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume.VolumeInfo.IndustryIdentifiers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SearchGoogle {

    private final Books books;

    public SearchGoogle(Books books) {
        this.books = books;
    }

    public void call(List<String> languages, String title, String author, String publisher, String isbn) {
        try {
            var parameters = new ArrayList<String>();

            if (isNotBlank(title)) {
                parameters.add("intitle:" + title);
            }

            if (isNotBlank(author)) {
                parameters.add("inauthor:" + author);
            }

            if (isNotBlank(publisher)) {
                parameters.add("inpublisher:" + publisher);
            }

            if (!parameters.isEmpty()) {
                query(join("+", parameters), join(",", languages));
            } else {
                throw new IllegalArgumentException();
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void query(String query, String langRestrict) throws IOException {
        // Set query string and filter only Google eBooks.
        log.info("Query: [{}]", query);

        var volumesList = books.volumes().list(query);
        volumesList.setLangRestrict(langRestrict);
        volumesList.setMaxResults(40L);
        volumesList.setOrderBy("newest");
//        volumesList.setFields()

        // Execute the query.
        var volumes = volumesList.execute();

        // Output results.
        if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
            log.info("No matches found.");
            return;
        }

        for (var volume : volumes.getItems()) {
            var volumeInfo = volume.getVolumeInfo();
            log.info("==========");

            // ISBN
            List<IndustryIdentifiers> industryIdentifiers = volumeInfo.getIndustryIdentifiers();
            if (industryIdentifiers != null) {
                for (IndustryIdentifiers i : industryIdentifiers) {
                    log.info("{}: {}", i.getType(), i.getIdentifier());
                }
            }

            // Title.
            log.info("Title: " + volumeInfo.getTitle());

            // Author(s).
            var authors = volumeInfo.getAuthors();
            if (authors != null && !authors.isEmpty()) {
                log.info("Author(s): {}", authors);
            }

            // Publisher.
            var publisher = volumeInfo.getPublisher();
            log.info("Publisher: {}", publisher);

            // Language.
            var language = volumeInfo.getLanguage();
            log.info("Language: {}", language);

            // Description (if any).
            if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
                log.info("Description: " + volumeInfo.getDescription());
            }
            // Ratings (if any).
            if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
                log.info("User Rating: {} ({} rating(s))",
                        "*".repeat(max(0, (int) round(volumeInfo.getAverageRating()))),
                        volumeInfo.getRatingsCount());
            }

            // Access status.
            var accessViewStatus = volume.getAccessInfo().getAccessViewStatus();
            var message = "Additional information about this book is available from Google eBooks at: {}";
            if ("FULL_PUBLIC_DOMAIN".equals(accessViewStatus)) {
                message = "This public domain book is available for free from Google eBooks at: {}";
            } else if ("SAMPLE".equals(accessViewStatus)) {
                message = "A preview of this book is available from Google eBooks at: {}";
            }
            // Link to Google eBooks.
            log.info(message, volumeInfo.getInfoLink());

            //

            if (volumeInfo.getImageLinks() != null) {
                if (volumeInfo.getImageLinks() != null) {
                    log.info("imageLinks: {}", volumeInfo.getImageLinks().toPrettyString());
                }
            }
        }

        log.info("==========");
        log.info("{} total results", volumes.getTotalItems());

    }

}
