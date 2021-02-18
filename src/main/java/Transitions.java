import java.util.ArrayList;

public class Transitions {
    public String etat;
    public ArrayList transitions;

    public Transitions(){
    }


    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public ArrayList getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList transitions) {
        this.transitions = transitions;
    }
}
