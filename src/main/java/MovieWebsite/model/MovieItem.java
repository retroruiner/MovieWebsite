package MovieWebsite.model;


import lombok.Getter;
import lombok.Lombok;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter

public class MovieItem {
    private int id;
    private int duration;
    private float rating;
    private String name;
    private String director;
    private String countryOfOrigin;
    private Date releaseDate;
    private ArrayList<Genre> genreList;

    public MovieItem(MovieItemBuilder builder) {
        this.id = builder.id;
        this.duration = builder.duration;
        this.rating = builder.rating;
        this.name = builder.name;
        this.director = builder.director;
        this.countryOfOrigin = builder.countryOfOrigin;
        this.releaseDate = builder.releaseDate;
        this.genreList = new ArrayList<>();
    }

    public void addGenre(Genre genre) {
        this.genreList.add(genre);
        genre.addMovie(this);
    }

    @Accessors(chain = true)
    @Setter
    public static class MovieItemBuilder {
        private int id;
        private int duration;
        private float rating;
        private String name;
        private String director;
        private String countryOfOrigin;
        private Date releaseDate;

        public MovieItem build(){
            return new MovieItem(this);
        }
    }

}

