package MovieWebsite.repository;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MovieItemRepository extends JpaRepository<MovieItem, Integer> {
    // Find movie by its title
    Optional<MovieItem> findByTitle(String name); //Finds single movie in collection

    // Find list of movies by title ignoring the case
    List<MovieItem> findByTitleContainingIgnoreCase(String title);

    // Find list of movies by genre
    List<MovieItem> findByGenreListContainsIgnoreCase(Genre genre);

    //Find list of movies by rating
    List<MovieItem> findByRatingGreaterThanEqual(float minRating);
}
