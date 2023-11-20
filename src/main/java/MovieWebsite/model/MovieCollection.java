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
    private List<MovieItem> movies = new ArrayList<>();

    @ManyToOne
    private UserAccount userAccount;

    public MovieCollection(String name, UserAccount userAccount) {
        this.name = name;
        this.userAccount = userAccount;
    }
}
