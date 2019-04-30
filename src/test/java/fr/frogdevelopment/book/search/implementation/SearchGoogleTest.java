package fr.frogdevelopment.book.search.implementation;

import java.util.List;
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
        String author = "brandon sanderson";

        // when
        searchGoogle.call(List.of("en", "fr"), null, author, null, null);

        // then
    }

}
