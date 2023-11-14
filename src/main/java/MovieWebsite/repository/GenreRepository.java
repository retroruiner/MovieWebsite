package MovieWebsite.repository;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository {
    Genre findGenreByName(String name);
    List<Genre> getAllGenres();
    List<MovieItem> getMoviesByGenre(String genre);
}
