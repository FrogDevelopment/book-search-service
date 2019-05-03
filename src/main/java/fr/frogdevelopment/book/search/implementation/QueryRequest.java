package fr.frogdevelopment.book.search.implementation;

import static java.lang.String.join;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Builder
@ToString
public class QueryRequest implements Serializable {

    private String title;
    private String author;
    private String publisher;

    @Getter
    private String langRestrict;

    public String getQuery() {
        var parameters = new ArrayList<String>();

        if (isNotBlank(title)) {
            parameters.add("intitle:" + title);
        }

        if (isNotBlank(author)) {
            parameters.add("inauthor:" + author);
        }

        if (isNotBlank(publisher)) {
            parameters.add("inpublisher:" + publisher);
        }

        if (parameters.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return join("+", parameters);
    }
}

