package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.*;

public class Lieu {

    private int id;
    private int activitesID;
    private String nom;
    private double lat;
    private double lng;

    public Lieu() {
    }

    public Lieu(String nom, double lat, double lng) {
        this.nom = nom;
        this.lat = lat;
        this.lng = lng;

    }

    public Lieu(String nom, double lat, double lng, int id, int activitesID) {
        this.nom = nom;
        this.lat = lat;
        this.lng = lng;
        this.id = id;
        this.activitesID = activitesID;

    }

    public Lieu(Lieu input) {
        this.nom = input.nom;
        this.lat = input.lat;
        this.lng = input.lng;
    }

    public int getId() {
        return id;
    }

    @JsonProperty
    public String getNom() {
        return nom;
    }

    @JsonProperty
    public double getLat() {
        return lat;
    }

    @JsonProperty
    public double getLng() {
        return lng;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivitesID() {
        return activitesID;
    }

    public void setActivitesID(int activitesID) {
        this.activitesID = activitesID;
    }

    @Override
    public String toString() {
        return String.format("«%s» --%f,%f", nom, lat, lng);
    }
}
