package MovieWebsite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingUpdateRequestDto {
    private String authToken;
    private float rating;
}
