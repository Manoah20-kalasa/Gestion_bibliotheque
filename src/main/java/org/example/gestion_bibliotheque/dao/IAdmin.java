package org.example.gestion_bibliotheque.dao;

import org.example.gestion_bibliotheque.models.User;

import java.util.List;

public interface IAdmin {

    void addUser(String matricule, String firstname, String secondname, String lastname, String role, int age);

    void deleteUser(String matricule);

    void updateUser(String matricule, String firstname, String lastname, String role, int age);

    public List<User> getAllUsers();
}
