package MovieWebsite.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum Genre {
    ACTION("Action"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    ROMANCE("Romance"),
    THRILLER("Thriller"),
    SCI_FI("Science fiction");

    private String name;
    private List<MovieItem> movies;

    Genre(String name) {
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
