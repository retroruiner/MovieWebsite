package MovieWebsite.repository;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import java.util.List;

public interface GenreRepository {
    public void addGenre(String genre);
    public List<Genre> getAllGenres();
    public List<MovieItem> getMoviesByGenre(String genre);

}
