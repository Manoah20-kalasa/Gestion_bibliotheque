package org.example.gestion_bibliotheque;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.gestion_bibliotheque.dao.AdminDAO;
import org.example.gestion_bibliotheque.database.DatabaseConnection;
import org.example.gestion_bibliotheque.models.User;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LoginController {
    @FXML private TextField usermatriculeField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {

        List<User> users = new ArrayList<User>();
        users = AdminDAO.getAllUsers();
        for(User user : users) {
            if(user.getMatricule().equals(usermatriculeField.getText())) {
                String name = user.getFirstname();
                String mat = user.getMatricule();
                String role = user.getRole();
                String fxmlFile = switch (role){
                    case "admin" -> "AdminPage.fxml";
                    case "lecteur" ->"LecteurPage.fxml";
                    case "bibliothecaire" ->"BibliothecairePage.fxml";
                    default -> null;
                };
                FXMLLoader loader =new  FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();
                LecteurController lecteurController = loader.getController();
                lecteurController.setLecteur(name,mat);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Z-Library application - Login");
                stage.show();
            }else {
                errorLabel.setText("Invalid username");
            }
        }
    }

}
