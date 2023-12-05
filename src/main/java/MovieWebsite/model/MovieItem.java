package MovieWebsite.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class MovieItem {
    @Id
    @GeneratedValue
    private int id;
    @Builder.Default
    private  int numOfUsersVoted = 0;
    private float duration;
    @Builder.Default
    private float rating = 0;
    private String title;
    private String director;
    private String countryOfOrigin;
    private Date releaseDate;

    @ElementCollection(targetClass=Genre.class)
    @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "movie_item_id"))
    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Genre> genreList = new ArrayList<>();

    @ManyToMany(mappedBy = "ratedMovies")
    private List<UserAccount> ratedByUsers = new ArrayList<>();

    @Transient
    private ArrayList<Float> ratings = new ArrayList<>();
}

