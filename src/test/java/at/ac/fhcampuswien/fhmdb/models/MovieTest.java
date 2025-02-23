package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

        @Test
        void testInitializeMovies () {
            int expected = 5;

            // Call the method to test
            List<Movie> movies = Movie.initializeMovies();

            // Check that the returned list contains exactly 5 movies
            assertEquals( expected, movies.size());
        }

    }
