package org.example.gestion_bibliotheque;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.gestion_bibliotheque.Roman.Roman;
import org.example.gestion_bibliotheque.dao.BibliothecaireDAO;
import org.example.gestion_bibliotheque.dao.LivreDAO;
import org.example.gestion_bibliotheque.models.Biographie;
import org.example.gestion_bibliotheque.models.Livre;
import org.example.gestion_bibliotheque.models.Magazine;

import java.util.List;

public class BibliothecaireController {

    @FXML private TextField titreField;
    @FXML private TextField auteurField;
    @FXML private TextField genreField;
    @FXML private TextField anneeField;
    @FXML private CheckBox disponibleCheckBox;
    @FXML private TableView<Livre> tableLivres;
    @FXML private TableColumn<Livre, String> titreColumn;
    @FXML private TableColumn<Livre, String> auteurColumn;
    @FXML private TableColumn<Livre, String> genreColumn;
    @FXML private TableColumn<Livre, Integer> anneeColumn;
    @FXML private TableColumn<Livre, Boolean> disponibleColumn;

    private BibliothecaireDAO bibliothecaireDAO = new BibliothecaireDAO();

    @FXML
    public void initialize() {
        // Initialisation des colonnes de la TableView
        titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        auteurColumn.setCellValueFactory(cellData -> cellData.getValue().auteurProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        anneeColumn.setCellValueFactory(cellData -> cellData.getValue().anneeProperty().asObject());
        disponibleColumn.setCellValueFactory(cellData -> cellData.getValue().disponibleProperty().asObject());

        // Charger les livres dans la TableView
        loadLivres();
    }

    // Charger tous les livres dans la TableView
    private void loadLivres() {
        List<Livre> livres = bibliothecaireDAO.getLivres();
        tableLivres.getItems().setAll(livres);
    }

    // Ajouter un nouveau livre
    @FXML
    public void ajouterLivre() {
        String titre = titreField.getText();
        String auteur = auteurField.getText();
        String genre = genreField.getText();
        int annee = Integer.parseInt(anneeField.getText());
        boolean disponible = disponibleCheckBox.isSelected();

        Livre nouveauLivre;
        switch (genre.toLowerCase()) {
            case "roman":
                nouveauLivre = new Roman(0, titre, auteur, genre, annee, disponible);
                break;
            case "biographie":
                nouveauLivre = new Biographie(0, titre, auteur, genre, annee, disponible);
                break;
            case "magazine":
                nouveauLivre = new Magazine(0, titre, auteur, genre, annee, disponible);
                break;
            default:
                showAlert("Genre inconnu", "Le genre doit être 'roman', 'biographie' ou 'magazine'.");
                return;
        }
        LivreDAO.ajouterLivre(nouveauLivre);
        loadLivres();
        // Recharger les livres dans la TableView
    }
    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Supprimer un livre sélectionné
    @FXML
    public void supprimerLivre() {
        Livre livreSelectionne = tableLivres.getSelectionModel().getSelectedItem();
        if (livreSelectionne != null) {
            bibliothecaireDAO.supprimerLivre(livreSelectionne.getId());
            loadLivres();  // Recharger les livres après suppression
        }
    }

    // Mettre à jour un livre sélectionné
    @FXML
    public void mettreAJourLivre() {
        Livre livreSelectionne = tableLivres.getSelectionModel().getSelectedItem();
        if (livreSelectionne != null) {
            livreSelectionne.setTitre(titreField.getText());
            livreSelectionne.setAuteur(auteurField.getText());
            livreSelectionne.setGenre(genreField.getText());
            livreSelectionne.setAnnee(Integer.parseInt(anneeField.getText()));
            livreSelectionne.setDisponible(disponibleCheckBox.isSelected());
            bibliothecaireDAO.modifierLivre(livreSelectionne);
            loadLivres();  // Recharger les livres après mise à jour
        }
    }
}
