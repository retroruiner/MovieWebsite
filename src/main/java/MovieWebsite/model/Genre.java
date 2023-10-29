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

    public void addMovie(MovieItem newMovie) {
        verifyMoviesListExistence();
        if(!validateIfMovieExists(newMovie)) {
            this.movies.add(newMovie);
            newMovie.addGenre(this);
        }

    }
    public void removeMovie(MovieItem movie) {
        verifyMoviesListExistence();
        if(validateIfMovieExists(movie)) {
            this.movies.remove(movie);
            movie.removeGenre(this);
        }
    }

    private boolean validateIfMovieExists(MovieItem movieItem) {
        for (MovieItem m: movies) {
            if( m.getId() == movieItem.getId()) {
                return true;
            }
        }
        return false;
    }

    private void verifyMoviesListExistence() {
        if(this.movies == null) {
            this.movies = new ArrayList<>();
        }
    }

}
