package MovieWebsite.service;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.MovieItemRepository;
import MovieWebsite.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class InitDbService {
    private UserRepository userRepository;
    private MovieItemRepository movieItemRepository;
    private MovieItemService movieItemService;
    private UserService userService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public void initDb() {
        try {
                //TODO: change user constructor to directly enter date as string
                Date dateOfBirth = dateFormat.parse("19.09.2003");
            Date release = dateFormat.parse("19.09.2023");
            List<Genre> genreList = Arrays.asList(Genre.DRAMA, Genre.ROMANCE);

                UserAccount userAccount1 =
                        UserAccount.builder()
                                .fullName("Ti Lalka")
                                .nickname("nagibator")
                                .password("password1")
                                .email("www@gmail.com")
                                .dateOfBirth(dateOfBirth)
                                .loggedIn(false)
                                .authToken("token")
                        .build();
            userRepository.save(userAccount1);


//            new UserAccount(0, "Ti Lalka", "nagibator", dateOfBirth, "www@gmail.com", "password1", false, "token");

            MovieItem movieItem = MovieItem.builder()
                    .id(12)
                    .duration(23)
                    .rating(3)
                    .title("Паук")
                    .director("Chris")
                    .countryOfOrigin("USA")
                    .releaseDate(release)
                    .genreList(genreList)
                    .build();

            movieItemRepository.save(movieItem);

            System.out.println(userAccount1);

            //movieItemService.rateMovie(0, "Паук", 10);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
