package com.example.demo.controllers;

import com.example.demo.main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class newNoteController {

    private Scene scene;
    private Stage stage;
    private Parent root;
    @FXML
    private Label mainLabel;
    @FXML
    private TextArea textArea;
    @FXML
    private Button backButton;
    @FXML
    private Button submitButton;

    @FXML
    private Label errorMessage;

    @FXML
    protected void switchToMainMenuView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(main.class.getResource("main-menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void processSubmission(ActionEvent event) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/note-to-self", "root", "P0ipole803!");
        Statement statement = connection.createStatement();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = LocalDate.now().format(formatter);

        String currentTime = LocalTime.now(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("H:mm"));
        String noteContent = textArea.getText();

        if(noteContent.length() > 500){
            updateErrorMessage("ERROR: Note is too long! " + noteContent.length() + "/500 characters used.");
            return;
        }

        statement.execute("INSERT INTO `note-to-self`.`notes`(`id`, `date`, `time` , `noteContent`) VALUES (0, '" + currentDate + "', '" + currentTime + "', '" + noteContent + "')");

        connection.close();
        updateErrorMessage("New note received! Thank you.");

        textArea.setDisable(true);
        submitButton.setDisable(true);
    }

    private void updateErrorMessage(String st){
        if(st.startsWith("ERR")){
            errorMessage.setText(st);
            return;
        } else {
            errorMessage.setText(st);
            errorMessage.setTextFill((Color.color(0,0,1)));
        }
    }
    private void clearErrorMessage(){
        errorMessage.setText("");
    }
}
