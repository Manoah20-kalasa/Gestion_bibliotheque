package org.example.gestion_bibliotheque.models;

import javafx.beans.property.*;

public abstract class Livre {
    protected final IntegerProperty id = new SimpleIntegerProperty();
    protected final StringProperty titre = new SimpleStringProperty();
    protected final StringProperty auteur = new SimpleStringProperty();
    protected final StringProperty genre = new SimpleStringProperty();
    protected final IntegerProperty annee = new SimpleIntegerProperty();
    protected final BooleanProperty disponible = new SimpleBooleanProperty();

    public Livre(int id, String titre, String auteur, String genre, int annee, boolean disponible) {
        this.id.set(id);
        this.titre.set(titre);
        this.auteur.set(auteur);
        this.genre.set(genre);
        this.annee.set(annee);
        this.disponible.set(disponible);
    }

    // Getters Java classiques
    public int getId() { return id.get(); }
    public String getTitre() { return titre.get(); }
    public String getAuteur() { return auteur.get(); }
    public String getGenre() { return genre.get(); }
    public int getAnnee() { return annee.get(); }
    public boolean isDisponible() { return disponible.get(); }

    // Propriétés JavaFX (pour liaison avec TableView, etc.)
    public IntegerProperty idProperty() { return id; }
    public StringProperty titreProperty() { return titre; }
    public StringProperty auteurProperty() { return auteur; }
    public StringProperty genreProperty() { return genre; }
    public IntegerProperty anneeProperty() { return annee; }
    public BooleanProperty disponibleProperty() { return disponible; }

    // Setters (si tu as besoin de modifier ces champs)
    public void setId(int id) { this.id.set(id); }
    public void setTitre(String titre) { this.titre.set(titre); }
    public void setAuteur(String auteur) { this.auteur.set(auteur); }
    public void setGenre(String genre) { this.genre.set(genre); }
    public void setAnnee(int annee) { this.annee.set(annee); }
    public void setDisponible(boolean disponible) { this.disponible.set(disponible); }

    // Méthode abstraite
    public abstract String getType();
}
