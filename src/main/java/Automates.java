import java.util.List;

public class Automates {
    private Etats init;
    //private List<Etats> finaux,alphabet,transition,etat;
    private List<Etats> finaux,alphabet,transition,etat;

    public Automates(Etats init, List<Etats> finaux, List<Etats> alphabet, List<Etats> transition, List<Etats> etat) {
        this.init = init;
        this.finaux = finaux;
        this.alphabet = alphabet;
        this.transition = transition;
        this.etat = etat;
    }

    public Etats getInit() {
        return init;
    }

    public List<Etats> getFinaux() {
        return finaux;
    }

    public List<Etats> getAlphabet() {
        return alphabet;
    }

    public List<Etats> getTransition() {
        return transition;
    }

    public List<Etats> getEtat() {
        return etat;
    }

    public void setInit(Etats init) {
        this.init = init;
    }

    public void setFinaux(List<Etats> finaux) {
        this.finaux = finaux;
    }

    public void setAlphabet(List<Etats> alphabet) {
        this.alphabet = alphabet;
    }

    public void setTransition(List<Etats> transition) {
        this.transition = transition;
    }

    public void setEtat(List<Etats> etat) {
        this.etat = etat;
    }

    // ALGORITHMES UTILES

    public EtatsCompose creationEtatCompose(List<Etats> liste){
        // TO DO
        //EtatsCompose ec = new EtatsCompose();
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
