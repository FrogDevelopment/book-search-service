package fr.frogdevelopment.book.search.implementation.google;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Tag("integrationTest")
class GetPreviewsTest {

    @Autowired
    private GetPreviews getPreviews;

    @Test
    void shouldReturnPreviews() {
        // given
        var country = "fr";
        var title = "Mistborn: Secret History";
        var author = "Brandon Sanderson";
        var publisher = "Macmillan";
        var langRestrict = "en";
        var nbResult = 40;
        var startIndex = 0;

        // when
        var previews = getPreviews.call(country, title, author, publisher, langRestrict, nbResult, startIndex);

        // then
        assertThat(previews).hasSize(2);
        assertThat(previews.get(0))
                .hasFieldOrPropertyWithValue("isbn", "9780765395498")
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("subTitle", null)
                .hasFieldOrPropertyWithValue("authors", List.of(author))
                .hasFieldOrPropertyWithValue("publisher", publisher)
                .hasFieldOrPropertyWithValue("publishedDate", null)
                .hasFieldOrPropertyWithValue("language", null)
                .hasFieldOrPropertyWithValue("description", null)
                .hasFieldOrPropertyWithValue("pageCount", null)
                .hasFieldOrPropertyWithValue("categories", null)
                .hasFieldOrPropertyWithValue("averageRating", null)
                .hasFieldOrPropertyWithValue("ratingsCount", null)
                .hasFieldOrPropertyWithValue("thumbnail",
                        "http://books.google.com/books/content?id=OozUDAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api")
        ;
    }

}
