import java.util.ArrayList;
import java.util.List;

public class Etats {
    private String nom;

    private ArrayList transitions;

    public Etats() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList transitions) {
        this.transitions = transitions;
    }
}