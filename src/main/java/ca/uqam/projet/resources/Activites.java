package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;

public class Activites {

    private int id;
    private String nom;
    private String description;
    private String arrondissement;
    private Lieu lieu;
    private ArrayList<Dates> dates;

    public Activites() {
    }

    public Activites(int id, String nom, String description, String arrondissement, Lieu lieu, ArrayList<String> dates) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.arrondissement = arrondissement;
        this.lieu = lieu;
        for (String dateString : dates) {
            this.dates.add(new Dates(dateString));
        }
    }

    public Activites(int id, String nom, String description, String arrondissement) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.arrondissement = arrondissement;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getNom() {
        return nom;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    @JsonProperty
    public String getArrondissement() {
        return arrondissement;
    }

    @JsonProperty
    public Lieu getLieu() {
        return lieu;
    }

    @JsonProperty
    public ArrayList<Dates> getDates() {
        return dates;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArrondissement(String arrondissement) {
        this.arrondissement = arrondissement;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public void setDates(ArrayList<Dates> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return String.format("%d «%s» --%s", id, nom, description);
    }
}
