package MovieWebsite.repository;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface MovieItemRepository extends JpaRepository<MovieItem, Integer> {
    List<MovieItem> findByTitle(String keyword); // Find movie items by a keyword in the title, used in searching
    MovieItem findByName(String name); //Finds single movie in collection
    //MovieItem findById(int id);
    void addGenreToMovie(MovieItem movie, Genre genre);
    ArrayList<String> findGenresOfMovie(MovieItem movie);
}
