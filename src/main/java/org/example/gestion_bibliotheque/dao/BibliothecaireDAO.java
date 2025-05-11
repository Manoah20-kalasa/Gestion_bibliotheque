package org.example.gestion_bibliotheque.dao;


import org.example.gestion_bibliotheque.Roman.Roman;
import org.example.gestion_bibliotheque.database.DatabaseConnection;
import org.example.gestion_bibliotheque.models.Biographie;
import org.example.gestion_bibliotheque.models.Livre;
import org.example.gestion_bibliotheque.models.Magazine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BibliothecaireDAO {



    // Méthode pour obtenir la liste des livres
    public List<Livre> getLivres() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livres";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String genre = rs.getString("genre");
                Livre livre;

                // Construction de l'objet livre selon son genre
                switch (genre.toLowerCase()) {
                    case "roman":
                        livre = new Roman(
                                rs.getInt("id"),
                                rs.getString("titre"),
                                rs.getString("auteur"),
                                rs.getString("genre"),
                                rs.getInt("annee_publication"),
                                rs.getBoolean("disponible")
                        );
                        break;

                    case "biographie":
                        livre = new Biographie(
                                rs.getInt("id"),
                                rs.getString("titre"),
                                rs.getString("auteur"),
                                rs.getString("genre"),
                                rs.getInt("annee_publication"),
                                rs.getBoolean("disponible")
                        );
                        break;

                    case "magazine":
                        livre = new Magazine(
                                rs.getInt("id"),
                                rs.getString("titre"),
                                rs.getString("auteur"),
                                rs.getString("genre"),
                                rs.getInt("annee"),
                                rs.getBoolean("disponible")
                        );
                        break;

                    default:
                        System.out.println("Genre inconnu : " + genre);
                        continue; // Si genre inconnu, on passe au suivant
                }
                livres.add(livre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    // Méthode pour modifier un livre
    public void modifierLivre(Livre livre) {
        String sql = "UPDATE livres SET titre = ?, auteur = ?, genre = ?, annee = ?, disponible = ? WHERE id= ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, livre.getTitre());
            pstmt.setString(2, livre.getAuteur());
            pstmt.setString(3, livre.getGenre());
            pstmt.setInt(4, livre.getAnnee());
            pstmt.setBoolean(5, livre.isDisponible());
            pstmt.setInt(6, livre.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer un livre
    public void supprimerLivre(int id) {
        String sql = "DELETE FROM livres WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

