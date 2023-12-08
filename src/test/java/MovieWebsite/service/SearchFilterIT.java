package MovieWebsite.service;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import MovieWebsite.repository.MovieItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.BridgeMethodResolver;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase
@SpringBootTest
public class SearchFilterIT {
    @Autowired
    SearchService searchService;

    @Autowired
    FilterService filterService;

    @Autowired
    MovieItemService movieItemService;

    @Autowired
    MovieItemRepository movieItemRepository;

    @Test
    public void testSearch() {

        //simpleSearch
        List<MovieItem> result1 = searchService.simpleSearch("ION");
        assertEquals(2, result1.size());

        List<MovieItem> result2 = searchService.simpleSearch("NotSubstring");
        assertEquals(0, result2.size());

        //Advanced Search with typo
        List<MovieItem> result3 = searchService.advancedSearch("Incerption");

        assertEquals(1, result3.size()); //"Inception" should be the closest matches
        assertEquals("Inception", result3.get(0).getTitle());

    }

    @Test
    public void testFilter() {
        List<MovieItem> result1 = filterService.filterByGenre(Genre.ACTION);
        assertEquals(2, result1.size());

        List<MovieItem> result2 = filterService.filterByGenre(Genre.DRAMA);
        assertEquals(3, result2.size());

        List<MovieItem> result3 = filterService.filterByRatings(3);
        assertEquals(2, result3.size());

        List<MovieItem> result4 = filterService.filterByRatings(4);
        assertEquals(1, result4.size());
    }



    private void generateMovies() {
        MovieItem movieItem1 = new MovieItem();
        movieItem1.setTitle("Inception");
        movieItem1.setGenreList(Arrays.asList(Genre.ACTION, Genre.COMEDY, Genre.DRAMA));
        movieItem1.setRating(3);
        movieItemRepository.save(movieItem1);

        MovieItem movieItem2 = new MovieItem();
        movieItem2.setTitle("Interstellar");
        movieItem2.setGenreList(Arrays.asList(Genre.ROMANCE, Genre.THRILLER, Genre.DRAMA));
        movieItem1.setRating(2);
        movieItemRepository.save(movieItem2);

        MovieItem movieItem3 = new MovieItem();
        movieItem3.setTitle("Redemption");
        movieItem2.setGenreList(Arrays.asList(Genre.ACTION, Genre.ROMANCE, Genre.DRAMA));
        movieItem1.setRating(4);
        movieItemRepository.save(movieItem3);
    }

}
