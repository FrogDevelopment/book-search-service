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
class GetBookTest {

    @Autowired
    private GetBook getBook;

    @Test
    void shouldReturnBookWithDetailFields() {
        // given
        var isbn = "9781429914567";

        // when
        var book = getBook.withDetailFields(isbn);

        // then
        assertThat(book).isPresent();
        assertThat(book).get()
                .hasFieldOrPropertyWithValue("isbn", "9781429914567")
                .hasFieldOrPropertyWithValue("title", null)
                .hasFieldOrPropertyWithValue("subTitle", "The Final Empire")
                .hasFieldOrPropertyWithValue("authors", null)
                .hasFieldOrPropertyWithValue("publisher", null)
                .hasFieldOrPropertyWithValue("publishedDate", "2010-04-01")
                .hasFieldOrPropertyWithValue("language", "en")
                .hasFieldOrPropertyWithValue("description",
                        "From #1 New York Times bestselling author Brandon Sanderson, the Mistborn series is a heist story of political intrigue and magical, martial-arts action. For a thousand years the ash fell and no flowers bloomed. For a thousand years the Skaa slaved in misery and lived in fear. For a thousand years the Lord Ruler, the \"Sliver of Infinity,\" reigned with absolute power and ultimate terror, divinely invincible. Then, when hope was so long lost that not even its memory remained, a terribly scarred, heart-broken half-Skaa rediscovered it in the depths of the Lord Ruler's most hellish prison. Kelsier \"snapped\" and found in himself the powers of a Mistborn. A brilliant thief and natural leader, he turned his talents to the ultimate caper, with the Lord Ruler himself as the mark. Kelsier recruited the underworld's elite, the smartest and most trustworthy allomancers, each of whom shares one of his many powers, and all of whom relish a high-stakes challenge. Only then does he reveal his ultimate dream, not just the greatest heist in history, but the downfall of the divine despot. But even with the best criminal crew ever assembled, Kel's plan looks more like the ultimate long shot, until luck brings a ragged girl named Vin into his life. Like him, she's a half-Skaa orphan, but she's lived a much harsher life. Vin has learned to expect betrayal from everyone she meets, and gotten it. She will have to learn to trust, if Kel is to help her master powers of which she never dreamed. This saga dares to ask a simple question: What if the hero of prophecy fails? Other Tor books by Brandon Sanderson The Cosmere The Stormlight Archive The Way of Kings Words of Radiance Edgedancer (Novella) Oathbringer (forthcoming) The Mistborn trilogy Mistborn: The Final Empire The Well of Ascension The Hero of Ages Mistborn: The Wax and Wayne series Alloy of Law Shadows of Self Bands of Mourning Collection Arcanum Unbounded Other Cosmere novels Elantris Warbreaker The Alcatraz vs. the Evil Librarians series Alcatraz vs. the Evil Librarians The Scrivener's Bones The Knights of Crystallia The Shattered Lens The Dark Talent The Rithmatist series The Rithmatist Other books by Brandon Sanderson The Reckoners Steelheart Firefight Calamity At the Publisher's request, this title is being sold without Digital Rights Management Software (DRM) applied.")
                .hasFieldOrPropertyWithValue("pageCount", 544)
                .hasFieldOrPropertyWithValue("categories", List.of("Fiction"))
                .hasFieldOrPropertyWithValue("averageRating", 4.0)
                .hasFieldOrPropertyWithValue("ratingsCount", 2257)
                .hasFieldOrPropertyWithValue("thumbnail", null)
        ;
    }

    @Test
    void shouldReturnBookWithAllFields() {
        // given
        var isbn = "9781429914567";

        // when
        var book = getBook.withFullFields(isbn);

        // then
        assertThat(book).isPresent();
        assertThat(book).get()
                .hasFieldOrPropertyWithValue("isbn", "9781429914567")
                .hasFieldOrPropertyWithValue("title", "Mistborn")
                .hasFieldOrPropertyWithValue("subTitle", "The Final Empire")
                .hasFieldOrPropertyWithValue("authors", List.of("Brandon Sanderson"))
                .hasFieldOrPropertyWithValue("publisher", "Macmillan")
                .hasFieldOrPropertyWithValue("publishedDate", "2010-04-01")
                .hasFieldOrPropertyWithValue("language", "en")
                .hasFieldOrPropertyWithValue("description",
                        "From #1 New York Times bestselling author Brandon Sanderson, the Mistborn series is a heist story of political intrigue and magical, martial-arts action. For a thousand years the ash fell and no flowers bloomed. For a thousand years the Skaa slaved in misery and lived in fear. For a thousand years the Lord Ruler, the \"Sliver of Infinity,\" reigned with absolute power and ultimate terror, divinely invincible. Then, when hope was so long lost that not even its memory remained, a terribly scarred, heart-broken half-Skaa rediscovered it in the depths of the Lord Ruler's most hellish prison. Kelsier \"snapped\" and found in himself the powers of a Mistborn. A brilliant thief and natural leader, he turned his talents to the ultimate caper, with the Lord Ruler himself as the mark. Kelsier recruited the underworld's elite, the smartest and most trustworthy allomancers, each of whom shares one of his many powers, and all of whom relish a high-stakes challenge. Only then does he reveal his ultimate dream, not just the greatest heist in history, but the downfall of the divine despot. But even with the best criminal crew ever assembled, Kel's plan looks more like the ultimate long shot, until luck brings a ragged girl named Vin into his life. Like him, she's a half-Skaa orphan, but she's lived a much harsher life. Vin has learned to expect betrayal from everyone she meets, and gotten it. She will have to learn to trust, if Kel is to help her master powers of which she never dreamed. This saga dares to ask a simple question: What if the hero of prophecy fails? Other Tor books by Brandon Sanderson The Cosmere The Stormlight Archive The Way of Kings Words of Radiance Edgedancer (Novella) Oathbringer (forthcoming) The Mistborn trilogy Mistborn: The Final Empire The Well of Ascension The Hero of Ages Mistborn: The Wax and Wayne series Alloy of Law Shadows of Self Bands of Mourning Collection Arcanum Unbounded Other Cosmere novels Elantris Warbreaker The Alcatraz vs. the Evil Librarians series Alcatraz vs. the Evil Librarians The Scrivener's Bones The Knights of Crystallia The Shattered Lens The Dark Talent The Rithmatist series The Rithmatist Other books by Brandon Sanderson The Reckoners Steelheart Firefight Calamity At the Publisher's request, this title is being sold without Digital Rights Management Software (DRM) applied.")
                .hasFieldOrPropertyWithValue("pageCount", 544)
                .hasFieldOrPropertyWithValue("categories", List.of("Fiction"))
                .hasFieldOrPropertyWithValue("averageRating", 4.0)
                .hasFieldOrPropertyWithValue("ratingsCount", 2257)
                .hasFieldOrPropertyWithValue("thumbnail",
                        "http://books.google.com/books/content?id=t_ZYYXZq4RgC&printsec=frontcover&img=1&zoom=1&source=gbs_api")
        ;
    }
}
