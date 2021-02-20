import java.util.List;

public class Automates {

    private Etats etatDepart;
    private List<Etats> etatsArrivee,etats;
    private List<String> alphabet;
    private List transitions; // [ [ 0 , [ [0,"a"],[1,"b"] ] ], [ 1 , [ [1,"a"],[2,"b"] ] ]]

    public Automates(Etats etatDepart, List<Etats> etatsArrivee, List<Etats> etats, List<String> alphabet, List transitions) {
        this.etatDepart = etatDepart;
        this.etatsArrivee = etatsArrivee;
        this.etats = etats;
        this.alphabet = alphabet;
        this.transitions = transitions;
    }

    public Etats getEtatDepart() {
        return etatDepart;
    }

    public void setEtatDepart(Etats etatDepart) {
        this.etatDepart = etatDepart;
    }

    public List<Etats> getEtatsArrivee() {
        return etatsArrivee;
    }

    public void setEtatsArrivee(List<Etats> etatsArrivee) {
        this.etatsArrivee = etatsArrivee;
    }

    public List<Etats> getEtats() {
        return etats;
    }

    public void setEtats(List<Etats> etats) {
        this.etats = etats;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public List getTransitions() {
        return transitions;
    }

    public void setTransitions(List transitions) {
        this.transitions = transitions;
    }
    // ALGORITHMES UTILES


    public void toMatrice(){
        //  TO DO
    }


    // ALGORITHMES

    public void determiniser(){
        // TO DO
    }

}
