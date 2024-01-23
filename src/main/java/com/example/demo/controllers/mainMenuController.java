package com.example.demo.controllers;

import com.example.demo.main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class mainMenuController implements Initializable {
    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    private Button newNoteButton;
    @FXML
    private Button editNoteButton;
    @FXML
    private Label dateLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.now();
        String currentDate = dtf.format(localDate);
        dateLabel.setText("The current date is " + currentDate);
    }

    @FXML
    protected void switchToNewNoteView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(main.class.getResource("new-note.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void switchToViewView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(main.class.getResource("view-notes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}