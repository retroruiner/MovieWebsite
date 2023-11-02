package MovieWebsite.service;


import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.MovieCollectionRepository;
import MovieWebsite.repository.MovieItemRepository;
import MovieWebsite.repository.UserRepository;
import lombok.Builder;

import java.util.Date;
import java.util.List;

public class MovieItemService {
    private MovieItemRepository movieItemRepository;
    private MovieCollectionRepository movieCollectionRepository;
    private MovieCollectionService movieCollectionService;
    private UserRepository userRepository;

    public void addMovie(MovieItemData movieItemData) {
        MovieItem movieItem = MovieItem.builder()
                .id(movieItemData.id)
                .duration(movieItemData.duration)
                .rating(movieItemData.rating)
                .title(movieItemData.title)
                .director(movieItemData.director)
                .countryOfOrigin(movieItemData.countryOfOrigin)
                .releaseDate(movieItemData.releaseDate)
                .genreList(movieItemData.genreList)
                .build();

        movieItemRepository.createMovieItem(movieItem);
    }

    public void rateMovie(int userID, String movieName, int rating) {
        UserAccount user = userRepository.findByUserID(userID);
        MovieItem movie = movieItemRepository.findByName(movieName);
        if(movie != null && user != null) {
            //TODO: add range of the rating to check if it's valid
            //if (rating >= 1 && rating <=10

            movie.setRating(rating);
            movieItemRepository.updateMovieItem(movie);
        } else {
            throw new IllegalArgumentException("User or movie does not exist.");
        }
    }
    /*
    public void clearPreviousCollections(UserAccount user, MovieItem movie);
    public void preventDuplicateAddition(UserAccount user, MovieItem movie, String collectionName);
    public List<MovieItem> getMoviesByGenre(String genreName);
    public List<MovieItem> getMoviesByParameters(String parameterName, String parameterValue);
    public List<MovieItem> sortMoviesByParameter(String parameterName);

     */
    @Builder
    public static class MovieItemData {
        private int id;
        private int duration;
        private float rating;
        private String title;
        private String director;
        private String countryOfOrigin;
        private Date releaseDate;
        private List<Genre> genreList;
    }
}
