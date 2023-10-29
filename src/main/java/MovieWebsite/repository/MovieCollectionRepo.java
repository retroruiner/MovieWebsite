package MovieWebsite.repository;

import MovieWebsite.model.MovieCollection;

public interface MovieCollectionRepo {
    public void save(MovieCollection collection);
    public void update(MovieCollection collection);
    public void delete(MovieCollection collection);
    public MovieCollection findById(String id);
}
