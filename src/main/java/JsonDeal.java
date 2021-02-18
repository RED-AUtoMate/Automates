import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JsonDeal {

    public void generer_aleatoirement(int nb_automates, int nb_etats, String[] alphabet, int nb_transition, int max_config_par_etat){
        JSONObject jsonAutomates = new JSONObject();
        ArrayList arrayList00 = new ArrayList();
        Random random = new Random();
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < nb_transition; j++){
                ArrayList transitions = new ArrayList();
                int a = random.nextInt(max_config_par_etat+1);
                if (a != 0){
                    ArrayList arrayList = new ArrayList();
                    for (int k = 0; k < a; k++ ){
                        int b = random.nextInt(alphabet.length);
                        int c = random.nextInt(nb_etats);
                        ArrayList config = new ArrayList();
                        config.add(String.valueOf(c));
                        config.add(alphabet[b]);
                        arrayList.add(config);
                    }
                    transitions.add(a);
                    transitions.add(arrayList);
                }else {
                }
                if (transitions.size() != 0){
                    arrayList00.add(transitions);
                }
            }
            System.out.println(arrayList00);
            HashMap hashMap = new HashMap();
            for (int j =0; j < arrayList00.size(); j++){
                ArrayList a = (ArrayList) arrayList00.get(j);
                System.out.println(a.get(0));
                if (hashMap.containsKey(a)){
                    ArrayList ab = (ArrayList) hashMap.get(a);

                }
                hashMap.put(a.get(0),a.get(1));
            }
            System.out.println(hashMap);
        }

    }

    public static void main(String[] args) {

        JsonDeal jsonDeal = new JsonDeal();
        jsonDeal.generer_aleatoirement(5, 4, new String[]{"a", "b", "c"}, 6, 3);
        JSONParser jsonParser = new JSONParser();

        try {
            /* lecture du fichier json */
            JSONObject ob = (JSONObject) jsonParser.parse(new FileReader("test.json"));

            /* obtention du contenu du fichier json (definition de l'automate) */
            JSONArray alphabet = (JSONArray) ob.get("Alphabet");
            JSONArray etats = (JSONArray) ob.get("Etats");
            String init =  ob.get("Init").toString();
            String fin =  ob.get("Fin").toString();
            JSONArray transitions = (JSONArray) ob.get("Transitions");

            /* construction du tableau des differentes transitions */
            /* tr va contenir les transitions*/
            ArrayList<Transitions> tr = new ArrayList<Transitions>();

            /* pour chaque transition on obtien l'etat initiale et les differentes configurations */
            for (int i = 0; i < transitions.size(); i++){
                JSONArray transition =(JSONArray) transitions.get(i);


                /* obtention de l'etat source */
                String e_init = transition.get(0).toString();

                /* obtention des differentes configuration de cette transition */
                JSONArray trans = (JSONArray) transition.get(1);

                /* chaque trnsition sera representée dans un tableau arrayList2 */
                ArrayList arrayList2 = new ArrayList();

                /* pour chaque configuration on obtient le mot de l'alphabet et l'etat destination */
                for (int j = 0; j < trans.size(); j++){
                    ArrayList arrayList1 = (ArrayList) trans.get(j);
                    ArrayList arrayList = new ArrayList();

                    /* chaque configuration est representée par arraylist1 0=>e_dest, 1=>mot de l alphabet*/
                    arrayList.add(arrayList1.get(0).toString());
                    arrayList.add(arrayList1.get(1).toString());

                    /* ajout à l'ensemble de configuration */
                    arrayList2.add(arrayList);
                }

                /* instantiation d'une transition et attribution des valeurs à ses attributs*/
                Transitions transitions1 = new Transitions();
                transitions1.setEtat(e_init);
                transitions1.setTransitions(arrayList2);

                /* ajout de la transition dans l'ensemble des transitions de notre automate */
                tr.add(transitions1);
            }




        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
