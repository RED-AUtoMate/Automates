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
        // contient les lignes du tableau de determinaisation ( on l'initialise avec l'etat de depart )
        Queue<List> file_etat_cree = new LinkedList<List>();
        List debut = new ArrayList();
        debut.add(this.getEtatDepart().getNom());
        file_etat_cree.add(debut);
        List<Etats> etatsList = new ArrayList<Etats>();

        while (file_etat_cree.size()!=0){
            List<String> etats_preced = file_etat_cree.poll();
//            System.out.println(etats_preced);
//            file_etat_cree.remove(etats_preced);
//            System.out.println(file_etat_cree);
            Etats etat = new Etats();
            etat.setTransitions(new ArrayList());
            // concatener les etats groupés : à définir
            etat.setNom(etats_preced.toString());
            // contient les colonnes du tableau
            HashMap<String,List> hash = new HashMap<String,List>();

            for(int i =0;i<etats_preced.size();i++){
                // on pourrait declarer l ici
                // ICI on pourrait faire un parcours des successeurs de etats_preced pour les ajouter à hash
                // on pourrait meme remplacer la boucle suivante
                // sans oublier de remplacer les alphabet.get/size par les vriables adéquates
                for (int j=0;j<this.alphabet.size();j++) {
                    Etats e = this.getEtats().get(get_etat(this, etats_preced.get(i)));
//                    System.out.println(e.getNom());
                    for (int k = 0; k < e.getTransitions().size(); k++) {
                        //verifier cette ligne la condition est inutile : on ajoute tous les etats sans distinction
                        // on devrait plutot verifier si ils ont alphabet.get(j) comme successeur
                        ArrayList al = (ArrayList) e.getTransitions().get(k);
                        if (al.get(1).equals(this.alphabet.get(j))) {
                            if (hash.containsKey(this.alphabet.get(j))) {
                                if (!hash.get(this.alphabet.get(j)).contains(((ArrayList<?>) e.getTransitions().get(k)).get(0))){
                                    // l'idée est là
                                    List l = (List) hash.get(this.alphabet.get(j));
                                    // pourquoi etats_preced alors qu'en bas on met un get(i)???
                                    l.add(((ArrayList<?>) e.getTransitions().get(k)).get(0));
                                    hash.put(this.alphabet.get(j), l);}
                            } else {
                                //ici ce sera le nom de l'etat i qui nous sera utile
                                List l = new ArrayList();
                                l.add(((ArrayList<?>) e.getTransitions().get(k)).get(0));
                                hash.put(this.alphabet.get(j), l);
                            }
                        }
                    }
                }
            }
//            System.out.println(hash);
            // à revoir
            // le fait que c'est à l'exterieur de la boucle peut poser des problemes de reinnitialisation
            ArrayList hashToList = new ArrayList();
            for(String key: hash.keySet()){
                ArrayList config = new ArrayList();
                // je pense qu'ici on devrait ajouter la key au lieu de get(key) PAS SÛR
                config.add(0,hash.get(key).toString());
//                config.add(1,hash.get(key));
                config.add(1,key);
//                System.out.println(config.get(0).getClass());
                hashToList.add(config);
                etat.setTransitions(hashToList);
//                System.out.println(etat.getNom()+" "+etat.getTransitions() +file_etat_cree.size());
//                if (!etatsList.contains(etat)) {
                boolean trouve = false;
                int s=0;
                while(!trouve && s < etatsList.size()){
                    if (etatsList.get(s).getNom().equals(etat.getNom())){
                        trouve = true;
                    }
                    s++;
                }
                if(!trouve || s>=etatsList.size()) {
                    etatsList.add(etat);
//                if(!etat.getTransitions().contains(config)) {
                    file_etat_cree.add(hash.get(key));
//                }
//                }
                }
            }
        }
    this.setEtats(etatsList);
    }
    public int get_etat(Automates automates, String nom) {
        for(int i = 0; i < automates.getEtats().size(); ++i) {
            if (((Etats)automates.getEtats().get(i)).getNom().equals(nom)) {
                return i;
            }
        }

        return -1;
    }

}
