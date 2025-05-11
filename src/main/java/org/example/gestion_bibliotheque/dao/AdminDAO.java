package org.example.gestion_bibliotheque.dao;

import org.example.gestion_bibliotheque.database.DatabaseConnection;
import org.example.gestion_bibliotheque.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    public void addUser(String matricule, String firstname, String secondname, String lastname, int age, String role) {
        String sql = "INSERT INTO users (matricule, firstname, secondname, lastname, age, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, matricule);
            pstmt.setString(2, firstname);
            pstmt.setString(3, secondname);
            pstmt.setString(4, lastname);
            pstmt.setInt(5, age);
            pstmt.setString(6, role);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(String oldMatricule, String matricule, String firstname, String secondname, String lastname, int age, String role) {
        String sql = "UPDATE users SET matricule = ?, firstname = ?, secondname = ?, lastname = ?, age = ?, role = ? WHERE matricule = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, matricule);
            pstmt.setString(2, firstname);
            pstmt.setString(3, secondname);
            pstmt.setString(4, lastname);
            pstmt.setInt(5, age);
            pstmt.setString(6, role);
            pstmt.setString(7, oldMatricule);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String matricule) {
        String sql = "DELETE FROM users WHERE matricule = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, matricule);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("matricule"),
                        rs.getString("firstname"),
                        rs.getString("secondname"),
                        rs.getString("lastname"),
                        rs.getInt("age"),
                        rs.getString("role")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


}
