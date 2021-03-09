import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

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

    public String concatener(List etatsG){
        if(etats == null) return "";
        else {
            String str = "";
            for(int a=0;a<etatsG.size();a++){
                if ( a == 0 ){
                    str = (String) etatsG.get(a);
                }else {
                    str = str + "," + (String) etatsG.get(a);
                }
            }
            return str;
        }
    }

    // ALGORITHMES

    public void determiniser(){

        Queue<List> file_etat_cree = new LinkedList<List>();
        List debut = new ArrayList();
        debut.add(this.getEtatDepart().getNom());
        file_etat_cree.add(debut);

        while (file_etat_cree.size()!=0){
            List etats_preced = file_etat_cree.poll();
//            file_etat_cree.remove(etats_preced);
//            System.out.println(file_etat_cree);
            Etats etat = new Etats();
            // concatener les etats groupés : à définir
            etat.setNom(etats_preced.toString());
            HashMap<String,List> hash = new HashMap<String,List>();

            for(int i =0;i<etats_preced.size();i++){
                for (int j=0;j<this.alphabet.size();j++){
                    //verifier cette ligne
                    if (hash.containsKey(this.alphabet.get(j))){
                        // l'idée est là
                        List l = (List)hash.get(this.alphabet.get(j));
                        l.add(etats_preced);
                        hash.put(this.alphabet.get(j),l);
                    } else {
                        //ici ce sera le nom de l'etat i qui nous sera utile
                        List l = new ArrayList();
                        l.add(etats_preced.get(i));
                        hash.put(this.alphabet.get(j),l);
                    }
                }
            }
            System.out.println(hash);
            // à revoir
            ArrayList hashToList = new ArrayList();
            for(String key: hash.keySet()){
                file_etat_cree.add(hash.get(key));
                ArrayList config = new ArrayList();
                config.add(0,hash.get(key).toString());
               config.add(1,hash.get(key));
               hashToList.add(config);
               etat.setTransitions(hashToList);
            }
        }

    }

}
