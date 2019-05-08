package fr.frogdevelopment.book.search.entity;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private String isbn;
    private String title;
    private String subTitle;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String language;
    private String description;
    private Integer pageCount;
    private List<String> categories;
    private Double averageRating;
    private Integer ratingsCount;
    private String thumbnail;
}
