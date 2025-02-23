package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    private List<Genre> genres;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Life is Beautiful",
                "When an open-minded Jewish librarian and his son become victims of the Holocaust, " +
                        "he uses a perfect mixture of will, humor and imagination to protect his son from the dangers around their camp.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE)));

        movies.add(new Movie("The Shawshank Redemption",
                "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                Arrays.asList(Genre.DRAMA, Genre.CRIME)));

        movies.add(new Movie("Inception",
                "A skilled thief, the absolute best in the dangerous art of extraction, steals valuable secrets from deep within the subconscious.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION, Genre.THRILLER)));

        movies.add(new Movie("Toy Story",
                "A cowboy doll is profoundly threatened and jealous when a new spaceman figure supplants him as top toy in a boy's room.",
                Arrays.asList(Genre.ANIMATION, Genre.FAMILY, Genre.COMEDY)));

        movies.add(new Movie("The Godfather",
                "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
                Arrays.asList(Genre.DRAMA, Genre.CRIME)));
        return movies;
    }
}
