package ca.uqam.projet.resources;

public class IDMaker {

    private static int idCounter = 1;

    public static int createID() {
        return idCounter++;
    }

}
