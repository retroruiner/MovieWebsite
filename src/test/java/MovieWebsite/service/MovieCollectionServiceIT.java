package MovieWebsite.service;


import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.MovieCollectionRepository;
import MovieWebsite.repository.MovieItemRepository;
import MovieWebsite.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@SpringBootTest
public class MovieCollectionServiceIT {

    @Autowired
    MovieCollectionService movieCollectionService;

    @Autowired
    MovieCollectionRepository movieCollectionRepository;

    @Autowired
    MovieItemService movieItemService;

    @Autowired
    MovieItemRepository movieItemRepository;

    @Autowired
    UserRepository userRepository;



    @Test
    public void testAddMovieToCollection() {
        List<Genre> genreList = Arrays.asList(Genre.ACTION, Genre.COMEDY, Genre.DRAMA);
        MovieItem movieItem = MovieItem.builder()
                .duration(23)
                .rating(2)
                .title("Love")
                .director("Katty")
                .countryOfOrigin("Thailand")
                .releaseDate(java.sql.Date.valueOf("2009-01-09"))
                .genreList(genreList)
                .build();
        movieItemRepository.save(movieItem);

        UserAccount userAccount = generateUserAccount();
        userRepository.save(userAccount);

        MovieCollection movieCollection = new MovieCollection("Watch Later", userAccount);
        movieCollectionRepository.save(movieCollection);

        //When
        movieCollectionService.addMovieToCollection(userAccount.getId(), movieItem.getTitle(), movieCollection.getName());

        //Then
        UserAccount updatedUser = userRepository.findById(userAccount.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertNotNull(movieCollection);
        assertTrue(movieCollection.getMovies().contains(movieItem));

        Optional<MovieCollection> updatedCollection = movieCollectionRepository.findById(movieCollection.getId());
        assertTrue(updatedCollection.isPresent());
        assertTrue(updatedCollection.get().getMovies().contains(movieItem));

    }

    @Transactional
    @Test
    public void testCreateNewCollection() {
        UserAccount userAccount = generateUserAccount();
        userRepository.save(userAccount);

        movieCollectionService.createNewCollection(userAccount.getId(), "Test New Collection");

        Optional<MovieCollection> movieCollection = movieCollectionRepository.findByNameAndUserAccount("Test New Collection", userAccount);
        assertNotNull(movieCollection.get());
        assertEquals(userAccount, movieCollection.get().getUserAccount());
    }




    /*
    @Test
    public void testIsMovieInCollection() {
        // Given
        //UserAccount userAccount = generateUserAccount();
        UserService.UserRegistrationData data = UserService.UserRegistrationData.builder()
                .fullName("Eliza Johnson")
                .email("eliza@gmail.com")
                .nickname("LizzyWizzy")
                .password("some_password")
                .dateOfBirth(java.sql.Date.valueOf("2020-01-09"))
                .build();

        UserAccount userAccount = UserAccount.builder()
                .fullName(data.getFullName())
                .nickname(data.getNickname())
                .password(data.getPassword())
                .email(data.getEmail())
                .dateOfBirth(data.getDateOfBirth())
                .build();

        userRepository.save(userAccount);

        MovieCollection movieCollection = new MovieCollection("Action Movies", userAccount);
        movieCollectionRepository.save(movieCollection);

        MovieItem movieItem1 = new MovieItem();
        MovieItem movieItem2 = new MovieItem();
        movieItemRepository.save(movieItem1);
        movieItemRepository.save(movieItem2);

        movieCollection.getMovies().add(movieItem1);

        System.out.println();

        // When
        boolean inCollection = movieCollectionService.isMovieInCollection(movieCollection, movieItem1);
        boolean notInCollection = movieCollectionService.isMovieInCollection(movieCollection, movieItem2);

        // Then
        assertTrue(inCollection);
        assertFalse(notInCollection);
    }

     */


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
