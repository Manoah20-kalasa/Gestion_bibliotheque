package org.example.gestion_bibliotheque;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.gestion_bibliotheque.dao.AdminDAO;
import org.example.gestion_bibliotheque.models.User;

public class AdminController {

    public Button AddUserButton;
    AdminDAO adminDAO = new AdminDAO();
    ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML private TextField MatriculeField;
    @FXML private TextField FirstNameField;
    @FXML private TextField SecondNameField;
    @FXML private TextField LastNameField;
    @FXML private TextField AgeField;
    @FXML private TextField RoleField;

    @FXML private TableView<User> TableUser;
    @FXML private TableColumn<User, String> matriculeColumn;
    @FXML private TableColumn<User, String> FirstNameColumn;
    @FXML private TableColumn<User, String> SecondNameColumn;
    @FXML private TableColumn<User, String> LastNameColumn;
    @FXML private TableColumn<User, Integer> AgeColumn;
    @FXML private TableColumn<User, String> RoleColumn;

    @FXML
    public void initialize() {
        matriculeColumn.setCellValueFactory(cellData -> cellData.getValue().matriculeProperty());

        FirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        SecondNameColumn.setCellValueFactory(cellData -> cellData.getValue().secondnameProperty());
        LastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        AgeColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        RoleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());


        userList.addAll(adminDAO.getAllUsers());
        TableUser.setItems(userList);

    }

    @FXML
    public void AddUserButtonAction() {
        String matricule = MatriculeField.getText();
        String firstName = FirstNameField.getText();
        String secondName = SecondNameField.getText();
        String lastName = LastNameField.getText();
        int age;
        try {
            age = Integer.parseInt(AgeField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Âge invalide");
            alert.setContentText("Veuillez entrer un âge valide (nombre entier).");
            alert.showAndWait();
            return;
        }

        String role = RoleField.getText();

        if (matricule.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || role.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Champs obligatoires manquants");
            alert.setContentText("Veuillez remplir tous les champs requis.");
            alert.showAndWait();
            return;
        }

        // Ajouter l'utilisateur dans la base de données
        adminDAO.addUser(matricule, firstName, secondName, lastName, age, role);

        // Ajouter l'utilisateur dans la liste et mettre à jour le tableau
        User newUser = new User(matricule, firstName, secondName, lastName, age, role);
        userList.add(newUser);

        clearFields();
    }

    @FXML
    public void onEditUser() {
        User selectedUser = TableUser.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Remplir les champs de texte avec les informations de l'utilisateur sélectionné
            MatriculeField.setText(selectedUser.getMatricule());
            FirstNameField.setText(selectedUser.getFirstname());
            SecondNameField.setText(selectedUser.getSecondname());
            LastNameField.setText(selectedUser.getLastname());
            AgeField.setText(String.valueOf(selectedUser.getAge()));
            RoleField.setText(selectedUser.getRole());

            // Lorsque l'utilisateur clique sur "Enregistrer" les modifications seront appliquées
            AddUserButton.setText("Enregistrer Modifications");
            AddUserButton.setOnAction(event -> saveEditedUser(selectedUser));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur à modifier.");
            alert.showAndWait();
        }
    }

    private void saveEditedUser(User selectedUser) {
        String matricule = MatriculeField.getText();
        String firstName = FirstNameField.getText();
        String secondName = SecondNameField.getText();
        String lastName = LastNameField.getText();
        int age;

        try {
            age = Integer.parseInt(AgeField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Âge invalide");
            alert.setContentText("Veuillez entrer un âge valide (nombre entier).");
            alert.showAndWait();
            return;
        }

        String role = RoleField.getText();

        if (matricule.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || role.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Champs obligatoires manquants");
            alert.setContentText("Veuillez remplir tous les champs requis.");
            alert.showAndWait();
            return;
        }

        // Modifier l'utilisateur dans la base de données
        adminDAO.updateUser(selectedUser.getMatricule(), matricule, firstName, secondName, lastName, age, role);

        // Mettre à jour l'utilisateur dans la liste et dans la table
        selectedUser.setMatricule(matricule);
        selectedUser.setFirstname(firstName);
        selectedUser.setSecondname(secondName);
        selectedUser.setLastname(lastName);
        selectedUser.setAge(age);
        selectedUser.setRole(role);

        TableUser.refresh();

        // Remise à zéro des champs
        clearFields();
    }

    @FXML
    public void onDeleteUser() {
        User selectedUser = TableUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            adminDAO.deleteUser(selectedUser.getMatricule());
            userList.remove(selectedUser);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur à supprimer.");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        MatriculeField.clear();
        FirstNameField.clear();
        SecondNameField.clear();
        LastNameField.clear();
        AgeField.clear();
        RoleField.clear();

        // Réinitialise le bouton pour l'ajout d'un utilisateur
        AddUserButton.setText("Ajouter Utilisateur");
        AddUserButton.setOnAction(event -> AddUserButtonAction());
    }
}
