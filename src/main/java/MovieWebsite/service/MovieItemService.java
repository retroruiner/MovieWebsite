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
import org.springframework.stereotype.Service;

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
    public void addMovie(MovieItem movieItem) {
        if(isMovieExistent(movieItem.getTitle())) {
            throw new RuntimeException("Movie " + movieItem.getTitle() + " already exists");
        }
        movieItemRepository.save(movieItem);
    }

    private boolean isMovieExistent(String movieName) {
        return movieItemRepository.findByTitle(movieName).isPresent();
    }

    @Transactional
    public void updateRating(int movieId, float rating) {
        Optional<MovieItem> movieItemFromDB = movieItemRepository.findById(movieId);
        MovieItem movieItem = movieItemFromDB.get();
        float newRating = calculateRating(movieItem.getRating(), movieItem.getNumOfUsersVoted(), rating);
        movieItem.setRating(newRating);
        //movieItemRepository.save(movieItem);

    }

    private float calculateRating(float previousRating, int numVotes, float newRating) {
        return (previousRating * numVotes + newRating) / (++numVotes);
    }


    /*
    public void clearPreviousCollections(UserAccount user, MovieItem movie);
    public void preventDuplicateAddition(UserAccount user, MovieItem movie, String collectionName);
    public List<MovieItem> getMoviesByParameters(String parameterName, String parameterValue);
    public List<MovieItem> sortMoviesByParameter(String parameterName);
     */

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
