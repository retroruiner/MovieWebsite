package MovieWebsite.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum GenreDto {
    ACTION("Action"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    ROMANCE("Romance"),
    THRILLER("Thriller"),
    SCI_FI("Science fiction");

    private String name;
    GenreDto(String name) {
        this.name = name;
    }
}
