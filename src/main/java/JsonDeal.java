import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JsonDeal {


    public void automate_to_json(Automates automate){
        List<String> alphabet = automate.getAlphabet();
        List<Transitions> transitions = automate.getTransitions();

    }


    /*
    Creation d'un auomate aleatoirement :
        nb_etats : le nombre d'etat que l'automate va contenir
        nb_transitions : le nombre total de transition
        alphabet : les non termineaux

        la fonction va renvoyer un objet de la classe Automate
     */
    public void random_aut(int nb_etats, int nb_transition, List<String> alphabet) {
        Random random = new Random();

        /* L'ensemble d'etats de l'automate */
        ArrayList etats = new ArrayList();
        for (int i = 1; i <= nb_etats; i++) {
            etats.add(String.valueOf(i));
        }


        /* les transitions */
        HashMap<String, ArrayList> transitions = new HashMap<String, ArrayList>();
        for (int j = 0; j < nb_transition; j++) {
            String depart = String.valueOf(random.nextInt(nb_etats));
            String destination = String.valueOf(random.nextInt(nb_etats));
            String mot = alphabet.get(random.nextInt(alphabet.size()));
            ArrayList<String> transition = new ArrayList<String>();
            transition.add(0, destination);
            transition.add(1, mot);
            if (transitions.containsKey(depart)) {
                ArrayList arrayList = (ArrayList) transitions.get(depart);
                arrayList.add(transition);
                transitions.put(depart, arrayList);
            } else {
                ArrayList config = new ArrayList();
                config.add(transition);
                transitions.put(depart, config);
            }
        }
        ArrayList<String> states= new ArrayList<String>();
        for (Map.Entry<String, ArrayList> entry: transitions.entrySet()){
            states.add(entry.getKey());
        }

        /* L'etat de depart et l'etat final */
        String etat_depart = String.valueOf(states.get(random.nextInt(states.size())));
        String etat_final = String.valueOf(states.get(random.nextInt(states.size())));
        System.out.println(etat_depart);
        System.out.println(etat_final);
        System.out.println(transitions);
        Etats etats_depart = new Etats();



    }

    public void generer_aleatoirement(int nb_automates, int nb_etats, String[] alphabet, int nb_transition, int max_config_par_etat) {
        JSONObject jsonAutomates = new JSONObject();
        ArrayList arrayList00 = new ArrayList();
        Random random = new Random();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < nb_transition; j++) {
                ArrayList transitions = new ArrayList();
                int a = random.nextInt(max_config_par_etat + 1);
                if (a != 0) {
                    ArrayList arrayList = new ArrayList();
                    for (int k = 0; k < a; k++) {
                        int b = random.nextInt(alphabet.length);
                        int c = random.nextInt(nb_etats);
                        ArrayList config = new ArrayList();
                        config.add(String.valueOf(c));
                        config.add(alphabet[b]);
                        arrayList.add(config);
                    }
                    transitions.add(a);
                    transitions.add(arrayList);
                } else {
                }
                if (transitions.size() != 0) {
                    arrayList00.add(transitions);
                }
            }
            System.out.println(arrayList00);
            HashMap hashMap = new HashMap();
            for (int j = 0; j < arrayList00.size(); j++) {
                ArrayList a = (ArrayList) arrayList00.get(j);
                System.out.println(a.get(0));
                if (hashMap.containsKey(a)) {
                    ArrayList ab = (ArrayList) hashMap.get(a);

                }
                hashMap.put(a.get(0), a.get(1));
            }
            System.out.println(hashMap);
        }

    }

    public static void main(String[] args) {


        JsonDeal jsonDeal = new JsonDeal();
        List<String> alphab= new ArrayList<String>();
        alphab.add("a");
        alphab.add("b");
        alphab.add("c");
        jsonDeal.random_aut(4, 8, alphab);
        //jsonDeal.generer_aleatoirement(5, 4, new String[]{"a", "b", "c"}, 6, 3);
        JSONParser jsonParser = new JSONParser();

        try {
            /* lecture du fichier json */
            JSONObject ob = (JSONObject) jsonParser.parse(new FileReader("test.json"));

            /* obtention du contenu du fichier json (definition de l'automate) */
            JSONArray alphabet = (JSONArray) ob.get("Alphabet");
            JSONArray etats = (JSONArray) ob.get("Etats");
            String init = ob.get("Init").toString();
            String fin = ob.get("Fin").toString();
            JSONArray transitions = (JSONArray) ob.get("Transitions");

            /* construction du tableau des differentes transitions */
            /* tr va contenir les transitions*/
            ArrayList<Transitions> tr = new ArrayList<Transitions>();

            /* pour chaque transition on obtien l'etat initiale et les differentes configurations */
            for (int i = 0; i < transitions.size(); i++) {
                JSONArray transition = (JSONArray) transitions.get(i);


                /* obtention de l'etat source */
                String e_init = transition.get(0).toString();

                /* obtention des differentes configuration de cette transition */
                JSONArray trans = (JSONArray) transition.get(1);

                /* chaque trnsition sera representée dans un tableau arrayList2 */
                ArrayList<ArrayList<String>> arrayList2 = new ArrayList<ArrayList<String>>();

                /* pour chaque configuration on obtient le mot de l'alphabet et l'etat destination */
                for (int j = 0; j < trans.size(); j++) {
                    ArrayList arrayList1 = (ArrayList) trans.get(j);
                    ArrayList<String> arrayList = new ArrayList<String>();

                    /* chaque configuration est representée par arraylist1 0=>e_dest, 1=>mot de l alphabet*/
                    arrayList.add(arrayList1.get(0).toString());
                    arrayList.add(arrayList1.get(1).toString());
                    Transitions transitions1 = new Transitions(arrayList);

                    /* ajout à l'ensemble de configuration */
                    arrayList2.add(arrayList);
                }

                /* instantiation d'une transition et attribution des valeurs à ses attributs*/



                /* ajout de la transition dans l'ensemble des transitions de notre automate */
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
