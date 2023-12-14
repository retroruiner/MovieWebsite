package MovieWebsite.repository;

import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieCollectionRepository extends JpaRepository<MovieCollection, Integer> {
    // Find a collection by its name
    MovieCollection findMovieCollectionByName(String name);

    // Find a collection that belongs to the specific user
    Optional<MovieCollection> findByNameAndUserAccount(String name, UserAccount userAccount);

    // FInd all collections that belong to user
    @Query("SELECT m FROM MovieCollection m WHERE m.userAccount.id = ?1")
    List<MovieCollection> findAllByUserId(int userId);
}
