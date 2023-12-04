package MovieWebsite.web;

import MovieWebsite.MovieCollectionMapper;
import MovieWebsite.UserAccountMapper;
import MovieWebsite.dto.MovieCollectionDto;
import MovieWebsite.dto.UserAccountDto;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.MovieCollectionRepository;
import MovieWebsite.service.MovieCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class MovieCollectionController {
    private final MovieCollectionRepository movieCollectionRepository;
    private final MovieCollectionMapper movieCollectionMapper;
    private final UserAccountMapper userAccountMapper;
    private final MovieCollectionService movieCollectionService;

    @PostMapping
    public MovieCollectionDto create(@RequestBody MovieCollectionDto movieCollectionDto, @RequestBody UserAccountDto userAccountDto) {
        movieCollectionDto.setId(0);  //to not update existing movie

        return movieCollectionMapper.movieCollectionToDto(movieCollectionService.createNewCollection(
                userAccountMapper.dtoToUser(userAccountDto).getAuthToken(),
                movieCollectionMapper.dtoToMovieCollection(movieCollectionDto).getName()));
    }

    @GetMapping
    public List<MovieCollectionDto> findAll() {
        return movieCollectionMapper.movieCollectionsToDtos(movieCollectionRepository.findAll());
    }

    @GetMapping("/{id}")
    public MovieCollectionDto findById(@PathVariable int id) {
        return movieCollectionMapper.movieCollectionToDto(movieCollectionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/{collectionName}")
    public void delete(@PathVariable String collectionName, @RequestBody UserAccountDto userAccountDto) {
        UserAccount user = userAccountMapper.dtoToUser(userAccountDto);
        movieCollectionService.deleteCollection(user.getAuthToken(), collectionName);
    }

    @PutMapping("/{id}")
    public MovieCollectionDto update(@PathVariable int id, @RequestBody MovieCollectionDto movieCollectionDto) {
        movieCollectionDto.setId(id);  //to make sure we're updating that one movie

        return movieCollectionMapper.movieCollectionToDto(movieCollectionRepository.save(
                movieCollectionMapper.dtoToMovieCollection(movieCollectionDto)));
    }


}
