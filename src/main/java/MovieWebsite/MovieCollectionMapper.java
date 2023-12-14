package MovieWebsite;

import MovieWebsite.dto.MovieCollectionDto;
import MovieWebsite.model.MovieCollection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieCollectionMapper {
    MovieCollectionDto movieCollectionToDto(MovieCollection movieCollection);

    List<MovieCollectionDto> movieCollectionsToDtos(List<MovieCollection> movieCollections);

    MovieCollection dtoToMovieCollection(MovieCollectionDto movieCollectionDto);
}
