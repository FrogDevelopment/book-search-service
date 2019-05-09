package fr.frogdevelopment.book.search.config;

import static java.lang.String.format;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleConfig {

    @Value("${google.api-key}")
    private String googleApiKey;

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
        errorIfNotSpecified();
        return new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory(), null)
                .setApplicationName(getApplicationName())
//                .setGoogleClientRequestInitializer(new BooksRequestInitializer(googleApiKey))
                .build();
    }

    private String getApplicationName() {
        return format("%s-%s/%s",
                buildProperties.getGroup(),
                buildProperties.getName(),
                buildProperties.getVersion());
    }

    private void errorIfNotSpecified() {
        if (StringUtils.isBlank(googleApiKey)) {
            throw new IllegalStateException("Missing google.api-key property");
        }
    }

}
