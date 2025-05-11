package org.example.gestion_bibliotheque.models;

public class Magazine extends Livre{
    public Magazine(int id, String titre, String auteur, String genre, int annee, boolean disponible) {
        super(id, titre, auteur, genre, annee, disponible);
    }

    @Override
    public String getType() {
        return "Magazine";
    }
}
