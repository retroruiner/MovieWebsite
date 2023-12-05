package MovieWebsite.service;


import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.MovieCollectionRepository;
import MovieWebsite.repository.MovieItemRepository;
import MovieWebsite.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieItemService {
    private final MovieItemRepository movieItemRepository;
    private final MovieCollectionRepository movieCollectionRepository;
    private final MovieCollectionService movieCollectionService;
    private final UserRepository userRepository;

    @Transactional
    public MovieItem addMovie(MovieItem movieItem) {
        if(isMovieExistent(movieItem.getTitle())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Movie " + movieItem.getTitle() + " already exist");
        }
        return movieItemRepository.save(movieItem);
    }

    private boolean isMovieExistent(String movieName) {
        return movieItemRepository.findByTitle(movieName).isPresent();
    }

    @Transactional
    public float addRating(int movieId, String authToken, float rating) {
        MovieItem movieItem = fetchMovie(movieId);
        UserAccount userAccount = fetchUser(authToken);
        if(!userAccount.getRatedMovies().contains(movieItem)) {
            float newRating = calculateRating(movieItem.getRating(), movieItem.getNumOfUsersVoted(), rating);
            updateMovieRating(movieItem, newRating);
            addUserRatedMovie(userAccount, movieItem);
            return newRating;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already rated this movie");
        }
    }

    private void updateMovieRating(MovieItem movieItem, float newRating) {
        movieItem.setRating(newRating);
        movieItem.setNumOfUsersVoted(movieItem.getNumOfUsersVoted() + 1);
        movieItemRepository.save(movieItem);
    }

    private void addUserRatedMovie(UserAccount userAccount, MovieItem movieItem) {
        userAccount.getRatedMovies().add(movieItem);
        userRepository.save(userAccount);
    }

    private float calculateRating(float previousRating, int numVotes, float newRating) {
        return (previousRating * numVotes + newRating) / (++numVotes);
    }

    private UserAccount fetchUser(String authToken) {
        UserAccount user = userRepository.findByAuthToken(authToken);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }
        return user;
    }

    private MovieItem fetchMovie(int movieID) {
        Optional<MovieItem> movieItemOptional = movieItemRepository.findById(movieID);
        return movieItemOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie does not exist"));
    }

    @Getter
    @Builder
    public static class MovieItemData {
        private int duration;
        private String title;
        private String director;
        private String countryOfOrigin;
        private Date releaseDate;
        private List<Genre> genreList;
    }
}
