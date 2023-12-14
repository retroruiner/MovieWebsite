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
    @Transactional   // fetch the collection within the same transaction where it was loaded
    public void testAddRemoveMovieFromCollection() {
        MovieItem movieItem = generateMovieItem();
        movieItemRepository.save(movieItem);

        UserAccount userAccount = generateUserAccount();
        userRepository.save(userAccount);

        MovieCollection movieCollection = new MovieCollection("Watch Later", userAccount);
        movieCollectionRepository.save(movieCollection);

        //add
        movieCollectionService.addMovieToCollection(userAccount.getAuthToken(), movieItem.getId(), movieCollection.getName());

        MovieCollection collectionWithMovie = movieCollectionRepository.findByNameAndUserAccount(movieCollection.getName(), userAccount)
                .orElseThrow(() -> new RuntimeException("Collection not found"));
        UserAccount updatedUser = userRepository.findById(userAccount.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertNotNull(collectionWithMovie);
        assertTrue(movieCollectionService.isMovieInCollection(collectionWithMovie, movieItem));


        //remove
        movieCollectionService.removeMovieFromCollection(userAccount.getAuthToken(), movieItem.getId(), movieCollection.getName());

        // Verify that the movie is not in the collection anymore
        MovieCollection collectionWithoutMovie = movieCollectionRepository.findByNameAndUserAccount(movieCollection.getName(), userAccount)
                .orElseThrow(() -> new RuntimeException("Collection not found"));
        assertFalse(movieCollectionService.isMovieInCollection(collectionWithoutMovie, movieItem));
    }

    @Test
    public void testCreateDeleteNewCollection() {
        UserAccount userAccount = generateUserAccount();
        userRepository.save(userAccount);

        //Create
        movieCollectionService.createNewCollection(userAccount.getAuthToken(), "Test New Collection");

        Optional<MovieCollection> movieCollection = movieCollectionRepository.findByNameAndUserAccount("Test New Collection", userAccount);
        assertNotNull(movieCollection.get());
        assertEquals(userAccount, movieCollection.get().getUserAccount());

        //Testing with non-empty collection
        MovieItem movieItem = generateMovieItem();
        movieItemRepository.save(movieItem);
        movieCollectionService.addMovieToCollection(userAccount.getAuthToken(), movieItem.getId(), "Test New Collection");

        //Delete
        movieCollectionService.deleteCollection(userAccount.getAuthToken(), "Test New Collection");

        Optional<MovieCollection> deletedMovieCollection = movieCollectionRepository.findByNameAndUserAccount("Test New Collection", userAccount);
        assertTrue(deletedMovieCollection.isEmpty());
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

    private MovieItem generateMovieItem() {
        List<Genre> genreList = Arrays.asList(Genre.ACTION, Genre.COMEDY, Genre.DRAMA);

        return MovieItem.builder()
                .duration(23)
                .rating(2)
                .title("Love")
                .director("Katty")
                .countryOfOrigin("Thailand")
                .releaseDate(java.sql.Date.valueOf("2009-01-09"))
                .genreList(genreList)
                .build();
    }
}
