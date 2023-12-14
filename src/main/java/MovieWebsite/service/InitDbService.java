package MovieWebsite.service;
import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitDbService {
    private final MovieItemService movieItemService;

    // Add some data to the database
    @Transactional
    public void initDb() {
        List<Genre> genreList = new ArrayList<>(Arrays.asList(Genre.SCI_FI, Genre.ACTION));
        MovieItem movieItem = MovieItem.builder()
                    .duration(2.5f)
                    .title("Cyberpunk 2077")
                    .director("Josh Graham")
                    .countryOfOrigin("Poland")
                    .releaseDate(java.sql.Date.valueOf("2020-12-20"))
                    .genreList(genreList)
                    .image("https://upload.wikimedia.org/wikipedia/en/9/9f/Cyberpunk_2077_box_art.jpg")
                    .build();

            movieItemService.addMovie(movieItem);
    }
}
