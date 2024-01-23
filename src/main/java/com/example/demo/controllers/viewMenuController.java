package com.example.demo.controllers;

import com.example.demo.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class viewMenuController implements Initializable {

    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    DatePicker datePicker;
    @FXML
    Label errorMessage;
    @FXML
    TableView<Note> notesTable;
    @FXML
    TableColumn<Note, String> dateColumn;
    @FXML
    TableColumn<Note, String> timeColumn;
    @FXML
    TableColumn<Note, String> noteContentColumn;


    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/note-to-self", "root", "P0ipole803!");
    Statement statement = connection.createStatement();

    ResultSet resultSet;
    ObservableList<Note> notesList = FXCollections.observableArrayList();

    Note selectedNote = null;

    public viewMenuController() throws SQLException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllNotes();

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

    @FXML
    protected void switchToSelectedNoteView(ActionEvent event) throws IOException{
        selectedNote = notesTable.getSelectionModel().getSelectedItem();
        if(selectedNote == null) {
            updateErrorMessage("ERROR: No note selected!");
            return;
        }
        System.out.println(selectedNote.toString());

        FXMLLoader loader = new FXMLLoader(main.class.getResource("view-selected-note.fxml"));
        root = loader.load();

        viewSelectedNoteController vSNC = loader.getController();
        System.out.println(vSNC);
        vSNC.setSelectedNote(selectedNote);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void getAllNotes(){
        try {
            resultSet = statement.executeQuery("SELECT * from notes");
            while(resultSet.next()){
                notesList.add(new Note(Integer.parseInt(resultSet.getString("id")), resultSet.getString("date"), resultSet.getString("time"), resultSet.getString("noteContent")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        populateTable(notesList);
    }

    @FXML
    protected void searchForNotesByDate(ActionEvent event) throws IOException{
        System.out.println(datePicker.getValue());
        notesList.clear();

        try {
            resultSet = statement.executeQuery("SELECT * from notes WHERE date = '" + datePicker.getValue() +"'" );
            while(resultSet.next()){
                notesList.add(new Note(Integer.parseInt(resultSet.getString("id")), resultSet.getString("date"), resultSet.getString("time"), resultSet.getString("noteContent")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        populateTable(notesList);

    }

    private void populateTable(ObservableList<Note> list){
        dateColumn.setCellValueFactory(new PropertyValueFactory<Note,String>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Note,String>("time"));
        noteContentColumn.setCellValueFactory(new PropertyValueFactory<Note,String>("noteContent"));

        notesTable.setItems(list);
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
