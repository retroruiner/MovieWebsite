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

@RequiredArgsConstructor
@Service
public class MovieCollectionService {
    private MovieCollectionRepository movieCollectionRepository;
    private UserRepository userRepository;
    private MovieItemRepository movieItemRepository;

    public boolean isMovieInCollection(MovieCollection collection, MovieItem movie) {
        if (movieCollectionRepository.getMovieInCollection(collection, movie) == null)
            return false;

        return true;
    }
    public void checkUserCollectionExistence(UserAccount userAccount, MovieCollection collection) {
        if (userAccount == null || collection == null) throw new IllegalArgumentException("Invalid user or collection.");
    }

    public void createNewCollection(int userID, String collectionName) {
        MovieCollection movieCollection = new MovieCollection(collectionName);
        UserAccount user = userRepository.findByUserID(userID);
        checkUserCollectionExistence(user, movieCollection);
        user.createNewCollection(collectionName);

        movieCollectionRepository.createCollection(movieCollection);
        userRepository.updateUserAccount(user);
    }

    public void addMovieToCollection(int userID, String movieName, int collectionID) {
        modifyMovieInCollection(userID, movieName, collectionID, true);
    }

    public void removeMovieFromCollection(int userID, String movieName, int collectionID) {
        modifyMovieInCollection(userID, movieName, collectionID, false);
    }

    private void modifyMovieInCollection(int userID, String movieName, int collectionID, boolean add) {
        UserAccount userAccount = userRepository.findByUserID(userID);
        MovieItem movie = movieItemRepository.findByName(movieName);
        MovieCollection collection = movieCollectionRepository.getCollectionById(collectionID);

        checkUserCollectionExistence(userAccount, collection);

        if (add) {
            if (!isMovieInCollection(collection, movie)) {
                userAccount.addMovieToCollection(movie, collection);
                movieCollectionRepository.addMovieToCollection(movie, collection);
            } else {
                throw new IllegalArgumentException("The movie is already in the collection.");
            }
        } else {
            if (isMovieInCollection(collection, movie)) {
                userAccount.deleteCollection(collection);
                movieCollectionRepository.updateCollection(collection);
            } else {
                throw new IllegalArgumentException("The movie is not in the collection.");
            }
        }

        userRepository.updateUserAccount(userAccount);
    }

    public void deleteCollection(int userID, int collectionID) {
        UserAccount user = userRepository.findByUserID(userID);
        MovieCollection collection = movieCollectionRepository.getCollectionById(collectionID);
        checkUserCollectionExistence(user, collection);
        if (user.getListOfCollections().contains(collection)) {
            user.deleteCollection(collection);

            movieCollectionRepository.deleteCollection(collection);
            userRepository.updateUserAccount(user);
        } else {
            throw new IllegalArgumentException("The user does not have this collection.");
        }
    }
}
