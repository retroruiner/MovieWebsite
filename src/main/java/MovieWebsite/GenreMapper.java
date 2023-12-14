package MovieWebsite;

import MovieWebsite.dto.GenreDto;
import MovieWebsite.model.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre dtoToGenre(GenreDto genreDto);
    GenreDto genreToDto(Genre genre);
}
