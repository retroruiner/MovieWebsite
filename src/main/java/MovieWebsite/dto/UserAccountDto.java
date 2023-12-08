package MovieWebsite.dto;

import MovieWebsite.model.MovieItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserAccountDto {
    private int id;
    private String fullName;
    private String nickname;
    private Date dateOfBirth;
    private String email;
    private String password;
    private boolean loggedIn;
    private String authToken;
    private List<MovieCollectionDto> listOfCollections = new ArrayList<>();
    private List<UserAccountDto> friendList = new ArrayList<>();
    private List<MovieItemDto> ratedMovies = new ArrayList<>();

}
