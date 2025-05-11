package org.example.gestion_bibliotheque.dao;

import org.example.gestion_bibliotheque.database.DatabaseConnection;
import org.example.gestion_bibliotheque.models.Biographie;
import org.example.gestion_bibliotheque.models.Livre;
import org.example.gestion_bibliotheque.models.Magazine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    public static
    List<Livre> getAllDisponibles() {
        List<Livre> livres = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM livres WHERE disponible = 1")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                livres.add(creerLivreDepuisResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    public static Livre creerLivreDepuisResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String titre = rs.getString("titre");
        String auteur = rs.getString("auteur");
        String genre = rs.getString("genre");
        int annee = rs.getInt("annee_publication");
        boolean disponible = rs.getBoolean("disponible");
        String type = rs.getString("type");

        return switch (type.toLowerCase()) {
            case "biographie" -> new Biographie(id, titre, auteur, genre, annee, disponible);
            case "magazine" -> new Magazine(id, titre, auteur, genre, annee, disponible);
            default -> throw new IllegalArgumentException("Type de livre inconnu : " + type);
        };
    }

    public static List<Livre> search(String keyword) {
        List<Livre> livres = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM livres WHERE disponible = 1 AND (titre LIKE ? OR auteur LIKE ? OR genre LIKE ?)")) {
            String query = "%" + keyword + "%";
            stmt.setString(1, query);
            stmt.setString(2, query);
            stmt.setString(3, query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                livres.add(creerLivreDepuisResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    public static void ajouterLivre(Livre livre) {
        String sql = "INSERT INTO livres (titre, auteur, genre, annee_publication, disponible, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getGenre());
            stmt.setInt(4, livre.getAnnee());
            stmt.setBoolean(5, livre.isDisponible());
            stmt.setString(6, livre.getType()); // Important pour savoir s'il s'agit d'un Roman, Biographie, etc.
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

