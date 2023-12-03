package MovieWebsite;

import MovieWebsite.dto.MovieCollectionDto;
import MovieWebsite.dto.MovieItemDto;
import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieCollectionMapper {
    MovieCollectionDto movieCollectionToDto(MovieCollection movieCollection);

    List<MovieCollectionDto> movieCollectionsToDtos(List<MovieCollection> movieCollections);

    MovieCollection dtoToMovieCollection(MovieCollectionDto movieCollectionDto);
}
