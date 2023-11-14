package MovieWebsite.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieCollection {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "movie_collection_movies",
            joinColumns = @JoinColumn(name = "movie_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<MovieItem> movies;

    @ManyToOne
    private UserAccount userAccount;

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

    private boolean isMovieInCollection(MovieItem movie) {
        for (MovieItem m: movies) {
            if(Objects.equals(m.getTitle(), movie.getTitle())) {
                return true;
            }
        }
        return false;
    }
    public void deleteCollection() { movies.clear();}
}
