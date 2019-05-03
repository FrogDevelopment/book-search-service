package fr.frogdevelopment.book.search.implementation.google;

import static org.assertj.core.api.Assertions.assertThat;

import fr.frogdevelopment.book.search.entity.Book;
import fr.frogdevelopment.book.search.implementation.QueryRequest;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//@Disabled
@SpringBootTest
@ActiveProfiles("test")
@Tag("integrationTest")
class SearchGoogleTest {

    @Autowired
    private SearchGoogle searchGoogle;

    @Test
    void should() {
        // given
        var nbResult = 100;
        QueryRequest queryRequest = QueryRequest.builder()
                .langRestrict("fr")
                .author("brandon sanderson")
                .build();
        // when
        List<Book> books = searchGoogle.call(queryRequest, nbResult, 0);

        // then
        assertThat(books).hasSize(nbResult);
    }

}
