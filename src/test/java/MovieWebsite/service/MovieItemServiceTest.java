package MovieWebsite.service;

import MovieWebsite.model.Genre;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.MovieItemRepository;
import MovieWebsite.model.MovieItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MovieItemServiceTest {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    Date releaseDate = null;

    List<Genre> genreList = Arrays.asList(Genre.ACTION, Genre.COMEDY, Genre.DRAMA);
    @InjectMocks
    MovieItemService movieItemService;

    @Mock
    MovieItemRepository movieItemRepository;

    @Test
    public void testUpdateRating() {
        try {
            releaseDate = dateFormat.parse("26.07.2003");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        MovieItemService.MovieItemData movieItemData = new MovieItemService.MovieItemData(
                255,
                "Inception",
                "Mike",
                "USA",
                releaseDate,
                genreList
        );

        MovieItem movieItem = MovieItem.builder()
                .duration(movieItemData.getDuration())
                .title(movieItemData.getTitle())
                .director(movieItemData.getDirector())
                .countryOfOrigin(movieItemData.getCountryOfOrigin())
                .releaseDate(movieItemData.getReleaseDate())
                .genreList(movieItemData.getGenreList())
                .build();

        float newRate = 9;
        int numVotes = 2;
        movieItem.setNumOfUsersVoted(numVotes);
        float expected = (movieItem.getRating() * numVotes + newRate) / (++numVotes);

        Mockito.when(movieItemRepository.findById(movieItem.getId())).thenReturn(Optional.of(movieItem));

        movieItemService.updateRating(movieItem.getId(), newRate);

        verify(movieItemRepository, times(1)).findById(movieItem.getId());
        //verify(movieItemRepository, times(1)).save(movieItem);
        assertEquals(expected, movieItem.getRating());

    }

    @Test
    public void testAddMovie() {
        try {
            releaseDate = dateFormat.parse("26.07.2020");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        MovieItemService.MovieItemData movieItemData = new MovieItemService.MovieItemData(
                255,
                "Inception",
                "Mike",
                "USA",
                releaseDate,
                genreList
        );

        MovieItem movieItem = MovieItem.builder()
                .duration(movieItemData.getDuration())
                .title(movieItemData.getTitle())
                .director(movieItemData.getDirector())
                .countryOfOrigin(movieItemData.getCountryOfOrigin())
                .releaseDate(movieItemData.getReleaseDate())
                .genreList(movieItemData.getGenreList())
                .build();

        Mockito.when(movieItemRepository.save(Mockito.any(MovieItem.class))).thenReturn(movieItem); //TODO: check Mockito

        movieItemService.addMovie(movieItem);

        assertEquals("Inception", movieItem.getTitle());
        assertEquals(255, movieItem.getDuration());
        assertEquals("USA", movieItem.getCountryOfOrigin());
        assertEquals("Mike", movieItem.getDirector());
        assertEquals(releaseDate, movieItem.getReleaseDate());
        assertEquals(genreList, movieItem.getGenreList());
    }
}