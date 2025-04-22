module com.example.javafxapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxapplication to javafx.fxml;
    exports com.example.javafxapplication;
}