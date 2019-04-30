package fr.frogdevelopment.book.search.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleConfig {

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or blank, the
     * application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "FrogDevelopment-Bibluelle/1.0";

    @Bean
    JsonFactory jsonFactory() {
        return JacksonFactory.getDefaultInstance();
    }

    @Bean
    Books books() throws GeneralSecurityException, IOException {
        return new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory(), null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

}
