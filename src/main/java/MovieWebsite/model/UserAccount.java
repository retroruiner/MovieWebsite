package MovieWebsite.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class UserAccount {
    private int id;
    private String fullName;
    private String nickname;
    private Date dateOfBirth;
    private String email;

    //TODO: Implement hashing algorithm
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int password;

    private List<MovieCollection> listOfCollections;
    private List<MovieCollection> listOfMovies;

    public UserAccount(UserAccountBuilder builder) {
        this.fullName = builder.fullName;
        this.nickname = builder.nickname;
        this.dateOfBirth = builder.dateOfBirth;
        this.email = builder.email;

        this.listOfCollections = new ArrayList<>();
    }

    public void createNewCollection(String name) {
        listOfCollections.add(new MovieCollection(name));
    }

    public void addMovieToCollection(MovieItem movie, MovieCollection movieCollection) {
        for (MovieCollection collection: listOfCollections) {
            if (collection.getName().equals(movieCollection.getName())) {
                collection.addMovie(movie);
            }
        }
    }

    @Accessors(chain = true)
    @Setter
    public static class UserAccountBuilder {
        private int id;
        private String fullName;
        private String nickname;
        private Date dateOfBirth;
        private String email;
    }
}
