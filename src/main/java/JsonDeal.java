import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonDeal {


    public static void main(String[] args) {

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
                Etats etats1 = new Etats();

                /* pour chaque configuration on obtient le mot de l'alphabet et l'etat destination */
                for (int j = 0; j < trans.size(); j++){
                    ArrayList arrayList1 = (ArrayList) trans.get(j);
                    ArrayList arrayList = new ArrayList();

                    /* chaque configuration est representée par arraylist1 0=>e_dest, 1=>mot de l alphabet*/
                    arrayList.add(arrayList1.get(0).toString());
                    arrayList.add(arrayList1.get(1).toString());
                    Transitions transitions1 = new Transitions(arrayList);

                    /* ajout à l'ensemble de configuration */
                    arrayList2.add(arrayList);
                }
                etats1.setNom(e_init);
                etats1.setTransitions(arrayList2);



                /* instantiation d'une transition et attribution des valeurs à ses attributs*/


                /* ajout de la transition dans l'ensemble des transitions de notre automate */
                System.out.println(etats1.getNom());
                System.out.println(etats1.getTransitions());
            }





        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
