import java.util.List;

public class Automates {

    private AbstractEtats etatDepart;
    private List<AbstractEtats> etatsArrivee,etats;
    private List<String> alphabet;
    private List transitions; // [ [ 0 , [ [0,"a"],[1,"b"] ] ], [ 1 , [ [1,"a"],[2,"b"] ] ]]

    public Automates(AbstractEtats etatDepart, List<AbstractEtats> etatsArrivee, List<AbstractEtats> etats, List<String> alphabet, List transitions) {
        this.etatDepart = etatDepart;
        this.etatsArrivee = etatsArrivee;
        this.etats = etats;
        this.alphabet = alphabet;
        this.transitions = transitions;
    }

    public AbstractEtats getEtatDepart() {
        return etatDepart;
    }

    public void setEtatDepart(AbstractEtats etatDepart) {
        this.etatDepart = etatDepart;
    }

    public List<AbstractEtats> getEtatsArrivee() {
        return etatsArrivee;
    }

    public void setEtatsArrivee(List<AbstractEtats> etatsArrivee) {
        this.etatsArrivee = etatsArrivee;
    }

    public List<AbstractEtats> getEtats() {
        return etats;
    }

    public void setEtats(List<AbstractEtats> etats) {
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

    public EtatsCompose creationEtatCompose(List<AbstractEtats> liste){
        // TO DO
        //AbstractEtatsCompose ec = new AbstractEtatsCompose();
        return null;
    }
    public void toMatrice(){
        //  TO DO
    }


    // ALGORITHMES

    public void determiniser(){
        // TO DO
    }

}
