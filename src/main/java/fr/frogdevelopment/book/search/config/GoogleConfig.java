package fr.frogdevelopment.book.search.config;

import static java.lang.String.format;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleConfig {

    private final BuildProperties buildProperties;

    public GoogleConfig(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    JsonFactory jsonFactory() {
        return JacksonFactory.getDefaultInstance();
    }

    @Bean
    Books books() throws GeneralSecurityException, IOException {
        return new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory(), null)
                /*
                  Be sure to specify the name of your application. If the application name is null or blank, the
                  application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
                 */
                .setApplicationName(format("%s-%s/%s",
                        buildProperties.getGroup(),
                        buildProperties.getName(),
                        buildProperties.getVersion()))
                .build();
    }

}
