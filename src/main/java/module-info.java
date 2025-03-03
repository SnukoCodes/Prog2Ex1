module at.ac.fhcampuswien.fhmdb {
    requires javafx.fxml;

    requires com.jfoenix;
    requires org.controlsfx.controls;
    requires javafx.graphics;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;
}