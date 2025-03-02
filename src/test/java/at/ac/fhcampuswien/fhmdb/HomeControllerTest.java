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
    void testSortMovies_Ascending_NullList() {
        // Expect a NullPointerException when passing a null list.
        assertThrows(NullPointerException.class, () -> {
            HomeController.sortMovies(null, Boolean.TRUE);
        });
    }

    @Test
    void testSortMovies_Ascending_EmptyList() {
        List<Movie> emptyList = new ArrayList<>();
        List<Movie> sorted = HomeController.sortMovies(emptyList, Boolean.TRUE);
        assertNotNull(sorted, "Sorted list should not be null");
        assertTrue(sorted.isEmpty(), "Sorted list should be empty");
    }

    @Test
    void testSortMovies_Ascending_SingleElement() {
        Movie movie = new Movie("Single", "A single movie", List.of(Genre.DRAMA));
        List<Movie> list = new ArrayList<>(Collections.singletonList(movie));
        List<Movie> sorted = HomeController.sortMovies(list, Boolean.TRUE);
        assertEquals(1, sorted.size(), "Sorted list should contain one element");
        assertEquals(movie, sorted.get(0), "The single movie should remain unchanged");
    }

    @Test
    void testSortMovies_Ascending_Duplicates() {
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
    void testSortMovies_Ascending_Sort() {
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
    void testSortMovies_Descending_NullList() {
        // Expect a NullPointerException when passing a null list.
        assertThrows(NullPointerException.class, () -> {
            HomeController.sortMovies(null, Boolean.FALSE);
        });
    }

    @Test
    void testSortMovies_Descending_EmptyList() {
        List<Movie> emptyList = new ArrayList<>();
        List<Movie> sorted = HomeController.sortMovies(emptyList, Boolean.FALSE);
        assertNotNull(sorted, "Sorted list should not be null");
        assertTrue(sorted.isEmpty(), "Sorted list should be empty");
    }

    @Test
    void testSortMovies_Descending_SingleElementDescending() {
        Movie movie = new Movie("Single", "A single movie", List.of(Genre.DRAMA));
        List<Movie> list = new ArrayList<>(Collections.singletonList(movie));
        List<Movie> sorted = HomeController.sortMovies(list, Boolean.FALSE);
        assertEquals(1, sorted.size(), "Sorted list should contain one element");
        assertEquals(movie, sorted.get(0), "The single movie should remain unchanged");
    }

    @Test
    void testSortMovies_Descending_Duplicates() {
        // Create two movies with identical titles and one with a different title.
        Movie movie1 = new Movie("A Movie", "First instance", List.of(Genre.DRAMA));
        Movie movie2 = new Movie("A Movie", "Second instance", List.of(Genre.COMEDY));
        Movie movie3 = new Movie("B Movie", "Unique movie", List.of(Genre.ACTION));

        List<Movie> list = new ArrayList<>();
        // Add in unsorted order.
        list.add(movie1);
        list.add(movie3);
        list.add(movie2);

        List<Movie> sorted = HomeController.sortMovies(list, Boolean.FALSE);

        // In descending order, "B Movie" should appear first followed by both "A Movie" entries.
        assertEquals("B Movie", sorted.get(0).getTitle());
        assertEquals("A Movie", sorted.get(1).getTitle());
        assertEquals("A Movie", sorted.get(2).getTitle());
    }

    @Test
    void testSortMovies_Descending_Sort() {
        List<Movie> movies = Movie.initializeMovies();

        // Call the function that sorts movies in ascending order (by title)
        List<Movie> sortedMovies = HomeController.sortMovies(movies, Boolean.FALSE);

        // Check that the list is sorted in ascending order by comparing adjacent titles.
        for (int i = 0; i < sortedMovies.size() - 1; i++) {
            String currentTitle = sortedMovies.get(i).getTitle();
            String nextTitle = sortedMovies.get(i + 1).getTitle();
            assertTrue(currentTitle.compareTo(nextTitle) >= 0, "Movie titles are not in descending order: "
                    + currentTitle + " should come after " + nextTitle);
        }

    }

    @Test
    void filter_Movies() {
        List<String> genres = new ArrayList<String>();
        genres.add(Genre.ANIMATION.toString());

        List<String> results = new ArrayList<>();

        List<Movie> movies = HomeController.filterMovies(genres, "");

        for (Movie mov : movies.stream().toList()){
            results.add(mov.getTitle());
        }

        assertTrue(results.contains("Toy Story"));
        assertTrue(results.contains("The Lion King"));
    }
}