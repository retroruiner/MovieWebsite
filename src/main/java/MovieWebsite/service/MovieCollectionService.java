package MovieWebsite.service;

import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.MovieCollectionRepository;
import MovieWebsite.repository.MovieItemRepository;
import MovieWebsite.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MovieCollectionService {
    private final MovieCollectionRepository movieCollectionRepository;
    private final UserRepository userRepository;
    private final MovieItemRepository movieItemRepository;

    public boolean isMovieInCollection(MovieCollection collection, MovieItem movie) {
        return collection.getMovies().contains(movie);
    }

    private UserAccount fetchUser(int userID) {
        Optional<UserAccount> userOptional = userRepository.findById(userID);
        return userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private MovieItem fetchMovie(String movieName) {
        return movieItemRepository.findByTitle(movieName).orElseThrow(() -> new RuntimeException("Movie " + movieName + " does not exist"));
    }

    private boolean isUserCollectionExistent(UserAccount userAccount, String collectionName) {
        return movieCollectionRepository.findByNameAndUserAccount(collectionName, userAccount).isPresent();
    }

    private MovieCollection fetchCollection(UserAccount userAccount, String collectionName) {
        return movieCollectionRepository.findByNameAndUserAccount(collectionName, userAccount)
                .orElseThrow(() -> new IllegalArgumentException("Collection with name " + collectionName + " does not exist"));
    }

    @Transactional
    public void createNewCollection(int userID, String collectionName) {
        UserAccount userAccount = fetchUser(userID);
        MovieCollection movieCollection = new MovieCollection(collectionName, userAccount);
        if(!isUserCollectionExistent(userAccount, movieCollection.getName())) {
            movieCollectionRepository.save(movieCollection);
        } else {
            throw new RuntimeException("Collection with name " + collectionName + " exists");
        }
    }

    @Transactional
    public void addMovieToCollection(int userId, String movieName, String collectionName) {
        UserAccount userAccount = fetchUser(userId);
        MovieItem movie = fetchMovie(movieName);
        MovieCollection movieCollection = fetchCollection(userAccount, collectionName);
        if(isMovieInCollection(movieCollection, movie)) {
            throw new RuntimeException("Movie already in collection: " + movieName);
        }
        movieCollection.getMovies().add(movie);
        movieCollectionRepository.save(movieCollection);
    }

    @Transactional
    public void removeMovieFromCollection(int userId, String movieName, String collectionName) {
        UserAccount userAccount = fetchUser(userId);
        MovieItem movie = fetchMovie(movieName);
        MovieCollection movieCollection = fetchCollection(userAccount, collectionName);
        if(!isMovieInCollection(movieCollection, movie)) {
            throw new RuntimeException("No movie in collection: " + movieName);
        }
        movieCollection.getMovies().remove(movie);
        movieCollectionRepository.save(movieCollection);
    }

    @Transactional
    public void deleteCollection(int userID, String collectionName) {
        UserAccount userAccount = fetchUser(userID);
        MovieCollection movieCollection = fetchCollection(userAccount, collectionName);
        movieCollectionRepository.delete(movieCollection);
    }
}
