package ca.uqam.projet.tasks;

public class IDMaker {
    
    static IDMaker instance;
    private static int idCounter = 1;
    
    private IDMaker(){
    }
    
    public static IDMaker getInstance(){
        if(instance == null){
        instance = new IDMaker();
                }
        return instance;
    }

    public int createID() {
        return idCounter++;
    }

}
