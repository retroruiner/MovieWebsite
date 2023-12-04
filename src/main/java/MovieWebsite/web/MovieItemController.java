package MovieWebsite.web;

import MovieWebsite.MovieItemMapper;
import MovieWebsite.UserAccountMapper;
import MovieWebsite.dto.MovieItemDto;
import MovieWebsite.dto.RatingUpdateRequestDto;
import MovieWebsite.dto.UserAccountDto;
import MovieWebsite.model.MovieItem;
import MovieWebsite.repository.MovieItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieItemController {
    private final MovieItemMapper movieItemMapper;
    private final MovieItemRepository movieItemRepository;

    @PostMapping("/create")
    public MovieItemDto create(@RequestBody MovieItemDto movieItem) {
        movieItem.setId(0);  //to not update existing movie
        return movieItemMapper.movieItemToDto(movieItemService.addMovie(movieItemMapper.dtoToMovieItem(movieItem)));
    }

    @GetMapping("/findAll")
    public List<MovieItemDto> findAll() {
        return movieItemMapper.movieItemstoDtos(movieItemRepository.findAll());
    }

    @GetMapping("/{id}")
    public MovieItemDto findById(@PathVariable int id) {
        return movieItemMapper.movieItemToDto(movieItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        movieItemRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public MovieItemDto update(@PathVariable int id, @RequestBody MovieItemDto movieItemDto) {
        movieItemDto.setId(id);  //to make sure we're updating that one movie
        return movieItemMapper.movieItemToDto(movieItemRepository.save(
                movieItemMapper.dtoToMovieItem(movieItemDto)));
    }


}
