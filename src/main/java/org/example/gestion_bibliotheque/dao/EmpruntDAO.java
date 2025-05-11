package org.example.gestion_bibliotheque.dao;

import org.example.gestion_bibliotheque.Roman.Roman;
import org.example.gestion_bibliotheque.database.DatabaseConnection;
import org.example.gestion_bibliotheque.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.example.gestion_bibliotheque.models.Emprunt;
import org.example.gestion_bibliotheque.models.Lecteur;
import org.example.gestion_bibliotheque.models.Livre;
import org.example.gestion_bibliotheque.models.User;
import org.example.gestion_bibliotheque.database.DatabaseConnection;
import java.sql.*;


public class EmpruntDAO {

    // Méthode pour emprunter un livre
    public static void emprunterLivre(int idLivre, String matriculeUser) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Insérer un nouvel emprunt
            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO emprunts (id_livre, matricule_user, date_emprunt,date_retour) VALUES (?, ?, NOW(),NOW())");
            insert.setInt(1, idLivre);
            insert.setString(2, matriculeUser);
            insert.executeUpdate();

            // Mettre à jour le livre pour le marquer comme emprunté (disponible = 0)
            PreparedStatement update = conn.prepareStatement(
                    "UPDATE livres SET disponible = 0 WHERE id = ?");
            update.setInt(1, idLivre);
            update.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Récupérer les emprunts d'un lecteur
    public static List<Emprunt> getEmpruntsByLecteur(String matricule) {
        List<Emprunt> emprunts = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT e.*, l.id AS livre_id, l.titre, l.auteur, l.genre, l.annee_publication, l.disponible, " +
                             "u.matricule AS user_id, u.matricule, u.firstname, u.secondname, u.lastname, u.age, u.role " +
                             "FROM emprunts e " +
                             "JOIN livres l ON e.id_livre = l.id " +
                             "JOIN users u ON e.matricule_user = u.matricule " +
                             "WHERE e.matricule_user = ?"
             )) {

            stmt.setString(1, matricule);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String genre = rs.getString("genre");
                Livre livre;

                // Construction de l'objet livre selon son genre
                switch (genre.toLowerCase()) {
                    case "roman":
                        livre = new Roman(
                                rs.getInt("id_livre"),
                                rs.getString("titre"),
                                rs.getString("auteur"),
                                rs.getString("genre"),
                                rs.getInt("annee_publication"),
                                rs.getBoolean("disponible")
                        );
                        break;

                    case "biographie":
                        livre = new Biographie(
                                rs.getInt("id_livre"),
                                rs.getString("titre"),
                                rs.getString("auteur"),
                                rs.getString("genre"),
                                rs.getInt("annee_publication"),
                                rs.getBoolean("disponible")
                        );
                        break;

                    case "magazine":
                        livre = new Magazine(
                                rs.getInt("id_livre"),
                                rs.getString("titre"),
                                rs.getString("auteur"),
                                rs.getString("genre"),
                                rs.getInt("annee_publication"),
                                rs.getBoolean("disponible")
                        );
                        break;

                    default:
                        System.out.println("Genre inconnu : " + genre);
                        continue; // Si genre inconnu, on passe au suivant
                }

                // Récupération de l'utilisateur (lecteur)
                User lecteur = new User(
                        rs.getString("matricule"),
                        rs.getString("firstname"),
                        rs.getString("secondname"),
                        rs.getString("lastname"),
                        rs.getInt("age"),
                        rs.getString("role")
                );

                // Récupération de la date d'emprunt
                LocalDate dateEmprunt = rs.getDate("date_emprunt").toLocalDate();

                // Gestion de la date de retour qui peut être NULL
                LocalDate dateRetour = null;
                if (rs.getDate("date_retour") != null) {
                    dateRetour = rs.getDate("date_retour").toLocalDate();
                }

                // Création de l'objet Emprunt
                Emprunt emprunt = new Emprunt(
                        livre,
                        lecteur,
                        dateEmprunt,
                        dateRetour,  // Date de retour qui peut être NULL
                        rs.getBoolean("rendu")
                );

                // Ajout de l'emprunt à la liste
                emprunts.add(emprunt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunts;
    }


    // Méthode pour retourner un livre
    public static void retournerLivre(int idLivre, String matriculeUser) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Mettre à jour l'emprunt pour ajouter la date de retour
            PreparedStatement updateEmprunt = conn.prepareStatement(
                    "UPDATE emprunts SET date_retour = NOW(),rendu = TRUE WHERE id_livre = ? AND matricule_user = ?");
            updateEmprunt.setInt(1, idLivre);
            updateEmprunt.setString(2, matriculeUser);
            updateEmprunt.executeUpdate();

            // Mettre à jour le livre pour le rendre disponible
            PreparedStatement updateLivre = conn.prepareStatement(
                    "UPDATE livres SET disponible = 1 WHERE id = ?");
            updateLivre.setInt(1, idLivre);
            updateLivre.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

