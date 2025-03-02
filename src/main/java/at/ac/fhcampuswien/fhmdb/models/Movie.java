package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Movie {
    private String title;
    private String description;
    private List<Genre> genres;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = new ArrayList<>(genres);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenres() {
        return genres.stream()
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    public List<String> getGenresAsList(){
        return genres.stream().map(Enum::name).toList();
    }

    public static List<Movie> initializeMovies() {
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

        movies.add(new Movie("The Matrix",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));

        movies.add(new Movie("Forrest Gump",
                "A man with a low IQ recounts his extraordinary life journey, unexpectedly influencing historic events along the way.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE)));

        movies.add(new Movie("Gladiator",
                "A betrayed Roman general seeks revenge against a corrupt emperor while fighting for justice and honor.",
                Arrays.asList(Genre.ACTION, Genre.DRAMA, Genre.ADVENTURE)));

        movies.add(new Movie("The Lion King",
                "A young lion prince flees his kingdom after his father's death, only to learn about responsibility, courage, and reclaiming his birthright.",
                Arrays.asList(Genre.ANIMATION, Genre.FAMILY, Genre.DRAMA)));

        movies.add(new Movie("Pulp Fiction",
                "Intertwined stories of crime and redemption bring together a series of eclectic characters in a gritty urban setting.",
                Arrays.asList(Genre.CRIME, Genre.DRAMA)));

        return movies;
    }
}
