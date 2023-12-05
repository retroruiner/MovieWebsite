package MovieWebsite.repository;

import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieCollectionRepository extends JpaRepository<MovieCollection, Integer> {
//    void addMovieToCollection(MovieItem movie, MovieCollection collection);
//    MovieCollection getCollectionByName(String name);
//    MovieItem getMovieInCollection(MovieCollection collection, MovieItem movieItem);

    MovieCollection findMovieCollectionByName(String name);
    Optional<MovieCollection> findByNameAndUserAccount(String name, UserAccount userAccount);

}
