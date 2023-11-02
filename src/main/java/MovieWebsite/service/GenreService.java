package MovieWebsite.service;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import MovieWebsite.repository.GenreRepository;
import MovieWebsite.repository.MovieCollectionRepository;
import MovieWebsite.repository.MovieItemRepository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenreService {
    private GenreRepository genreRepository;
    private MovieItemRepository movieItemRepository;

    public  GenreService (GenreRepository genreRepository, MovieItemRepository movieItemRepository) {
        this.genreRepository = genreRepository;
        this.movieItemRepository = movieItemRepository;
    }

    public void addGenreToMovie(String genreName, int movieId) {
        MovieItem movie = movieItemRepository.findById(movieId);
        Genre genre = genreRepository.findGenreByName(genreName);
        if(isMovieAndGenreExist(movie, genre) && !isGenreInMovie(movie, genre)) {
            movieItemRepository.addGenreToMovie(movie, genre);
            movie.addGenre(genre);
        }
    }

    private boolean isMovieAndGenreExist(MovieItem movie, Genre genre) { //TODO: change genre parameter
        return !(movie == null || genre == null);
    }

    private boolean isGenreInMovie(MovieItem movie, Genre genre) {
        ArrayList<String> listOfGenres = movieItemRepository.findGenresOfMovie(movie);
        return listOfGenres.contains(genre.getName());
    }

    public List<Genre> getAllGenres() {
        return genreRepository.getAllGenres();
    }

    public List<MovieItem> getMoviesByGenre(String genre) {
        return genreRepository.getMoviesByGenre(genre);
    }
}
