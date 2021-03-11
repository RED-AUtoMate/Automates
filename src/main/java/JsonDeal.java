import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JsonDeal {


    /*
    Generation d'un automate aleatoirement les parametres sont le nombres
    d'etats, le nombre de transitions, une liste d'alphabet
     */

    public static Etats creerEtatGrp(String destinations,String depart,String lettre){

        return null;
    }

    public Automates random_aut(int nb_etats, int nb_transition, List<String> alphabet) {
        Random random = new Random();

        /*
        Obtenir une liste contenant nb_etats etats numerotés de 0 jusque a nb_etats-1 [0,1,2] si n = 3
         */
        ArrayList<String> etats = new ArrayList<String>();
        for (int i = 0; i < nb_etats; i++) {
            etats.add(String.valueOf(i));
        }


        /*
        generation aleatoire de transition, une transition est un triplet etat_depart, mot lu, etat_arrive
        on cree une map qui va avoir comme kle des indices d'etats et comme valeur les differentes
        configuration que cet etat peut suivre {1: [[0, a], [1,b]],
                                                2: [[2,a],[2,b]]
                                                }
         */
        HashMap<String, ArrayList<ArrayList<String>>> transitions = new HashMap<String, ArrayList<ArrayList<String>>>();
        for (int j = 0; j < nb_transition; j++) {
            /*
            le treplet etat_depart, mot, etat_arrive est genere à chaque eteration de la boucle,
            le mot et l'etat_arrive forment une configuration represente par "transition" [0, a]
             */
            String depart = String.valueOf(random.nextInt(nb_etats));
            String destination = String.valueOf(random.nextInt(nb_etats));
            String mot = alphabet.get(random.nextInt(alphabet.size()));
            ArrayList<String> transition = new ArrayList<String>();
            transition.add(0, destination);
            transition.add(1, mot);

            /*
            on assure que chaque etat de depart ne va pas se repeter en utilisant une map qui va
            contenir l etat_depart comme cle, une liste de transition (liste ) comme valeur
                                    1:[[0,a], [0,b]]
             */
            if (transitions.containsKey(depart)) {
                ArrayList<ArrayList<String>> arrayList = transitions.get(depart);
                arrayList.add(transition);
                transitions.put(depart, arrayList);
            } else {
                ArrayList<ArrayList<String>> config = new ArrayList<ArrayList<String>>();
                config.add(transition);
                transitions.put(depart, config);
            }
        }

        /*
        on cree la liste d'etats que va contenir notre automate
        on enitialise pour chque item de la map un objet de la classe Etats
        et on l'ajoute a la liste etatsListe
         */
        List<Etats> etatsList = new ArrayList<Etats>();
        for (Map.Entry<String, ArrayList<ArrayList<String>>> entry: transitions.entrySet()){
            Etats etats1 = new Etats();
            etats1.setNom(entry.getKey());
            etats1.setTransitions(entry.getValue());
            etatsList.add(etats1);
        }

        /*
        notre automate est pret a etre instancie et a se balader dans la memoire
        par contre il reste de definir l'etat initiale et l'ensemble d'etats fineaux
         a*/
        Automates automates = new Automates();
        automates.setEtats(etatsList);
        automates.setAlphabet(alphabet);

        int n = automates.getEtats().size();
        /*
        l'etat initiale est tire aleatoirement parmi les etats qu'on a
         */
        automates.setEtatDepart(automates.getEtats().get(random.nextInt(n)));

        /*
        la cardinalite de l'ensemble d'etats fineaux est tire au hasard et donc un nombre = card
        d'etats fineaux est selectionne
         */
        int m = random.nextInt(n);
        if (m == 0){
            m += 1;
        }
        ArrayList<Etats> finale = new ArrayList<Etats>();
        for (int i = 0; i < m; i++){
            finale.add(automates.getEtats().get(i));
        }
        automates.setEtatsArrivee(finale);

        return automates;

    }



    /* partir depuis une representation json vers des objets java ensuite vers notre objet automate */
    public Automates json_to_automate(String path){
        Automates automates = new Automates();

        JSONParser jsonParser = new JSONParser();

        try {
            /* lecture du fichier json */
            JSONObject ob = (JSONObject) jsonParser.parse(new FileReader(path));

            /* on recupere la representation de l'automate depuis le fichier json */
            ArrayList alpha = (ArrayList) ob.get("Alphabet");
            JSONArray etats = (JSONArray) ob.get("Etats");
            String init = ob.get("Init").toString();
            JSONArray fin = (JSONArray) ob.get("Fin");
            JSONArray trans = (JSONArray) ob.get("Transitions");

            /*on cree une map qui va contenir pour chaque etat ses transitions */
            HashMap<String, ArrayList> hashMap = new HashMap<String, ArrayList>();

            /* les differentes listes utilisees par notre automate */
            ArrayList<String> alphabet = new ArrayList<String>();
            ArrayList<Etats> etatsArrayList = new ArrayList<Etats>();
            ArrayList<Etats> fineaux = new ArrayList<Etats>();
            Etats etat_depart = new Etats();

            for (int i = 0; i < alpha.size(); i++){
                alphabet.add(alpha.get(i).toString());
            }

            ArrayList aut_trans = new ArrayList();

            for (int i = 0; i < trans.size(); i++){
                ArrayList transi = (ArrayList) trans.get(i);

                String depart = transi.get(0).toString();
                ArrayList config = (ArrayList) transi.get(1);

                /* building a list of configurations */
                ArrayList aut_config = new ArrayList();
                for (int j = 0; j < config.size(); j++){

                    ArrayList<String> aut_conf = new ArrayList<String>();
                    /* getting the basic configuration [0, a] from json format to arraylist aut_conf */
                    ArrayList conf = (ArrayList) config.get(j);
                    String arrive = conf.get(0).toString();
                    String mot = conf.get(1).toString();

                    aut_conf.add(arrive);
                    aut_conf.add(mot);
                    aut_config.add(aut_conf);

                }
                hashMap.put(depart, aut_config);
            }

            for (Map.Entry<String, ArrayList> entry: hashMap.entrySet()){
                Etats etats1 = new Etats();
                etats1.setNom(entry.getKey());
                etats1.setTransitions(entry.getValue());

                etatsArrayList.add(etats1);
            }
            /*depart*/
            etat_depart.setNom(init);
            etat_depart.setTransitions(hashMap.get(init));

            /*finaux*/
            ArrayList<Etats> finos = new ArrayList<Etats>();
            for (int i = 0; i < fin.size(); i++){
                Etats finale = new Etats();
                finale.setNom(fin.get(i).toString());
                finale.setTransitions(hashMap.get(fin.get(i).toString()));
                finos.add(finale);
            }
            automates.setAlphabet(alphabet);
            automates.setEtats(etatsArrayList);
            automates.setEtatDepart(etat_depart);
            automates.setEtatsArrivee(finos);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return automates;
    }


    public JSONObject automate_to_json(Automates automates){



        ArrayList transitions = new ArrayList();
        for (int i = 0; i < automates.getEtats().size(); i++){
            ArrayList etat = new ArrayList();
            Etats etats = automates.getEtats().get(i);
            etat.add(etats.getNom());
            etat.add(etats.getTransitions());
            transitions.add(etat);
        }
        List<String> alphabet = automates.getAlphabet();
        String init = automates.getEtatDepart().getNom();

        ArrayList etats = new ArrayList();
        for (int i = 0; i < automates.getEtats().size(); i++){
            etats.add(automates.getEtats().get(i).getNom());
        }

        ArrayList finaux = new ArrayList();
        for (int i = 0; i < automates.getEtatsArrivee().size(); i++){
            finaux.add(automates.getEtatsArrivee().get(i).getNom());
        }
        HashMap hashMap = new HashMap();
        hashMap.put("Alphabet", alphabet);
        hashMap.put("Etats", etats);
        hashMap.put("Init", init);
        hashMap.put("Fin", finaux);
        hashMap.put("Transitions", transitions);
        JSONObject object = new JSONObject();
        object.putAll(hashMap);



        return object;
    }



    public static void main(String[] args) {
//        JsonDeal jsonDeal= new JsonDeal();
//        Automates a = jsonDeal.json_to_automate("test.json");
//        jsonDeal.automate_to_json(a);

        ArrayList transition = new ArrayList();

        ArrayList<ArrayList<String>> transitions = new ArrayList<ArrayList<String>>();

        // creation q1
        ArrayList transition1 = new ArrayList();
        transition1.add("1");
        transition1.add("a");
        ArrayList transition2 = new ArrayList();
        transition2.add("2");
        transition2.add("a");
        Etats q1 = new Etats();
        q1.setNom("1");
        ArrayList<ArrayList<String>> q1_transitions = q1.getTransitions();
        q1_transitions = new ArrayList<ArrayList<String>>();
        q1_transitions.add(transition1);
        q1_transitions.add(transition2);
        q1.setTransitions(q1_transitions);

        // creation q2
        ArrayList transition3 = new ArrayList();
        transition3.add("3");
        transition3.add("@");
        Etats q2 = new Etats();
        q2.setNom("2");
        ArrayList<ArrayList<String>> q2_transitions = new ArrayList<ArrayList<String>>();
        q2_transitions.add(transition3);
        q2.setTransitions(q2_transitions);

        // creation q3
        ArrayList transition4 = new ArrayList();
        transition4.add("3");
        transition4.add("a");
        ArrayList transition5 = new ArrayList();
        transition5.add("4");
        transition5.add("a");
        Etats q3 = new Etats();
        q3.setNom("3");
        ArrayList<ArrayList<String>> q3_transitions = new ArrayList<ArrayList<String>>();
        q3_transitions.add(transition4);
        q3_transitions.add(transition5);
        q3.setTransitions(q3_transitions);

        // creation q4
        ArrayList transition6 = new ArrayList();
        transition6.add("5");
        transition6.add(".");
        Etats q4 = new Etats();
        q4.setNom("4");
        ArrayList<ArrayList<String>> q4_transitions = new ArrayList<ArrayList<String>>();
        q4_transitions.add(transition6);
        q4.setTransitions(q4_transitions);

        // creation q5
        ArrayList transition7 = new ArrayList();
        transition7.add("5");
        transition7.add("a");
        ArrayList transition8 = new ArrayList();
        transition8.add("6");
        transition8.add("a");
        Etats q5 = new Etats();
        q5.setNom("5");
        ArrayList<ArrayList<String>> q5_transitions = new ArrayList<ArrayList<String>>();
        q5_transitions.add(transition7);
        q5_transitions.add(transition8);
        q5.setTransitions(q5_transitions);

        //q6

        Etats q6 = new Etats();
        q6.setNom("6");
        q6.setTransitions(new ArrayList());

        ArrayList<Etats> arrivee = new ArrayList<Etats>();
        arrivee.add(q6);
        ArrayList<Etats> etats = new ArrayList<Etats>();
        etats.add(q1);
        etats.add(q2);
        etats.add(q3);
        etats.add(q4);
        etats.add(q5);
        etats.add(q6);
        ArrayList<String> alphabet = new ArrayList<String>();
        alphabet.add("a");
        alphabet.add("@");
        alphabet.add(".");
        Automates a = new Automates(q1,arrivee,etats,alphabet);
        a.determiniser();
        for (int i=0;i<a.getEtats().size();i++){
            System.out.println(a.getEtats().get(i).getNom()+" "+a.getEtats().get(i).getTransitions());
        }
//        HashMap h = new HashMap();
//        h.put("a",1);
//        h.put("b",2);
//        System.out.println(h.containsKey(1));
//        Queue x = new LinkedList();
//        x.add("a");
//        x.add("b");
//        System.out.println(x);
//        System.out.println(x.poll());
//        System.out.println(x);
//        System.out.println(a.getAlphabet());
////        System.out.println(a.getEtats());
//        for(int i=0;i<a.getEtats().size();i++) {
//            System.out.println(a.getEtats().get(i).getNom());
//            System.out.println(a.getEtats().get(i).getTransitions());
//        }
//        System.out.println(a.getEtatDepart());
//        System.out.println(a.getEtatsArrivee());
//        System.out.println(a.getEtats().get(0).getTransitions());

//        ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
//        ArrayList<String> str = new ArrayList<String>();
//        str.add("3");
//        str.add("a");
//        listOfLists.add(str);
//        ArrayList<String> str2 = new ArrayList<String>();
//        str2.add("1");
//        str2.add("g");
//        listOfLists.add(str2);
//        System.out.println(listOfLists);


        // determinaison
//        System.out.println(a.getEtats().get());
        // contient les transitions finales à inserer dans les grpEtats
//        ArrayList grpTransitions = new ArrayList();
//        // sert a creer les transitions groupées mais de maniere condensée : ["a:1,2,4","b:2,3"]
//        ArrayList<String> grpTransitionsTemp = new ArrayList<String>();
//        // contient les etat à inserer dans l'automate final deteminisé
//        ArrayList<Etats> grpEtats = new ArrayList<Etats>();
//        Etats e = new Etats();
//        e.setNom(a.getEtatDepart().getNom());
//        grpEtats.add(e);
//        // on parcourt les etats de a
//        for(int i=0;i<a.getEtats().size();i++){
//            // contient les transition de l'etat i
//            ArrayList transitions_etat = a.getEtats().get(i).getTransitions();
//            int size = 0;
//            if (transitions_etat == null)
//                size = 0;
//            else
//                size = transitions_etat.size();
//            // parcourt les transitions de l"etat i
//            for(int j=0;j<size;j++){
//                if (transitions_etat.get(j) != null) {
//                    // contient la transition j de l'etat i : ce sera toujours un tableau de 2 elements
//                    ArrayList<String> tr = (ArrayList<String>)transitions_etat.get(j);
//                    //System.out.println(tr.get(1));
//                    // creer un nouvel etat composé pour chaque transition partant de etat num i
//                    // creer un liste d'etat destination pour chaque lettre de l'alphabet
//                    // a partir de cette liste on creera les etats
//                    boolean exist = false;
//                    if(grpTransitionsTemp != null) {
//                        // parcourt les transitions temporaires
//                        for(int k=0;k<grpTransitionsTemp.size();k++){
//                            String str = grpTransitionsTemp.get(k);
//                            String alph_etat[] = str.split(":");
////                            System.out.println("ceci est "+alph_etat[0]+"/"+alph_etat[1]+"///"+alph_etat.length);
//                            // ajouter un nouvel etat au groupe d'etat
//                            if (tr.get(1).equals(alph_etat[0])){
////                                System.out.println(alph_etat[1]);
//                                // ici erreur :
//                                // le alph_etat[1] ne recupere qu'un seul element ??? qui pourtant est censé etre un string
//                                alph_etat[1] = alph_etat[1]+","+tr.get(0);
//                                // les deux prochaines lignes devraient etre correctes ???
//                                // le probleme vient probablement du fait que les boucles ou les structures sont foireuses
//
//                                ////// le code est correct : le probleme : je ne precise pas le depart des transitions
//                                // le a va bel et bien vers 1,2,3,4,5,6
//                                // il faudrait penser à modifier les structures  intermediraire du groupage d'en haut comme ça on pourrait
//                                // determiner le depart des arcs
//                                String temp =alph_etat[0]+":"+alph_etat[1];
//                                grpTransitionsTemp.set(k,temp);
////                                System.out.println(alph_etat[0]+" "+alph_etat[1]+" "+k+"\n\n");
//                                exist = true;
//                            }
//                        }
//
//                    }
//                    // creation d'une transition pour un group d'etat
//                    if (!exist){
//                        String str = tr.get(1)+":"+tr.get(0);
//                        grpTransitionsTemp.add(str);
//                    }
//                }
//            }
//            for(int g=0;g<grpEtats.size();g++)
//            System.out.println("grpetats :"+grpEtats.get(g).getNom());
//            System.out.println("grpTransitionsTemp :"+grpTransitionsTemp);
//            // ici generer grpTransitions
//
//            System.out.println("fin du parcours de l'etat "+(i+1));
//            // ici on pourrais creer les etats groupés puis vider grpTransitionsTemp
//            // ici on utilisera grpetat pour stocker les etats groupés déja créés
//            int s;
//            if(grpTransitionsTemp!=null) {
//                s = grpTransitionsTemp.size();
//            }else {
//                s = 0;
//            }
//                for (int x = 0; x < s; x++) {
//                    String alph_etat[] = grpTransitionsTemp.get(x).split(":");
////                Etats etat = creerEtatGrp(alph_etat[1], String.valueOf(i), alph_etat[0]);
//                    if (grpEtats.contains(alph_etat[1])) {
//
//                    } else {
//                        Etats et = new Etats();
//                        et.setNom(alph_etat[1]);
//                        // la partie suivante pourra etre ajoutée à une autre boucle
//                        // ou on pourrait vider grpTransitionstemp et refaire la meme boucle,sauf qu'en remplissant on ajouterait progressivement les transitions
////                        ArrayList<String> trns = new ArrayList<String>();
////                        trns.add()
////                        et.getTransitions().add();
//                        grpEtats.add(et);
//                    }
//                }
//                // ici former grpTransitions
//            grpTransitions.add(String.valueOf(i));
//            grpTransitions.add(grpTransitionsTemp);
//            // ICI PPROBLEME
//            // grpTransitionTemp est vide????
//            System.out.println("voila "+grpTransitions);
//            grpTransitionsTemp.clear();
//
//        }
//            // parcours de grpEtat ( pour le depart )
//            for(int j=0;j<grpEtats.size();j++){
//                String etat[] = grpEtats.get(j).getNom().split(",");
//                // on recupere les etats du grpEtat ( depart )
//                for (int y=0;y<etat.length;y++){
//                    // parcours des etats de l'automate ( pour determiner l'arrivée )
//                    for (int i=0;i<a.getEtats().size();i++) {
//                        if (etat[y].equals(a.getEtats().get(i).getNom())) {
//                            // ajouter les transitions de l'etat i de l'automate non deterministe
//                            if(a.getEtats().get(i).getTransitions()!=null){
//                            for (int m = 0; m < a.getEtats().get(i).getTransitions().size(); m++) {
//                                //grpetat pour les arrivées
//                                for (int l = 0; l < grpEtats.size(); l++) {
//                                    // etat1 contient les etats d'arrivée à tester
//                                    String etat1[] = grpEtats.get(l).getNom().split(",");
//                                    for (int g = 0; g < etat1.length; g++) {
//                                        ArrayList ltr = (ArrayList) a.getEtats().get(i).getTransitions().get(m);
//                                        if (ltr.get(0).equals(etat1[g])) {
//                                            boolean trouve = false;
//                                            // verfie si on a déja ajouté la transition
//                                            if (grpEtats.get(j).getTransitions() != null) {
//                                                for (int n = 0; n < grpEtats.get(j).getTransitions().size(); n++) {
//                                                    ArrayList q = (ArrayList) (grpEtats.get(j).getTransitions().get(n));
//                                                    if (q.get(1).equals((String) ((ArrayList<?>) a.getEtats().get(i).getTransitions().get(m)).get(1))) {
//                                                        trouve = true;
//                                                        // ce qu'on pourrait faire c'est ajouter ici  a.getEtats().get(i).getTransitions().get(m)).get(0)
//                                                        // à une liste qui formerait l'etat ou envoie l'etat groupé en cours
//                                                    }
//                                                }
//                                            }
//                                            // s'il n'existte pas envore de transition avec cette lettre la on ajoute
//                                            if (!trouve) {
//                                                ArrayList<String> al = new ArrayList<String>();
//                                                // cette ligne a un probleme
//                                                al.add(grpEtats.get(l).getNom());
//                                                al.add((String) ((ArrayList<?>) a.getEtats().get(i).getTransitions().get(m)).get(1));
//                                                if (grpEtats.get(j).getTransitions() != null) {
//                                                    grpEtats.get(j).getTransitions().add(al);
//                                                } else {
//                                                    // à regler le cas ou les transitions sont vides
////                                                grpEtats.get(j).getTransitions() = new ArrayList();
//                                                    grpEtats.get(j).setTransitions(new ArrayList());
//                                                    grpEtats.get(j).getTransitions().add(al);
//
//                                                }
//
//                                        }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        }
//                    }
//                }
//            }
//
//        System.out.println(grpTransitionsTemp);
//            for (int i=0;i<grpEtats.size();i++){
//                System.out.println(grpEtats.get(i).getNom()+"  "+grpEtats.get(i).getTransitions());
//            }
//        System.out.println(((ArrayList<?>) a.getEtats().get(0).getTransitions().get(0)).get(1));

//        List<List> lists = new ArrayList<List>();
//        for (int i = 0; i < 4; i++) {
//            List list = new ArrayList();
//            list.add(i);
//            list.add("a");
//            lists.add(list);
//            // Use the list further...
//        }
//        System.out.println(lists);


    }

}
