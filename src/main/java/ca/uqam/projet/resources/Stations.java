package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;

public class Stations {

    private ArrayList<Bixi> stations;
    private boolean schemeSuspended;
    private float timestamp;

    public Stations() {
    }

    public Stations(ArrayList<Bixi> stations) {
        this.stations = stations;
    }

    @JsonProperty
    public ArrayList<Bixi> getStations() {
        return stations;
    }

    @JsonProperty
    public boolean isSchemeSuspended() {
        return schemeSuspended;
    }

    @JsonProperty
    public float getTimestamp() {
        return timestamp;
    }

    public void setStations(ArrayList<Bixi> stations) {
        this.stations = stations;
    }

    public void setSchemeSuspended(boolean schemeSuspended) {
        this.schemeSuspended = schemeSuspended;
    }

    public void setTimestamp(float timestamp) {
        this.timestamp = timestamp;
    }

}
