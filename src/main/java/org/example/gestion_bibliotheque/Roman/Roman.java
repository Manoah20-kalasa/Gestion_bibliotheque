package org.example.gestion_bibliotheque.Roman;

import org.example.gestion_bibliotheque.models.Livre;

public class Roman extends Livre {


    public Roman(int id, String titre, String auteur, String genre, int annee, boolean disponible) {
        super(id, titre, auteur, genre, annee, disponible);
    }

    @Override
    public String getType() {
        return "Roman";
    }
}
