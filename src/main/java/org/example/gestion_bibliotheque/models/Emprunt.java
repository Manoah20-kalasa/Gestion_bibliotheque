package org.example.gestion_bibliotheque.models;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Emprunt {
    private final ObjectProperty<Livre> livre = new SimpleObjectProperty<>();
    private final ObjectProperty<User> lecteur = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> dateEmprunt = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> dateRetour = new SimpleObjectProperty<>();
    private final BooleanProperty rendu = new SimpleBooleanProperty();

    public Emprunt(Livre livre, User lecteur, LocalDate dateEmprunt, LocalDate dateRetour, boolean rendu) {
        this.livre.set(livre);
        this.lecteur.set(lecteur);
        this.dateEmprunt.set(dateEmprunt);
        this.dateRetour.set(dateRetour);
        this.rendu.set(rendu);
    }

    // Getters
    public Livre getLivre() { return livre.get(); }
    public User getLecteur() { return lecteur.get(); }
    public LocalDate getDateEmprunt() { return dateEmprunt.get(); }
    public LocalDate getDateRetour() { return dateRetour.get(); }
    public boolean isRendu() { return rendu.get(); }

    // Properties (pour JavaFX bindings)
    public ObjectProperty<Livre> livreProperty() { return livre; }
    public ObjectProperty<User> lecteurProperty() { return lecteur; }
    public ObjectProperty<LocalDate> dateEmpruntProperty() { return dateEmprunt; }
    public ObjectProperty<LocalDate> dateRetourProperty() { return dateRetour; }
    public BooleanProperty renduProperty() { return rendu; }

    // Setters (optionnels)
    public void setLivre(Livre livre) { this.livre.set(livre); }
    public void setLecteur(User lecteur) { this.lecteur.set(lecteur); }
    public void setDateEmprunt(LocalDate dateEmprunt) { this.dateEmprunt.set(dateEmprunt); }
    public void setDateRetour(LocalDate dateRetour) { this.dateRetour.set(dateRetour); }
    public void setRendu(boolean rendu) { this.rendu.set(rendu); }

    // Pour le titre du livre dans le tableau
    public StringProperty livreTitreProperty() {
        return new SimpleStringProperty(livre.get() != null ? livre.get().getTitre() : "");
    }

    public StringProperty dateEmpruntStrProperty() {
        return new SimpleStringProperty(dateEmprunt.get() != null ? dateEmprunt.get().toString() : "");
    }

    public StringProperty dateRetourStrProperty() {
        return new SimpleStringProperty(dateRetour.get() != null ? dateRetour.get().toString() : "");
    }

}
