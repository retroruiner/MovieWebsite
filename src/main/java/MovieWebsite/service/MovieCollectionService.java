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
    private final MovieCollectionRepository movieCollectionRepository;
    private final UserRepository userRepository;
    private final MovieItemRepository movieItemRepository;

    public boolean isMovieInCollection(MovieCollection collection, MovieItem movie) {
        if (movieCollectionRepository.getMovieInCollection(collection, movie) == null)
            return false;

        return true;
    }
    public void checkUserCollectionExistence(int userID, int movieCollectionId) {
        if (!(userRepository.existsById(userID)) || !(movieCollectionRepository.existsById(movieCollectionId))) throw new IllegalArgumentException("Invalid user or collection.");
    }

    @Transactional
    public void createNewCollection(int userID, String collectionName) {
        MovieCollection movieCollection = new MovieCollection(collectionName);
        Optional<UserAccount> userOptional = userRepository.findById(userID);
        UserAccount user = userOptional.get();
        checkUserCollectionExistence(userID, movieCollection.getId());
        user.createNewCollection(collectionName);

        movieCollectionRepository.save(movieCollection);
        userRepository.save(user); //creates
    }

    public void addMovieToCollection(int userID, String movieName, int collectionID) {
        modifyMovieInCollection(userID, movieName, collectionID, true);
    }

    public void removeMovieFromCollection(int userID, String movieName, int collectionID) {
        modifyMovieInCollection(userID, movieName, collectionID, false);
    }

    @Transactional
    public void modifyMovieInCollection(int userID, String movieName, int collectionID, boolean add) {
        Optional<UserAccount> userOptional = userRepository.findById(userID);
        UserAccount userAccount = userOptional.get();
        MovieItem movie = movieItemRepository.findByName(movieName);
        MovieCollection collection = movieCollectionRepository.getById(collectionID);

        checkUserCollectionExistence(userID, collection.getId());

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
                movieCollectionRepository.save(collection); //updates
            } else {
                throw new IllegalArgumentException("The movie is not in the collection.");
            }
        }

        userRepository.save(userAccount); //updates
    }


    @Transactional
    public void deleteCollection(int userID, int collectionID) {
        Optional<UserAccount> userOptional = userRepository.findById(userID);
        UserAccount user = userOptional.get();
        MovieCollection collection = movieCollectionRepository.getById(collectionID);
        checkUserCollectionExistence(userID, collection.getId());
        if (user.getListOfCollections().contains(collection)) {
            user.deleteCollection(collection);

            movieCollectionRepository.delete(collection);
            userRepository.save(user);  //updates
        } else {
            throw new IllegalArgumentException("The user does not have this collection.");
        }
    }
}
