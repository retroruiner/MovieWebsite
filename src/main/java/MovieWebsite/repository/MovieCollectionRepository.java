package MovieWebsite.repository;

import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;

public interface MovieCollectionRepository {
    void createCollection(MovieCollection collection);
    void addMovieToCollection(MovieItem movie, MovieCollection collection);
    void updateCollection(MovieCollection collection);
    void deleteCollection(MovieCollection collection);
    MovieCollection getCollectionById(int id);
    MovieCollection getCollectionByName(String name);
    MovieItem getMovieInCollection(MovieCollection collection, MovieItem movieItem);
}
