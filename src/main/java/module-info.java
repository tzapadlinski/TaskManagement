module com.example.taskmanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.taskmanagement to javafx.fxml;
    exports com.example.taskmanagement;
}