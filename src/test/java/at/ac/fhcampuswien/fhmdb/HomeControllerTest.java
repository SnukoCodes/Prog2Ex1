package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    // Ascending order tests
    @Test
    void testSortMoviesAscendingNullList() {
        // Expect a NullPointerException when passing a null list.
        assertThrows(NullPointerException.class, () -> {
            HomeController.sortMovies(null, Boolean.TRUE);
        });
    }

    @Test
    void testSortMoviesAscendingEmptyList() {
        List<Movie> emptyList = new ArrayList<>();

        List<Movie> sorted = HomeController.sortMovies(emptyList, Boolean.TRUE);

        assertNotNull(sorted, "Sorted list should not be null");
        assertTrue(sorted.isEmpty(), "Sorted list should be empty");
    }

    @Test
    void testSortMoviesAscendingSingleElement() {
        Movie movie = new Movie("Single", "A single movie", List.of(Genre.DRAMA));
        List<Movie> list = new ArrayList<>(Collections.singletonList(movie));

        List<Movie> sorted = HomeController.sortMovies(list, Boolean.TRUE);

        assertEquals(1, sorted.size(), "Sorted list should contain one element");
        assertEquals(movie, sorted.get(0), "The single movie should remain unchanged");
    }

    @Test
    void testSortMoviesAscendingDuplicates() {
        // Create two movies with identical titles and one with a different title.
        Movie movie1 = new Movie("A Movie", "First instance", List.of(Genre.DRAMA));
        Movie movie2 = new Movie("A Movie", "Second instance", List.of(Genre.COMEDY));
        Movie movie3 = new Movie("B Movie", "Unique movie", List.of(Genre.ACTION));

        List<Movie> list = new ArrayList<>();
        // Add in unsorted order.
        list.add(movie3);
        list.add(movie1);
        list.add(movie2);

        List<Movie> sorted = HomeController.sortMovies(list, Boolean.TRUE);

        // Expect both "A Movie" entries to appear before "B Movie"
        assertEquals("A Movie", sorted.get(0).getTitle());
        assertEquals("A Movie", sorted.get(1).getTitle());
        assertEquals("B Movie", sorted.get(2).getTitle());
    }

    @Test
    void testSortMoviesAscendingSort() {
        List<Movie> movies = Movie.initializeMovies();

        // Call the function that sorts movies in ascending order (by title)
        List<Movie> sortedMovies = HomeController.sortMovies(movies, Boolean.TRUE);

        // Check that the list is sorted in ascending order by comparing adjacent titles.
        for (int i = 0; i < sortedMovies.size() - 1; i++) {
            String currentTitle = sortedMovies.get(i).getTitle();
            String nextTitle = sortedMovies.get(i + 1).getTitle();
            assertTrue(currentTitle.compareTo(nextTitle) <= 0, "Movie titles are not in ascending order: "
                    + currentTitle + " should come before " + nextTitle);
        }
    }

    // Descending order tests

    @Test
    void testSortMoviesDescendingNullList() {
        // Expect a NullPointerException when passing a null list.
        assertThrows(NullPointerException.class, () -> {
            HomeController.sortMovies(null, Boolean.FALSE);
        });
    }

    @Test
    void testSortMoviesDescendingEmptyList() {
        List<Movie> emptyList = new ArrayList<>();

        List<Movie> sorted = HomeController.sortMovies(emptyList, Boolean.FALSE);

        assertNotNull(sorted, "Sorted list should not be null");
        assertTrue(sorted.isEmpty(), "Sorted list should be empty");
    }

    @Test
    void testSortMoviesDescendingSingleElementDescending() {

        Movie movie = new Movie("Single", "A single movie", List.of(Genre.DRAMA));
        List<Movie> list = new ArrayList<>(Collections.singletonList(movie));

        List<Movie> sorted = HomeController.sortMovies(list, Boolean.FALSE);

        assertEquals(1, sorted.size(), "Sorted list should contain one element");
        assertEquals(movie, sorted.get(0), "The single movie should remain unchanged");
    }

    @Test
    void testSortMoviesDescendingDuplicates() {
        //Arrange
        // Create two movies with identical titles and one with a different title.
        Movie movie1 = new Movie("A Movie", "First instance", List.of(Genre.DRAMA));
        Movie movie2 = new Movie("A Movie", "Second instance", List.of(Genre.COMEDY));
        Movie movie3 = new Movie("B Movie", "Unique movie", List.of(Genre.ACTION));

        List<Movie> list = new ArrayList<>();
        // Add in unsorted order.
        list.add(movie1);
        list.add(movie3);
        list.add(movie2);

        //Act
        List<Movie> sorted = HomeController.sortMovies(list, Boolean.FALSE);

        //Assert
        // In descending order, "B Movie" should appear first followed by both "A Movie" entries.
        assertEquals("B Movie", sorted.get(0).getTitle());
        assertEquals("A Movie", sorted.get(1).getTitle());
        assertEquals("A Movie", sorted.get(2).getTitle());
    }

    @Test
    void testSortMoviesDescendingSort() {
        //Arrange
        List<Movie> movies = Movie.initializeMovies();

        //Act
        List<Movie> sortedMovies = HomeController.sortMovies(movies, Boolean.FALSE);

        //Assert
        for (int i = 0; i < sortedMovies.size() - 1; i++) {
            String currentTitle = sortedMovies.get(i).getTitle();
            String nextTitle = sortedMovies.get(i + 1).getTitle();
            assertTrue(currentTitle.compareTo(nextTitle) >= 0, "Movie titles are not in descending order: "
                    + currentTitle + " should come after " + nextTitle);
        }

    }

    @Test
    void testFilterMoviesOneGenre() {
        //Arrange
        List<String> genres = new ArrayList<String>();
        genres.add(Genre.ANIMATION.toString());

        //Act
        List<Movie> results = HomeController.filterMovies(genres, "");

        //Assert
        for (Movie mov : results) {
            assertTrue(mov.getGenres().contains(Genre.ANIMATION.toString()));
        }
    }

    @Test
    void testFilterMoviesTwoGenres() {
        //Arrange
        List<String> genres = new ArrayList<String>();
        genres.add(Genre.ACTION.toString());
        genres.add(Genre.DRAMA.toString());

        //Act
        List<Movie> results = HomeController.filterMovies(genres, "");

        //Assert
        for (Movie mov : results) {
            assertTrue(mov.getGenres().contains(Genre.ACTION.toString()));
            assertTrue(mov.getGenres().contains(Genre.DRAMA.toString()));
        }
    }

    @Test
    void testFilterMoviesOneGenreWithSearch() {
        // Arrange
        List<String> genres = new ArrayList<>();
        genres.add(Genre.ACTION.toString());
        String searchString = "The";

        // Act
        List<Movie> results = HomeController.filterMovies(genres, searchString);

        // Assert
        for (Movie mov : results) {
            assertTrue(mov.getGenres().contains(Genre.ACTION.toString()),
                    "Movie " + mov.getTitle() + " does not contain ACTION");
            assertTrue(mov.getTitle().toLowerCase().contains(searchString.toLowerCase()),
                    "Movie " + mov.getTitle() + " does not contain search string: " + searchString);
        }
    }
}