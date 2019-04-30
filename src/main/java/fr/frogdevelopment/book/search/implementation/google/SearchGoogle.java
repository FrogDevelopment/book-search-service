package fr.frogdevelopment.book.search.implementation.google;

import static java.lang.Math.max;
import static java.lang.Math.round;

import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo.IndustryIdentifiers;
import fr.frogdevelopment.book.search.implementation.QueryRequest;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class SearchGoogle {

    private final Books books;

    public SearchGoogle(Books books) {
        this.books = books;
    }

    void call(QueryRequest queryRequest) {
        log.info("{}", queryRequest);

        try {
            Volumes.List volumesList = books.volumes().list(queryRequest.getQuery());
            volumesList.setLangRestrict(queryRequest.getLangRestrict());
            volumesList.setMaxResults(queryRequest.getMaxResults());
            volumesList.setStartIndex(queryRequest.getStartIndex());
            volumesList.setOrderBy("newest");
            volumesList.setPrintType("books");
//        volumesList.setFields()

            // Execute the queryRequest.
            var volumes = volumesList.execute();

            // Output results.
            if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
                log.info("No matches found.");
                return;
            }

            for (var volume : volumes.getItems()) {
                displayVolumeInfo(volume);
            }

            log.info("==========");
            log.info("{} total results", volumes.getTotalItems());

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void displayVolumeInfo(Volume volume) throws IOException {
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
            log.info("imageLinks: {}", volumeInfo.getImageLinks().toPrettyString());
        }
    }
}
