package MovieWebsite.model;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class MovieCollection {
    private String name;
    private List<MovieItem> movies;

    public MovieCollection(String name) {
        this.name = name;
        this.movies = new ArrayList<>();
    }
    public void addMovie(MovieItem movie) {
        if(!isMovieInCollection(movie)) {
            movies.add(movie);
        }
    }

    public void removeMovie(MovieItem movie) {
        if(isMovieInCollection(movie)) {
            movies.remove(movie);
        }
    }
}
