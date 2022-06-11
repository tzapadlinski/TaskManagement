package com.example.taskmanagement;

import com.structure.Manager;
import com.structure.ProjectLog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LogController implements Initializable {

    private ArrayList<String> logNames;
    private ProjectLog log;

    @FXML
    private ListView<String> raporty;

    @FXML
    private Button button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //tutaj
        logNames = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/accountbase", "root", "");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM logs");
            while (resultSet.next()) {
                logNames.add(resultSet.getString("fileName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //albo w staticu :(
        raporty.getItems().addAll(logNames);
        raporty.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                log = ProjectLog.loadProject(raporty.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void pressed(ActionEvent e) throws IOException {
        if (log == null)
            return;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logView.fxml"));
        Parent root = fxmlLoader.load();
        LogViewerController controller = fxmlLoader.getController();
        controller.display(log.getFileName(), log.toString());
        //Parent root = FXMLLoader.load(getClass().getResource("logView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Raport");
        stage.setScene(new Scene(root));
        stage.show();
    }



}
