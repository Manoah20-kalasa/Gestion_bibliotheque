package org.example.gestion_bibliotheque.models;
import javafx.beans.property.*;

public abstract class Personne {
    private final StringProperty matricule;
    private final StringProperty firstname;
    private final StringProperty secondname;
    private final StringProperty lastname;
    private final IntegerProperty age;

    public Personne(String matricule, String firstname, String secondname, String lastname, int age) {
        this.matricule = new SimpleStringProperty(matricule);
        this.firstname = new SimpleStringProperty(firstname);
        this.secondname = new SimpleStringProperty(secondname);
        this.lastname = new SimpleStringProperty(lastname);
        this.age = new SimpleIntegerProperty(age);

    }

    public String getMatricule() {
        return matricule.get();
    }

    public void setMatricule(String matricule) {
        this.matricule.set(matricule);
    }

    public StringProperty matriculeProperty() {
        return matricule;
    }

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public String getSecondname() {
        return secondname.get();
    }

    public void setSecondname(String secondname) {
        this.secondname.set(secondname);
    }

    public StringProperty secondnameProperty() {
        return secondname;
    }

    public String getLastname() {
        return lastname.get();
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public IntegerProperty ageProperty() {
        return age;
    }
}
