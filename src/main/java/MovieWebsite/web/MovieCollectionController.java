package MovieWebsite.web;

import MovieWebsite.MovieCollectionMapper;
import MovieWebsite.UserAccountMapper;
import MovieWebsite.dto.MovieCollectionDto;
import MovieWebsite.dto.NewCollectionDataDto;
import MovieWebsite.dto.RatingUpdateRequestDto;
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

    @PostMapping("/createCollection")
    public MovieCollectionDto create(@RequestBody NewCollectionDataDto newCollectionData) {
        System.out.println(newCollectionData.getAuthToken());
        return movieCollectionMapper.movieCollectionToDto(movieCollectionService.createNewCollection(newCollectionData.getAuthToken(), newCollectionData.getCollectionName()));
    }

    @PutMapping("/addMovie/{id}")
    public MovieCollectionDto addMovieToCollection(@PathVariable int id, @RequestBody NewCollectionDataDto newCollectionData) {
        return movieCollectionMapper.movieCollectionToDto(movieCollectionService.addMovieToCollection(newCollectionData.getAuthToken(), id, newCollectionData.getCollectionName()));
    }

    @DeleteMapping("/removeMovie/{id}")
    public MovieCollectionDto removeMovieFromCollection(@PathVariable int id, @RequestBody NewCollectionDataDto newCollectionData) {
        return movieCollectionMapper.movieCollectionToDto(movieCollectionService.removeMovieFromCollection(newCollectionData.getAuthToken(), id, newCollectionData.getCollectionName()));
    }

    @GetMapping("/findAll/{id}")
    public List<MovieCollectionDto> findAll(@PathVariable int id) {
        return movieCollectionMapper.movieCollectionsToDtos(movieCollectionRepository.findAllByUserId(id));
    }

    @GetMapping("/{id}")
    public MovieCollectionDto findById(@PathVariable int id) {
        return movieCollectionMapper.movieCollectionToDto(movieCollectionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/deleteCollection")
    public void delete(@RequestParam String collectionName, @RequestParam String authToken) {
        movieCollectionService.deleteCollection(authToken, collectionName);
    }
}
