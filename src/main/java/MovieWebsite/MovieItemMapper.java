package MovieWebsite;

import MovieWebsite.dto.MovieItemDto;
import MovieWebsite.model.MovieItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")   //to make sure the component will have spring bean
public interface MovieItemMapper {
    MovieItemDto movieItemToDto(MovieItem movieItem);

    List<MovieItemDto> movieItemstoDtos(List<MovieItem> movieItems);

    MovieItem dtoToMovieItem(MovieItemDto movieItemDto);

}


