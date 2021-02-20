import java.util.List;

public class Etats {
    private String nom;
    private List<Transitions> transitions;//[ [0,"a"],[1,"b"] ]

    public Etats(){

    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Transitions> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transitions> transitions) {
        this.transitions = transitions;
    }
}
// enlever les classes etatscompose et abstractetat ne laisser que celle la
// laisser le nom en string on fera les traitement plus tard