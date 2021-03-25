import java.util.List;

public class Etats {
    private String nom;
    private List transitions;//[ [0,"a"],[1,"b"] ]

    public Etats(){

    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List getTransitions() {
        return transitions;
    }

    public void setTransitions(List transitions) {
        this.transitions = transitions;
    }
}
// enlever les classes etatscompose et abstractetat ne laisser que celle la
// laisser le nom en string on fera les traitement plus tard