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
     * @return Automate
     * @brief function that creates a random automate
     */
    public Automates random(ArrayList<String> alphabet, int nb_etats, int nb_max_trans_etat, int nb_trs) {
        Automates nv = new Automates();

        do {
            Automates automates = new Automates();
            Random random = new Random();

            ArrayList<Etats> etats = new ArrayList<Etats>();
            ArrayList<Etats> etats_finaux = new ArrayList<Etats>();

            /* Noms et nbr de transitions pour chaque etat */
            for (int i = 0; i < nb_etats; i++) {
                Etats etat = new Etats();

                int nTrans = 0;
                nTrans = random.nextInt(nb_max_trans_etat + 1);
                boolean est_f = false;
                if (nTrans == nb_max_trans_etat) {
                    nTrans -= 1;
                    est_f = true;
                }
                if (i == 0) {
                    while (nTrans == 0) {
                        nTrans = random.nextInt(nb_max_trans_etat);
                    }
                }

                /* Transitions pour chaque etat */
                ArrayList transitions = new ArrayList();
                System.out.println("i " + i + " " + nTrans);
                for (int j = 0; j < nTrans; j++) {
                    ArrayList<String> config = new ArrayList<String>();

                    /* Differentes configurations */
                    int ind_alpha = random.nextInt(alphabet.size() + 1);
                    String alpha;
                    if (ind_alpha == alphabet.size()) {
                        alpha = "eps";
                    } else {
                        alpha = alphabet.get(ind_alpha);
                    }
                    System.out.println(alpha);

                    /* configuration */
                    int dest = random.nextInt(nb_etats);
                    config.add(String.valueOf(dest));
                    config.add(alpha);
                    transitions.add(config);

                }

                etat.setNom(String.valueOf(i));
                etat.setTransitions(transitions);
                etats.add(etat);
                if (est_f) {
                    etats_finaux.add(etat);
                }
            }
            automates.setEtatDepart(etats.get(0));
            automates.setEtats(etats);
            automates.setEtatsArrivee(etats_finaux);
            automates.setAlphabet(alphabet);

            System.out.println("Etatd: " + automates.getEtatDepart().getNom() + " " + automates.getEtatDepart().getTransitions());
            for (int i = 0; i < automates.getEtats().size(); i++) {
                System.out.println("Etat " + automates.getEtats().get(i).getNom() + ": " + automates.getEtats().get(i).getTransitions());
            }
            for (int i = 0; i < automates.getEtatsArrivee().size(); i++) {
                System.out.println("Final " + automates.getEtatsArrivee().get(i).getNom());
            }

            nv = Automates.supp_non_accessible(automates);
            System.out.println(Automates.nb_trans(nv) + " nb trs");

        } while (Automates.nb_trans(nv) < nb_trs);


        System.out.println("***********");
        System.out.println("Etatd: " + nv.getEtatDepart().getNom() + " " + nv.getEtatDepart().getTransitions());
        for (int i = 0; i < nv.getEtats().size(); i++) {
            System.out.println("Etat " + nv.getEtats().get(i).getNom() + ": " + nv.getEtats().get(i).getTransitions());
        }

        System.out.println("fffffff" + nv.getEtatsArrivee().size());
        for (int i = 0; i < nv.getEtatsArrivee().size(); i++) {
            System.out.println("Final " + nv.getEtatsArrivee().get(i).getNom() + " " + nv.getEtatsArrivee().get(i).getTransitions());
        }
        return nv;
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