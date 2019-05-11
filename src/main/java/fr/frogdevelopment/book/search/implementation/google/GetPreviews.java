package fr.frogdevelopment.book.search.implementation.google;

import static java.lang.String.join;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import fr.frogdevelopment.book.search.entity.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GetPreviews {

    private static final String PRINT_TYPE = "books";
    private static final String PREVIEW_FIELDS = "totalItems,items/volumeInfo(title,publisher,authors,imageLinks(thumbnail),industryIdentifiers)";

    private final CreateVolumesQuery createVolumesQuery;

    public GetPreviews(CreateVolumesQuery createVolumesQuery) {
        this.createVolumesQuery = createVolumesQuery;
    }

    public List<Book> call(String country,
                           String title,
                           String author,
                           String publisher,
                           String langRestrict,
                           long nbResult,
                           long startIndex) {

        try {
            var volumesQuery = createVolumesQuery.call(country, toQuery(title, author, publisher));
            volumesQuery.setLangRestrict(langRestrict);
            volumesQuery.setMaxResults(nbResult);
            volumesQuery.setStartIndex(startIndex);
            volumesQuery.setOrderBy("relevance");
            volumesQuery.setPrintType(PRINT_TYPE);
            volumesQuery.setFields(PREVIEW_FIELDS);

            // Execute the queryRequest.
            var volumes = volumesQuery.execute();

            // Output results.
            if (volumes.getTotalItems() == 0) {
                return emptyList();
            }

            return volumes.getItems()
                    .stream()
                    .map(ToBook::call)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String toQuery(String title, String author, String publisher) {
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
            throw new IllegalArgumentException("At least one parameter is required. 'title', 'author' or 'publisher'");
        }

        return join("+", parameters);
    }
}
