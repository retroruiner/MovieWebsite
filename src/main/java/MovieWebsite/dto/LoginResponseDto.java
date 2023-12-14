package MovieWebsite.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String authToken;
    private int userId;
}