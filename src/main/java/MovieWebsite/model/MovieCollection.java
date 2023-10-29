package MovieWebsite.model;
import MovieWebsite.repository.MovieCollectionRepo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MovieCollection {
    private String name;
    private List<MovieItem> movies;

    public MovieCollection(String name) {
        this.name = name;
        this.movies = new ArrayList<>();
    }
    public void addMovie(MovieItem movie) {
        movies.add(movie);
    }

    public void removeMovie(MovieItem movie) {
        movies.remove(movie);
    }
}
