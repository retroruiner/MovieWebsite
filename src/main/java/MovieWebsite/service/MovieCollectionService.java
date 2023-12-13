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

    private UserAccount fetchUser(String authToken) {
       return userRepository.findByAuthToken(authToken);
    }

    private MovieItem fetchMovie(int movieId) {
        return movieItemRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie with id " + movieId + " does not exist"));
    }

    private boolean isUserCollectionExistent(UserAccount userAccount, String collectionName) {
        return movieCollectionRepository.findByNameAndUserAccount(collectionName, userAccount).isPresent();
    }

    private MovieCollection fetchCollection(UserAccount userAccount, String collectionName) {
        return movieCollectionRepository.findByNameAndUserAccount(collectionName, userAccount)
                .orElseThrow(() -> new IllegalArgumentException("Collection with name " + collectionName + " does not exist"));
    }

    @Transactional
    public MovieCollection createNewCollection(String authToken, String collectionName) {
        UserAccount userAccount = fetchUser(authToken);
//        System.out.println(userAccount.getId());
        MovieCollection movieCollection = new MovieCollection(collectionName, userAccount);
        if(!isUserCollectionExistent(userAccount, movieCollection.getName())) {
            movieCollectionRepository.save(movieCollection);
        } else {
            throw new RuntimeException("Collection with name " + collectionName + " exists");
        }
        return movieCollection;
    }

    @Transactional
    public MovieCollection addMovieToCollection(String authToken, int movieId, String collectionName) {
        UserAccount userAccount = fetchUser(authToken);
        MovieItem movie = fetchMovie(movieId);
        MovieCollection movieCollection = fetchCollection(userAccount, collectionName);
        if(isMovieInCollection(movieCollection, movie)) {
            throw new RuntimeException("Movie already in collection: " + movie.getTitle());
        }
        movieCollection.getMovies().add(movie);
        return movieCollectionRepository.save(movieCollection);
    }

    @Transactional
    public MovieCollection removeMovieFromCollection(String authToken, int movieId, String collectionName) {
        UserAccount userAccount = fetchUser(authToken);
        MovieItem movie = fetchMovie(movieId);
        MovieCollection movieCollection = fetchCollection(userAccount, collectionName);
        if(!isMovieInCollection(movieCollection, movie)) {
            throw new RuntimeException("No movie in collection: " + movie.getTitle());
        }
        movieCollection.getMovies().remove(movie);
        return movieCollectionRepository.save(movieCollection);
    }

    @Transactional
    public void deleteCollection(String authToken, String collectionName) {
        UserAccount userAccount = fetchUser(authToken);
        MovieCollection movieCollection = fetchCollection(userAccount, collectionName);
        movieCollectionRepository.delete(movieCollection);
    }

}
