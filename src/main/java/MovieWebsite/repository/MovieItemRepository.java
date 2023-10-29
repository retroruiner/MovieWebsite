package MovieWebsite.repository;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;

import java.util.ArrayList;
import java.util.List;

public interface MovieItemRepository {
    void createMovieItem(MovieItem movieItem);
    void updateMovieItem(MovieItem movieItem);
    List<MovieItem> findByTitle(String keyword); // Find movie items by a keyword in the title, used in searching
    MovieItem findByName(String name); //Finds single movie in collection
    MovieItem findById(int id);
    void addGenreToMovie(MovieItem movie, Genre genre);
    ArrayList<String> findGenresOfMovie(MovieItem movie);
}
