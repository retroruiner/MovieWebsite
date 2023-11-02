package MovieWebsite.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
public class UserAccount {
    private int id;
    private String fullName;
    private String nickname;
    private Date dateOfBirth;
    private String email;
    private String password;
    private boolean loggedIn;
    private String authToken; //TODO: change location of Token, not safe now

    //TODO: Implement hashing algorithm

    private List<MovieCollection> listOfCollections;

    private final List<UserAccount> friendList;

    public void createNewCollection(String name) {
        listOfCollections.add(new MovieCollection(name));
    }

    private void verifyCollectionExistence() {
        if(this.listOfCollections == null) {
            this.listOfCollections = new ArrayList<>();
        }
    }

    public void addMovieToCollection(MovieItem movie, MovieCollection movieCollection) {
        modifyMovieInCollection(movie, movieCollection, true);
    }

    public void removeMovieFromCollection(MovieItem movie, MovieCollection movieCollection) {
        modifyMovieInCollection(movie, movieCollection, false);
    }

    private void modifyMovieInCollection(MovieItem movie, MovieCollection movieCollection, boolean add) {
        verifyCollectionExistence();
        for (MovieCollection collection : listOfCollections) {
            if (collection.getName().equals(movieCollection.getName())) {
                if (add) {
                    collection.addMovie(movie);
                } else {
                    collection.removeMovie(movie);
                }
                return;
            }
        }
    }
    public void deleteCollection(MovieCollection movieCollection) {
        if (listOfCollections.contains(movieCollection)) {
            listOfCollections.remove(movieCollection);
        } else {
            throw new IllegalArgumentException("No such collection found");
        }
    }

    public boolean verifyPassword(String password) {
        if (this.password.equals(password)) {
            return true; // Password matches
        } else {
            throw new IllegalArgumentException("Incorrect password"); // Password does not match
        }
    }
}
