package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public CheckComboBox<String> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public static List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    private final ObservableList<String> selectedGenres = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    @FXML
    public JFXButton resetBtn;

    public static List<Movie> sortMovies(List<Movie> movies, Boolean ascending) {
        if(ascending) {
            movies.sort(Comparator.comparing(Movie::getTitle));
        }
        else {
            movies.sort(Comparator.comparing(Movie::getTitle).reversed());
        }
        return movies;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.setAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(listView -> new MovieCell()); // use custom cell factory to display data

        resetBtn.setOnAction(actionEvent -> {
            observableMovies.setAll(allMovies);         // add dummy data to observable list
            // initialize UI stuff
            movieListView.setItems(observableMovies);
            movieListView.setCellFactory(listView -> new MovieCell());
        });

        // The following XXX lines of Code have been taken from the Internet | https://stackoverflow.com/questions/13783295/getting-all-names-in-an-enum-as-a-string, last visited 02.03.2025
        String[] genres = Stream.of(Genre.values()).map(Genre::name).toArray(String[]::new);
        genreComboBox.getItems().addAll(genres);
        genreComboBox.setTitle("Filter by Genre");

        // either set event handlers in the fxml file (onAction) or add them here
            searchBtn.setOnAction(actionEvent -> {
                // Get the list of selected genres as strings from the genreComboBox.
                List<String> selectedGenres = genreComboBox.getCheckModel().getCheckedItems();
                String searchQuery = searchField.getText().toLowerCase();

                // Set the filtered list into the ListView.
                ObservableList<Movie> filteredMovies = filterMovies(selectedGenres, searchQuery);
                movieListView.setItems(filteredMovies);
                movieListView.setCellFactory(listView -> new MovieCell());
        });

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                sortMovies(observableMovies, Boolean.TRUE);
                sortBtn.setText("Sort (desc)");
            } else {
                sortMovies(observableMovies, Boolean.FALSE);
                sortBtn.setText("Sort (asc)");
            }
        });
    }

    public static ObservableList<Movie> filterMovies(List<String> selectedGenres, String searchQuery) {
//           // Create a new ObservableList for the filtered movies.
        ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();

        for (Movie movie : allMovies) {
            // Check if the movie's title contains the search text (case-insensitive).
            boolean titleMatches = movie.getTitle().toLowerCase().contains(
                    searchQuery);

            // Split the movie's genres string into individual genres.
            List<String> movieGenres = Arrays.stream(movie.getGenres().split(",\\s*"))
                    .map(String::toLowerCase)
                    .toList();

            // If no genres are selected, we ignore the genre filter.
            boolean genreMatches;
            if (selectedGenres.isEmpty()) {
                genreMatches = true;
            } else {
                // Otherwise, require that the movie contains all selected genres.
                genreMatches = selectedGenres.stream()
                        .map(String::toLowerCase)
                        .allMatch(movieGenres::contains);
            }

            if (titleMatches && genreMatches) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }
}