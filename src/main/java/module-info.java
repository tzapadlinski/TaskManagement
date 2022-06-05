module com.example.taskmanagement {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;


    opens com.example.taskmanagement to javafx.fxml;
    exports com.example.taskmanagement;
}