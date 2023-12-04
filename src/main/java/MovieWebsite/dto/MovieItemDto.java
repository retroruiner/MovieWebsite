package MovieWebsite.dto;

import MovieWebsite.model.Genre;
import MovieWebsite.model.UserAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MovieItemDto {
    private int id;
    private  int numOfUsersVoted = 0;
    private float duration;
    private float rating = 0;
    private String title;
    private String director;
    private String countryOfOrigin;
    private Date releaseDate;
    private List<GenreDto> genreList = new ArrayList<>();
    private List<UserAccountDto> ratedByUsers = new ArrayList<>();
}
