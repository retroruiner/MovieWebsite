package MovieWebsite.web;

import MovieWebsite.MovieItemMapper;
import MovieWebsite.dto.MovieItemDto;
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

    @PostMapping
    public MovieItemDto create(@RequestBody MovieItemDto movieItem) {    //TODO: check parameter -> MovieItem
        movieItem.setId(0);  //to not update existing movie

        return movieItemMapper.movieItemToDto(movieItemRepository.save(
                movieItemMapper.dtoToMovieItem(movieItem)));
    }

    @GetMapping
    public List<MovieItemDto> findAll() {
        return movieItemMapper.movieItemstoDtos(movieItemRepository.findAll());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<MovieItemDto> findById(@PathVariable int id) {  //ResponseEntity -> spring mvc specific type that can encapsulate entity response body
//        Optional<MovieItem> movieItemOptional = movieItemRepository.findById(id);
//        if(movieItemOptional.isPresent()) {
//            return ResponseEntity.ok(movieItemMapper.movieItemToDto(movieItemOptional.get()));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

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
