package MovieWebsite.repository;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieItemRepository extends JpaRepository<MovieItem, Integer> {
    Optional<MovieItem> findByTitle(String name); //Finds single movie in collection

    @Query("SELECT m.rating FROM MovieItem m WHERE m.id = ?1")
    Float findRatingById(int id);

    @Modifying
    @Query("UPDATE MovieItem m SET m.rating = ?2, m.numOfUsersVoted = m.numOfUsersVoted + 1 WHERE m.id = ?1")
    void updateMovieItemRating(int movieId, float rating);

    @Query("SELECT m.numOfUsersVoted FROM MovieItem m WHERE m.id = ?1")
    Integer findVotedUsersById(int movieId);

    List<MovieItem> findByTitleContainingIgnoreCase(String title);

    List<MovieItem> findByGenreListContainsIgnoreCase(Genre genre);

    List<MovieItem> findByRatingGreaterThanEqual(float minRating);
}
