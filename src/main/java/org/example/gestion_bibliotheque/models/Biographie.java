package org.example.gestion_bibliotheque.models;

public class Biographie extends Livre {
    public Biographie(int id, String titre, String auteur, String genre, int annee, boolean disponible) {
        super(id, titre, auteur, genre, annee, disponible);
    }

    @Override
    public String getType() {
        return "Biographie";
    }
}
