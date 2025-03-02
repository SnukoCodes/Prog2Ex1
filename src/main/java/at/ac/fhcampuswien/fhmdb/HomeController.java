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

        // TODO add genre filter items with genreComboBox.getItems().addAll(...)

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // The following XXX lines of Code have been taken from the Internet | https://stackoverflow.com/questions/13783295/getting-all-names-in-an-enum-as-a-string, last visited 02.03.2025
        String[] genres = Stream.of(Genre.values()).map(Genre::name).toArray(String[]::new);
        genreComboBox.getItems().addAll(genres);
        genreComboBox.setTitle("Filter by Genre");

        genreComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c ->{
            selectedGenres.clear();
            selectedGenres.addAll(genreComboBox.getCheckModel().getCheckedItems());
            observableMovies.setAll(filterMovies(selectedGenres,searchField.getText()));
        });
        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                // TODO sort observableMovies ascending
                sortMovies(observableMovies, Boolean.TRUE);
                sortBtn.setText("Sort (desc)");
            } else {
                // TODO sort observableMovies descending
                sortMovies(observableMovies, Boolean.FALSE);
                sortBtn.setText("Sort (asc)");
            }
        });
    }

    public static List<Movie> filterMovies(List<String> selectedGenres, String searchQuery) {
        Set<String> seenTitles = new HashSet<>();
        List<Movie> filteredMovies = new ArrayList<>();

        for (Movie movie : allMovies) {
            boolean matchesGenre = selectedGenres == null || selectedGenres.isEmpty() || selectedGenres.get(0).isEmpty();

            for (int i = 0; i < movie.getGenresAsList().size() && (!matchesGenre); i++) {
                matchesGenre = selectedGenres.contains(movie.getGenresAsList().get(i));
            }

            boolean matchesQuery = searchQuery == null || searchQuery.isEmpty() || movie.getTitle().toLowerCase().contains(searchQuery.toLowerCase())
                    || (movie.getDescription() != null && movie.getDescription().toLowerCase().contains(searchQuery.toLowerCase()));


            if (matchesGenre && matchesQuery && seenTitles.add(movie.getTitle().toLowerCase())) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }
}