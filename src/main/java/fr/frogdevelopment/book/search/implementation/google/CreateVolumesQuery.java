package fr.frogdevelopment.book.search.implementation.google;

import static java.lang.String.format;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.BooksRequest;
import com.google.api.services.books.BooksRequestInitializer;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

@Component
public class CreateVolumesQuery {

    private final String googleApiKey;
    private final Books.Builder builder;

    public CreateVolumesQuery(@Value("${google.api-key}") String googleApiKey,
                              BuildProperties buildProperties) throws GeneralSecurityException, IOException {
        this.googleApiKey = googleApiKey;
        builder = new Books.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                null)
                .setApplicationName(format("%s-%s/%s",
                        buildProperties.getGroup(),
                        buildProperties.getName(),
                        buildProperties.getVersion()));
    }

    List call(String country, String q) throws IOException {
        return builder
                .setGoogleClientRequestInitializer(new CountryBooksRequestInitializer(googleApiKey, country))
                .build()
                .volumes()
                .list(q);
    }

    private static class CountryBooksRequestInitializer extends BooksRequestInitializer {

        private final String country;

        private CountryBooksRequestInitializer(String key, String country) {
//            super(key);
            super();
            this.country = country;
        }

        @Override
        public void initializeBooksRequest(BooksRequest request) {
            request.set("country", country);
        }
    }

}
