package MovieWebsite.dto;

import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MovieCollectionDto {
    private int id;
    private String name;
    private List<MovieItemDto> movies = new ArrayList<>();
    private UserAccountDto userAccountDto;
}
