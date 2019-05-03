package fr.frogdevelopment.book.search.entity;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class Book {

    private String isbn;
    private String title;
    @Singular("author")
    private List<String> authors;
    private String publisher;
    private String language;
    private String description;
    private Double ratingAverage;
    private Integer ratingCount;
    private String accessViewStatus;
    private String infoLink;

    private String thumbnail;
    private String large;
}
