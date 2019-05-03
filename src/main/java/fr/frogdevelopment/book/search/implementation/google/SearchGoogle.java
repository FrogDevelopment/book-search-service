package fr.frogdevelopment.book.search.implementation.google;

import static java.util.Collections.emptyList;

import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.google.api.services.books.model.Volume.VolumeInfo.IndustryIdentifiers;
import fr.frogdevelopment.book.search.entity.Book;
import fr.frogdevelopment.book.search.implementation.QueryRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class SearchGoogle {

    private final Books books;

    public SearchGoogle(Books books) {
        this.books = books;
    }

    List<Book> call(QueryRequest queryRequest, int nbResult, int startIndex) {
        if (nbResult <= 40) {
            return getBooks(queryRequest, nbResult, startIndex);
        } else {
            List<Book> books = new ArrayList<>();
            do {
                nbResult -= 40;
                books.addAll(getBooks(queryRequest, 40, startIndex++));
            } while (nbResult > 40);

            if (nbResult > 0) {
                books.addAll(getBooks(queryRequest, nbResult, startIndex));
            }

            return books;
        }
    }

    private List<Book> getBooks(QueryRequest queryRequest, long nbResult, long startIndex) {
        log.info("*** {}, nbResult={}, startIndex={}", queryRequest, nbResult, startIndex);
        try {
            Volumes.List volumesList = books.volumes().list(queryRequest.getQuery());
            volumesList.setLangRestrict(queryRequest.getLangRestrict());
            volumesList.setMaxResults(nbResult);
            volumesList.setStartIndex(startIndex);
            volumesList.setOrderBy("newest");
            volumesList.setPrintType("books");
//        volumesList.setFields()

            // Execute the queryRequest.
            var volumes = volumesList.execute();

            // Output results.
            if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
                log.info("No matches found.");
                return emptyList();
            }

            return volumes.getItems().stream()
                    .map(toBook())
                    .collect(Collectors.toList());


        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Function<Volume, Book> toBook() {
        return volume -> {
            VolumeInfo volumeInfo = volume.getVolumeInfo();
            var builder = Book.builder()
                    .title(volumeInfo.getTitle())
                    .authors(volumeInfo.getAuthors())
                    .publisher(volumeInfo.getPublisher())
                    .language(volumeInfo.getLanguage())
                    .description(volumeInfo.getDescription())
                    .accessViewStatus(volume.getAccessInfo().getAccessViewStatus())
                    .infoLink(volumeInfo.getInfoLink());

            // ISBN
            List<IndustryIdentifiers> industryIdentifiers = volumeInfo.getIndustryIdentifiers();
            if (industryIdentifiers != null) {
                industryIdentifiers.stream()
                        .filter(i -> "ISBN_13".equals(i.getType()))
                        .map(IndustryIdentifiers::getIdentifier)
                        .forEach(builder::isbn);
            }

            // Ratings
            if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
                builder.ratingAverage(volumeInfo.getAverageRating())
                        .ratingCount(volumeInfo.getRatingsCount());
            }

//            // Access status.
//            var accessViewStatus = volume.getAccessInfo().getAccessViewStatus();
//            var message = "Additional information about this book is available from Google eBooks at: {}";
//            if ("FULL_PUBLIC_DOMAIN".equals(accessViewStatus)) {
//                message = "This public domain book is available for free from Google eBooks at: {}";
//            } else if ("SAMPLE".equals(accessViewStatus)) {
//                message = "A preview of this book is available from Google eBooks at: {}";
//            }
//            // Link to Google eBooks.
//            log.info(message, volumeInfo.getInfoLink());

            // Images
            if (volumeInfo.getImageLinks() != null) {
                builder.thumbnail(volumeInfo.getImageLinks().getThumbnail())
                        .large(volumeInfo.getImageLinks().getLarge());
            }

            Book book = builder.build();
            log.info("{}", book);

            return book;
        };
    }
}
