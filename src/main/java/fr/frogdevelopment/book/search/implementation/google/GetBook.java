package fr.frogdevelopment.book.search.implementation.google;

import com.google.api.services.books.Books;
import fr.frogdevelopment.book.search.entity.Book;
import java.io.IOException;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class GetBook {

    private static final String DETAIL_FIELDS = "totalItems,items(volumeInfo(subtitle,publishedDate,language,description,pageCount,categories,averageRating,ratingsCount))";
    private static final String FULL_FIELDS = "totalItems,items(volumeInfo(title,subtitle,authors,imageLinks(thumbnail),publisher,publishedDate,language,description,pageCount,categories,averageRating,ratingsCount))";

    private final Books books;

    public GetBook(Books books) {
        this.books = books;
    }

    public Optional<Book> withDetailFields(String isbn) {
        return call(isbn, DETAIL_FIELDS);
    }

    public Optional<Book> withFullFields(String isbn) {
        return call(isbn, FULL_FIELDS);
    }

    private Optional<Book> call(String isbn, String fields) {
        try {
            var volumesList = books.volumes().list("isbn:" + isbn);
            volumesList.setFields(fields);

            // Execute the queryRequest.
            var volumes = volumesList.execute();

            // Output results.
            if (volumes.getTotalItems() == 0) {
                return Optional.empty();
            }

            return volumes.getItems()
                    .stream()
                    .map(ToBook::call)
                    .peek(book -> book.setIsbn(isbn))
                    .findFirst();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
