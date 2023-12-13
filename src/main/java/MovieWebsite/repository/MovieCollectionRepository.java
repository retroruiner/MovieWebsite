package MovieWebsite.repository;

import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieCollectionRepository extends JpaRepository<MovieCollection, Integer> {

    MovieCollection findMovieCollectionByName(String name);
    Optional<MovieCollection> findByNameAndUserAccount(String name, UserAccount userAccount);

    @Query("SELECT m FROM MovieCollection m WHERE m.userAccount.id = ?1")
    List<MovieCollection> findAllByUserId(int userId);
}
