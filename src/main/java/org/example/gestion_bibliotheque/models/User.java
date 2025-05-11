package org.example.gestion_bibliotheque.models;
import javafx.beans.property.*;

public class User extends Personne {
    private final StringProperty role;

    public User(String matricule, String firstname, String secondname, String lastname, int age, String role) {
        super(matricule, firstname, secondname, lastname, age);
        this.role = new SimpleStringProperty(role);
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public StringProperty roleProperty() {
        return role;
    }
}
