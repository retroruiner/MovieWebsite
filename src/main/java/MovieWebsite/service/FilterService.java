package MovieWebsite.service;

import MovieWebsite.model.Genre;
import MovieWebsite.model.MovieItem;
import MovieWebsite.repository.MovieItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final MovieItemRepository movieItemRepository;

    public List<MovieItem> filterByGenre(Genre genre) {
        return movieItemRepository.findByGenreListContainsIgnoreCase(genre);
    }

    public List<MovieItem> filterByRatings(float minRating) {
        return movieItemRepository.findByRatingGreaterThanEqual(minRating);
    }
}
