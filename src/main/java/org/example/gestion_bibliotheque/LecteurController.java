package org.example.gestion_bibliotheque;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.gestion_bibliotheque.dao.EmpruntDAO;
import org.example.gestion_bibliotheque.dao.LivreDAO;
import org.example.gestion_bibliotheque.models.Emprunt;
import org.example.gestion_bibliotheque.models.Livre;
import org.example.gestion_bibliotheque.models.Lecteur;

import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class LecteurController {

    @FXML
    private Label lecteurNomLabel;

    @FXML
    private TableView<Livre> livresTable;

    @FXML
    private TableColumn<Livre, String> titreLivreColumn;

    @FXML
    private TableColumn<Livre, String> auteurLivreColumn;

    @FXML
    private TableColumn<Livre, String> genreLivreColumn;

    @FXML
    private TableView<Emprunt> empruntsTable;

    @FXML
    private TableColumn<Emprunt, String> titreEmpruntColumn;

    @FXML
    private TableColumn<Emprunt, String> dateEmpruntColumn;

    @FXML
    private TableColumn<Emprunt, String> dateRetourColumn;

    private String matricule;



    public void initialize() {
        titreLivreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        auteurLivreColumn.setCellValueFactory(cellData -> cellData.getValue().auteurProperty());
        genreLivreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());

        titreEmpruntColumn.setCellValueFactory(cellData -> cellData.getValue().livreTitreProperty());
        dateEmpruntColumn.setCellValueFactory(cellData -> cellData.getValue().dateEmpruntStrProperty());
        dateRetourColumn.setCellValueFactory(cellData -> cellData.getValue().dateRetourStrProperty());

        loadLivres();
        
    }

    public void setLecteur(String nom, String mat) {
        lecteurNomLabel.setText("Nom Du Lecteur: " + nom);
        this.matricule = mat;
        loadEmprunts();
    }

    private void loadLivres() {
        List<Livre> livres = LivreDAO.getAllDisponibles();
        livresTable.getItems().setAll(livres);
    }

    private void loadEmprunts() {
            List<Emprunt> emprunts = EmpruntDAO.getEmpruntsByLecteur(matricule);
            empruntsTable.getItems().setAll(emprunts);
    }

    @FXML
    private void emprunterLivre() {

        Livre livreSelectionne = livresTable.getSelectionModel().getSelectedItem();
        if (livreSelectionne != null) {
            EmpruntDAO.emprunterLivre(livreSelectionne.getId(), matricule);
            loadLivres();
            loadEmprunts();
        } else {
            showAlert("Erreur", "Sélectionnez un livre à emprunter.");
        }
    }

    @FXML
    private void retournerLivre() {
        Emprunt empruntSelectionne = empruntsTable.getSelectionModel().getSelectedItem();
        if (empruntSelectionne != null) {
            EmpruntDAO.retournerLivre(empruntSelectionne.getLivre().getId(), matricule);
            loadLivres();
            loadEmprunts();
        } else {
            showAlert("Erreur", "Sélectionnez un emprunt à retourner.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
}
