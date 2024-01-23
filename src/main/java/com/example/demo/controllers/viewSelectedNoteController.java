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
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class viewSelectedNoteController {

    private Scene scene;
    private Stage stage;
    Note selectedNote;

    @FXML
    private TextArea noteContentArea;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Label mainLabel;
    @FXML
    private Label errorMessage;

    int confirmDeletion = 0;
    String currentNoteContent;

    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/note-to-self", "root", "P0ipole803!");
    Statement statement = connection.createStatement();

    public viewSelectedNoteController() throws SQLException {
    }

    public void setSelectedNote(Note sentNote){
        this.selectedNote = sentNote;
        currentNoteContent = selectedNote.getNoteContent();
        noteContentArea.setText(selectedNote.getNoteContent());
        mainLabel.setText("Note from: " + selectedNote.getDate());
    }



    @FXML
    protected void deleteNote(ActionEvent event) throws IOException, SQLException {
        if(confirmDeletion != 1){
            updateErrorMessage("Click DELETE again to confirm deletion");
            confirmDeletion++;
            return;
        }

        statement.execute("DELETE FROM `note-to-self`.`notes` WHERE (`id` = '" + selectedNote.getId() + "')");

        updateErrorMessage("Note successfully deleted!");
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        noteContentArea.setEditable(false);

    }

    @FXML
    protected void submitNoteEdits(ActionEvent event) throws IOException, SQLException{
        confirmDeletion = 0;
        if(currentNoteContent.equals(noteContentArea.getText())){
            updateErrorMessage("ERROR: Please make an edit before submitting");
            return;
        }

        currentNoteContent = noteContentArea.getText();
        statement.execute("UPDATE `note-to-self`.`notes` SET `noteContent` = '"+ noteContentArea.getText() +"' WHERE (`id` = '" + selectedNote.getId()+ "');");

        updateErrorMessage("Note successfully updated!");
    }

    @FXML
    protected void switchToViewView(ActionEvent event) throws IOException, SQLException {
        connection.close();
        Parent root = FXMLLoader.load(main.class.getResource("view-notes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void switchToMainMenuView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(main.class.getResource("main-menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void updateErrorMessage(String st){
        if(st.startsWith("ERR")){
            errorMessage.setText(st);
            errorMessage.setTextFill((Color.color(1,0,0)));
        } else {
            errorMessage.setText(st);
            errorMessage.setTextFill((Color.color(0,0,1)));
        }
    }
    private void clearErrorMessage(){
        errorMessage.setText("");
    }
}
