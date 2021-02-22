import java.util.List;

public class Transitions {
    //private AbstractEtats depart;
    private List transitions; // [0,"a"]

    public Transitions(List transitions) {
        this.transitions = transitions;
    }

    public List getTransitions() {
        return transitions;
    }

    public void setTransitions(List transitions) {
        this.transitions = transitions;
    }

    public int nbTransitions() {
        // Retourne le nombre de transitions pour un etat ou le nombre de transitions
        // totales, on s'en branle un, j'ai juste besoin d'un truc qui retourne un entier
        int nbTransitions = 0;
        return nbTransitions;
    }
}
