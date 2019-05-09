package fr.frogdevelopment.book.search.api;

import fr.frogdevelopment.book.search.entity.Book;
import fr.frogdevelopment.book.search.implementation.google.GetBook;
import fr.frogdevelopment.book.search.implementation.google.GetPreviews;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final GetPreviews getPreviews;
    private final GetBook getBook;

    public BookController(GetPreviews getPreviews, GetBook getBook) {
        this.getPreviews = getPreviews;
        this.getBook = getBook;
    }

    @GetMapping("search")
    public List<Book> search(@RequestParam(name = "title", required = false) String title,
                             @RequestParam(name = "author", required = false) String author,
                             @RequestParam(name = "publisher", required = false) String publisher,
                             @RequestParam(name = "lang_restrict", required = false) String langRestrict,
                             @RequestParam(name = "nb_result", defaultValue = "10") long nbResult,
                             @RequestParam(name = "start_index", defaultValue = "0") long startIndex) {
        return getPreviews.call(title, author, publisher, langRestrict, nbResult, startIndex);
    }

    @GetMapping("get-detail")
    public Optional<Book> withDetailFields(@RequestParam String isbn) {
        return getBook.withDetailFields(isbn);
    }

    @GetMapping("get-full")
    public Optional<Book> withFullFields(@RequestParam String isbn) {
        return getBook.withFullFields(isbn);
    }

}
