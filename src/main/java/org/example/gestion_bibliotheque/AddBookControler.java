package org.example.gestion_bibliotheque;

import javafx.fxml.FXML;

import java.awt.*;

public class AddBookControler {


    @FXML
    public Button add;
    @FXML
    public javafx.scene.control.TextField TitleBook;
    @FXML
    public javafx.scene.control.TextField BookAuthor;
    @FXML
    public javafx.scene.control.TextField BookKind;
    @FXML
    public javafx.scene.control.TextField BookName;
    @FXML
    public javafx.scene.control.TextField PublicationYear;
    @FXML
    public javafx.scene.control.TextField IsbnBook;
    @FXML
    public javafx.scene.control.Button AddUser;


    public void AddBookButtonAction(javafx.event.ActionEvent actionEvent) {
        System.out.println("AddBookButtonAction");
    }
}
