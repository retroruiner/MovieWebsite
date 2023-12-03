package MovieWebsite.service;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.MovieItemRepository;
import MovieWebsite.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitDbService {
//    private final UserRepository userRepository;
//    private final MovieItemRepository movieItemRepository;
//    private final MovieItemService movieItemService;
//    private final UserService userService;
//    private final MovieCollectionService movieCollectionService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Transactional
    public void initDb() {
//            List<Genre> genreList = new ArrayList<>(Arrays.asList(Genre.SCI_FI, Genre.THRILLER));

//            UserAccount userAccount1 =
//                        UserAccount.builder()
//                                .fullName("Hello Sooronbaev")
//                                .nickname("hella")
//                                .password("password1")
//                                .email("s.sddfd@gmail.com")
////                                .dateOfBirth(java.sql.Date.valueOf("2003-07-26"))
//                                .loggedIn(false)
////                                .authToken("token")
//                        .build();
//            userService.registerUser(userAccount1);
//
//
//            MovieItem movieItem = MovieItem.builder()
//                    .duration(2.5f)
//                    .title("Cyberpunk 2077")
//                    .director("CD Projekt Red")
//                    .countryOfOrigin("Poland")
//                    .releaseDate(java.sql.Date.valueOf("2020-12-20"))
//                    .genreList(genreList)
//                    .build();
//
//            movieItemService.addMovie(movieItem);

//            movieItemService.updateRating(952, 5.6f);
//        movieCollectionService.createNewCollection(352, "Arigato");

    }
}
