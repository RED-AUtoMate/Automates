import java.util.ArrayList;
import java.util.List;

public class Automates {

    private Etats etatDepart;
    private List<Etats> etatsArrivee,etats;
    private List<String> alphabet;

    public Automates(){

    }
    public Automates(Etats etatDepart, List<Etats> etatsArrivee, List<Etats> etats, List<String> alphabet) {
        this.etatDepart = etatDepart;
        this.etatsArrivee = etatsArrivee;
        this.etats = etats;
        this.alphabet = alphabet;
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

    // ALGORITHMES UTILES


    public void toMatrice(){
        //  TO DO
    }


    // ALGORITHMES

    public void determiniser(){
        // TO DO
    }


    /* Obtenir l'equivalent de l'automate sans eps-transitions */
    public Automates synchroniser(){



        /*  Pour chaque etat on verifie s'il contient des eps-transitions
        *   avec deux cas possible
        *       1- la transition mene vers un etat final
        *       2- la transition mene vers un etat non final
        *           le traitement est le meme mais dans le 1er cas on
        *           ajoute l'etat dont il s'agit a l'ensemble des
        *           etats finaux    */

        List<Etats> etats_finaux = this.getEtatsArrivee();
        for (int i = 0; i < this.getEtats().size(); i++){

            /* pour chaque etat on obtient son ensemble de transitions */
            Etats etat = this.getEtats().get(i);
            System.out.println(etat.getNom());
            System.out.println(etat.getTransitions());
            System.out.println("***********");
            ArrayList transitions = etat.getTransitions();

            /* pour chaque transition on verifie s'il s'agit d'une eps-transition */
            for (int j = 0; j < transitions.size(); j++){
                ArrayList configs = (ArrayList) transitions.get(j);
                if (configs.get(1).equals("eps")){
                    Etats eps = new Etats();
                    for (int a = 0; a < this.getEtats().size(); a++){
                        Etats et = this.getEtats().get(a);
                        if (et.getNom().equals(configs.get(0))){
                            eps = et;
                        }
                    }

                    /* puis on verifie si elle mene vers un etat final ou non */
                    int not_final = 1;
                    int n = 0;
                    while (n < etats_finaux.size() && not_final == 1){
                        Etats etat_final = etats_finaux.get(n);
                        if(etat_final.getNom().equals(configs.get(0))){
                            not_final = 0;
                        }
                        n++;
                    }

                    /* 1erement on supprime la transition */
                    transitions.remove(j);

                    /* si elle mene a un etat final on ajoute l'etat actuel a l'ensemble des finaux*/
                    if (not_final == 0){
                        etats_finaux.add(etat);
                    }

                    /* on ajoute les non epsilon transitions de l'etat auquel l'eps-transition mene
                    * a l'etat actuel */
                    for (int q = 0; q < eps.getTransitions().size(); q++){
                        ArrayList trs = (ArrayList) eps.getTransitions().get(q);
                        if (!trs.get(1).equals("eps")){
                            transitions.add(trs);
                        }
                    }
                }
            }
        }

        System.out.println("****** apres ******");

        for (int i = 0; i < this.getEtats().size(); i++){
            Etats etats = this.getEtats().get(i);
            System.out.println(etats.getNom());
            System.out.println(etats.getTransitions());
            System.out.println("***************");
        }

        System.out.println("*****finaux******");
        for (int i = 0; i < etats_finaux.size(); i++){
            System.out.println(etats_finaux.get(i).getNom());
        }

        return this;
    }

}
