package MovieWebsite.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    @Override
    public String toString() {
        return name;
    }
}
