package MovieWebsite.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@Builder
public class MovieItem {
    private int id;
    private int duration;
    private float rating;
    private String title;
    private String director;
    private String countryOfOrigin;
    private Date releaseDate;
    private List<Genre> genreList;


    public void addGenre(Genre genre) {
        verifyGenreListExistence();
        if(!validateIfGenreExists(genre)) {
            this.genreList.add(genre);
            genre.addMovie(this);
        }
    }

    public void removeGenre(Genre genre) {
        verifyGenreListExistence();
        if(validateIfGenreExists(genre)) {
            this.genreList.remove(genre);
            genre.removeMovie(this);
        }
    }

    private boolean validateIfGenreExists(Genre genre) {
        for (Genre g: genreList) {
            if(g == genre){
                return true;
            }
        }
        return false;
    }

    private void verifyGenreListExistence() {
        if(this.genreList == null) {
            this.genreList = new ArrayList<>();
        }
    }
}

