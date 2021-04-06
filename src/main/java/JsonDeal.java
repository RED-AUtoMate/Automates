import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JsonDeal {
    int i = 0;


    /**
     * @param alphabet      the alphabet that we want to have in our random automate
     * @param nb_etats      the number of etats that we want to have
     * @param nb_transition the number of transitions that we need in our radnom automate
     * @return Automate
     * @brief function that creates a random automate
     */
    public Automates random_aut(int nb_etats, int nb_transition, List<String> alphabet) {
        Random random = new Random();

        /*
        Obtenir une liste contenant nb_etats etats numerotés de 0 jusque a nb_etats-1 [0,1,2] si n = 3
         */
        ArrayList<String> etats = new ArrayList<String>();
        for (int i = nb_etats; i > 0; i--) {
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
        for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : transitions.entrySet()) {
            Etats etats1 = new Etats();
            etats1.setNom(entry.getKey());
            etats1.setTransitions(entry.getValue());
            etatsList.add(etats1);
        }

        /*
        notre automate est pret a etre instancie et a se balader dans la memoire
        par contre il reste de definir l'etat initiale et l'ensemble d'etats fineaux
        */
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
        if (m == 0) {
            m += 1;
        }
        ArrayList<Etats> finale = new ArrayList<Etats>();
        for (int i = 0; i < m; i++) {
            finale.add(automates.getEtats().get(i));
        }
        automates.setEtatsArrivee(finale);

        /* etat inaccessibles => supp */

        for (int i = 0; i < automates.getEtats().size(); i++) {
            Etats et = automates.getEtats().get(i);
            if (!(automates.estFinale(automates, et.getNom())) && et.getTransitions() == null) {
                automates.getEtats().remove(et);
            }
        }

        ArrayList<String> etats_acce = new ArrayList<String>();
        for (int i = 0; i < automates.getEtats().size(); i++) {

            String nom = automates.getEtats().get(i).getNom();
            for (int j = 0; j < automates.getEtats().size(); j++) {

                ArrayList transitionss = automates.getEtats().get(j).getTransitions();
                for (int k = 0; k < transitionss.size(); k++) {

                    ArrayList conf = (ArrayList) transitionss.get(k);
                    if (conf.get(0).equals(nom)) {
                        etats_acce.add(nom);
                    }
                }
            }
            if (automates.getEtats().get(i).getNom().equals(automates.getEtatDepart().getNom())) {
                etats_acce.add(automates.getEtats().get(i).getNom());
            }
        }

        /* definition des nouveaux etats */
        ArrayList<Etats> etts = new ArrayList<Etats>();
        for (int i = 0; i < automates.getEtats().size(); i++) {
            if (etats_acce.contains(automates.getEtats().get(i).getNom())) {
                etts.add(automates.getEtats().get(i));
            }
        }

        automates.setEtats(etts);

        /* etats non productif => supp */

        if (automates.getEtatDepart().getTransitions() == null) {
            return null;
        }


        return automates;

    }


    /**
     * @param path the path of the needed json file
     * @return Automate
     * @brief a function that creates an automate from a given json file
     */
    public Automates json_to_automate(String path) {
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

            for (int i = 0; i < alpha.size(); i++) {
                alphabet.add(alpha.get(i).toString());
            }

            ArrayList aut_trans = new ArrayList();

            for (int i = 0; i < trans.size(); i++) {
                ArrayList transi = (ArrayList) trans.get(i);

                String depart = transi.get(0).toString();
                ArrayList config = (ArrayList) transi.get(1);

                /* building a list of configurations */
                ArrayList aut_config = new ArrayList();
                for (int j = 0; j < config.size(); j++) {

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

            for (Map.Entry<String, ArrayList> entry : hashMap.entrySet()) {
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
            for (int i = 0; i < fin.size(); i++) {
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

    /**
     * @param jsonFileName the name that we want to give to our json file
     * @param object       the Json object from where we want to create the .json file
     * @brief a function that creates a .json from a given Json object
     */
    public void jsonToJsonFile(JSONObject object, String jsonFileName) {
        String jsonFileContent = object.toJSONString();
        try {
            BufferedWriter b = new BufferedWriter(new FileWriter(jsonFileName + ".json"));
            System.out.println("Fichier " + jsonFileName + ".json crée avec succés.");
            b.write("\r\n" + jsonFileContent);
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param automates the automate we want to convert onto a JSONObject
     * @return JSONObject
     * @brief a function that creates a JSONObject from any given Automate object
     */
    public JSONObject automate_to_json(Automates automates) {

        ArrayList transitions = new ArrayList();
        for (int i = 0; i < automates.getEtats().size(); i++) {
            ArrayList etat = new ArrayList();
            Etats etats = automates.getEtats().get(i);
            etat.add(etats.getNom());
            etat.add(etats.getTransitions());
            transitions.add(etat);
        }
        List<String> alphabet = automates.getAlphabet();
        String init = automates.getEtatDepart().getNom();

        ArrayList etats = new ArrayList();
        for (int i = 0; i < automates.getEtats().size(); i++) {
            etats.add(automates.getEtats().get(i).getNom());
        }

        ArrayList finaux = new ArrayList();
        for (int i = 0; i < automates.getEtatsArrivee().size(); i++) {
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


}