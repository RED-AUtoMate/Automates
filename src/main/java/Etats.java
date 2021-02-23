import java.util.ArrayList;
import java.util.List;

public class Etats {
    private String nom;

    private ArrayList transitions;//[ [0,"a"],[1,"b"] ]getliste.getitem.getobje.getlist.getitem

    public Etats(){

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
// enlever les classes etatscompose et abstractetat ne laisser que celle la
// laisser le nom en string on fera les traitement plus tard