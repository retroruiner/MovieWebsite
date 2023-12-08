package MovieWebsite;

import MovieWebsite.dto.MovieCollectionDto;
import MovieWebsite.dto.MovieItemDto;
import MovieWebsite.dto.UserAccountDto;
import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
    UserAccountDto userToDto(UserAccount userAccount);

    List<UserAccountDto> usersToDtos(List<UserAccount> users);

    UserAccount dtoToUser(UserAccountDto userAccountDto);

    @Mapping(target = "userAccountDto", ignore = true)
    MovieCollectionDto movieCollectionToDto(MovieCollection collection);
    
    @Mapping(target = "ratedByUsers", ignore = true)
    MovieItemDto movieItemToDto(MovieItem movieItem);

}
