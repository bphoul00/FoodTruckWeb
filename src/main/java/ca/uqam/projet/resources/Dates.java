package ca.uqam.projet.resources;

public class Dates {

    private int id;
    private String dates;

    public Dates() {
    }

    public Dates(Dates date) {
        this.id = id;
        this.dates = dates;
    }

    public Dates(int id, String dates) {
        this.id = id;
        this.dates = dates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

}
