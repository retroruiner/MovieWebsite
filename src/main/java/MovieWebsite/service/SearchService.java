package MovieWebsite.service;

import MovieWebsite.model.MovieItem;
import MovieWebsite.repository.MovieItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final MovieItemRepository movieItemRepository;

    @Transactional
    public List<MovieItem> simpleSearch(String searchTerm) {
        List<MovieItem> movieItemList = new ArrayList<>();
        for (MovieItem movieItem : movieItemRepository.findByTitleContainingIgnoreCase(searchTerm)) {
            movieItemList.add(movieItem);
        }
        return movieItemList;
    }

    @Transactional
    public List<MovieItem> advancedSearch(String searchTerm) {
        List<MovieItem> allMovies = movieItemRepository.findAll();

        List<MovieItem> result = new ArrayList<>();
        for (MovieItem movie : allMovies) {
            if (levenshteinDistance(searchTerm.toLowerCase(), movie.getTitle().toLowerCase()) <= getThreshold(movie.getTitle())) {
                result.add(movie);
            }
        }

        return result;
    }

    private static int levenshteinDistance( String s1, String s2 ) {
        return dist( s1.toCharArray(), s2.toCharArray() );
    }

    private static int dist( char[] s1, char[] s2 ) {

        // memoize only previous line of distance matrix
        int[] prev = new int[ s2.length + 1 ];

        for( int j = 0; j < s2.length + 1; j++ ) {
            prev[ j ] = j;
        }

        for( int i = 1; i < s1.length + 1; i++ ) {

            // calculate current line of distance matrix
            int[] curr = new int[ s2.length + 1 ];
            curr[0] = i;

            for( int j = 1; j < s2.length + 1; j++ ) {
                int d1 = prev[ j ] + 1;
                int d2 = curr[ j - 1 ] + 1;
                int d3 = prev[ j - 1 ];
                if ( s1[ i - 1 ] != s2[ j - 1 ] ) {
                    d3 += 1;
                }
                curr[ j ] = Math.min( Math.min( d1, d2 ), d3 );
            }

            // define current line of distance matrix as previous
            prev = curr;
        }
        return prev[ s2.length ];
    }

    private int getThreshold(String movieName) {
        //the threshold based on the length of the string
        if (movieName.length() <= 5) {
            return 1;
        } else if (movieName.length() <= 10) {
            return 2;
        } else {
            return 3;
        }
    }

}
