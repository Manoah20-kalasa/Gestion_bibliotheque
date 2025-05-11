package org.example.gestion_bibliotheque.models;

public class Admin extends Personne {
    private String statut;

    public Admin(String nom, String prenom, String matricule, String postnom, int age, String statut) {
        super(nom, prenom, matricule, postnom, age);
        this.statut = statut;
    }
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
}
