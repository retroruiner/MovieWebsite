package MovieWebsite.repository;

import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCollectionRepository extends JpaRepository<MovieCollection, Integer> {
    void addMovieToCollection(MovieItem movie, MovieCollection collection);
    MovieCollection getCollectionByName(String name);
    MovieItem getMovieInCollection(MovieCollection collection, MovieItem movieItem);
}
