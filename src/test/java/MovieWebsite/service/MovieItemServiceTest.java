package MovieWebsite.service;

import MovieWebsite.model.Genre;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.MovieItemRepository;
import MovieWebsite.model.MovieItem;
import MovieWebsite.repository.UserRepository;
import jakarta.transaction.Transactional;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Mock
    UserRepository userRepository;

    @Test
    public void testUpdateRating() {
        MovieItem movieItem = generateMovieItem();
        UserAccount userAccount = generateUserAccount();

        float newRate = 9;
        int numVotes = 2;
        movieItem.setNumOfUsersVoted(numVotes);
        float expected = (movieItem.getRating() * numVotes + newRate) / (++numVotes);

        Mockito.when(userRepository.findByAuthToken(userAccount.getAuthToken())).thenReturn(userAccount);
        Mockito.when(movieItemRepository.findById(movieItem.getId())).thenReturn(Optional.of(movieItem));

        //update rating
        float resultRating = movieItemService.addRating(movieItem.getId(), userAccount.getAuthToken(), newRate);

        verify(movieItemRepository, times(1)).findById(movieItem.getId());
        assertEquals(expected, resultRating);
        assertTrue(userAccount.getRatedMovies().contains(movieItem));

    }

    @Test
    public void testAddMovie() {
        MovieItem movieItem = generateMovieItem();

        Mockito.when(movieItemRepository.save(Mockito.any(MovieItem.class))).thenReturn(movieItem); //TODO: check Mockito

        movieItemService.addMovie(movieItem);

        assertEquals("Inception", movieItem.getTitle());
        assertEquals(255, movieItem.getDuration());
        assertEquals("USA", movieItem.getCountryOfOrigin());
        assertEquals("Mike", movieItem.getDirector());
        assertEquals(releaseDate, movieItem.getReleaseDate());
        assertEquals(genreList, movieItem.getGenreList());
    }


    private MovieItem generateMovieItem() {
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

        return movieItem;
    }

    private UserAccount generateUserAccount() {
        UserService.UserRegistrationData data = UserService.UserRegistrationData.builder()
                .fullName("Eliza Johnson")
                .email("eliza@gmail.com")
                .nickname("LizzyWizzy")
                .password("some_password")
                .dateOfBirth(java.sql.Date.valueOf("2020-01-09"))
                .build();

        return UserAccount.builder()
                .fullName(data.getFullName())
                .nickname(data.getNickname())
                .password(data.getPassword())
                .email(data.getEmail())
                .dateOfBirth(data.getDateOfBirth())
                .build();
    }
}