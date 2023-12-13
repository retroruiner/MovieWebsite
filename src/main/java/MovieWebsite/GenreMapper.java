package MovieWebsite;

import MovieWebsite.dto.GenreDto;
import MovieWebsite.dto.MovieCollectionDto;
import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieCollection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre dtoToGenre(GenreDto genreDto);
    GenreDto genreToDto(Genre genre);
}
