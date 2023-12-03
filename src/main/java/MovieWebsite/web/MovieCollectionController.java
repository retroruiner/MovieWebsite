package MovieWebsite.web;

import MovieWebsite.MovieCollectionMapper;
import MovieWebsite.dto.MovieCollectionDto;
import MovieWebsite.repository.MovieCollectionRepository;
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

    @PostMapping
    public MovieCollectionDto create(@RequestBody MovieCollectionDto movieCollectionDto) {
        movieCollectionDto.setId(0);  //to not update existing movie

        return movieCollectionMapper.movieCollectionToDto(movieCollectionRepository.save(
                movieCollectionMapper.dtoToMovieCollection(movieCollectionDto)));
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        movieCollectionRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public MovieCollectionDto update(@PathVariable int id, @RequestBody MovieCollectionDto movieCollectionDto) {
        movieCollectionDto.setId(id);  //to make sure we're updating that one movie

        return movieCollectionMapper.movieCollectionToDto(movieCollectionRepository.save(
                movieCollectionMapper.dtoToMovieCollection(movieCollectionDto)));
    }

}
