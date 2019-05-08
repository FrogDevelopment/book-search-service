package fr.frogdevelopment.book.search.implementation.google;

import static lombok.AccessLevel.PRIVATE;

import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.google.api.services.books.model.Volume.VolumeInfo.IndustryIdentifiers;
import fr.frogdevelopment.book.search.entity.Book;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
class ToBook {

    static Book call(Volume volume) {
        VolumeInfo volumeInfo = volume.getVolumeInfo();

        var builder = Book.builder()
                .title(volumeInfo.getTitle())
                .subTitle(volumeInfo.getSubtitle())
                .authors(volumeInfo.getAuthors())
                .publisher(volumeInfo.getPublisher())
                .publishedDate(volumeInfo.getPublishedDate())
                .language(volumeInfo.getLanguage())
                .description(volumeInfo.getDescription())
                .pageCount(volumeInfo.getPageCount())
                .categories(volumeInfo.getCategories());

        // ISBN 13
        if (volumeInfo.getIndustryIdentifiers() != null) {
            volumeInfo.getIndustryIdentifiers().stream()
                    .filter(i -> "ISBN_13".equals(i.getType()))
                    .findFirst()
                    .map(IndustryIdentifiers::getIdentifier)
                    .ifPresent(builder::isbn);
        }

        // Ratings
        if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
            builder.averageRating(volumeInfo.getAverageRating())
                    .ratingsCount(volumeInfo.getRatingsCount());
        }

        // Images
        if (volumeInfo.getImageLinks() != null && volumeInfo.getImageLinks().getThumbnail() != null) {
            builder.thumbnail(volumeInfo
                    .getImageLinks()
                    .getThumbnail()
                    .replaceAll("&edge=curl", ""));

        }

        Book book = builder.build();
        log.info("{}", book);

        return book;
    }
}
