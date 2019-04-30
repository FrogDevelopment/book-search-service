package fr.frogdevelopment.book.search.implementation.google;

import fr.frogdevelopment.book.search.implementation.QueryRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@SpringBootTest
@ActiveProfiles("test")
@Tag("integrationTest")
class SearchGoogleTest {

    @Autowired
    private SearchGoogle searchGoogle;

    @Test
    void should() {
        // given
        QueryRequest queryRequest = QueryRequest.builder()
                .langRestrict("fr")
                .author("brandon sanderson")
                .startIndex(0)
                .maxResults(23)
                .build();
        // when
        searchGoogle.call(queryRequest);

        // then
    }

}
