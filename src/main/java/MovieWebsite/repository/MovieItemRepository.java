package MovieWebsite.repository;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import java.util.List;

public interface MovieItemRepository {
    List<MovieItem> findByTitleContaining(String keyword); // Find movie items by a keyword in the title, used in searching

}
