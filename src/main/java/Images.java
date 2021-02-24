import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.lang.Object;
import java.io.*;
import java.util.ArrayList;

public class Images {


    public static void automate_to_graphviz(Automates automates){
        JsonDeal jsonDeal= new JsonDeal();
        Automates a = jsonDeal.json_to_automate("test.json");
        for (int i = 0; i < a.getEtats().size(); i++){
            Etats e = a.getEtats().get(i);
            System.out.println("source=" + e.getNom());
            ArrayList ar = e.getTransitions();
            for (int j = 0; j < ar.size(); j++){
                ArrayList tr = (ArrayList) ar.get(j);
                System.out.println("dest = " + tr.get(0));
                System.out.println("mot = " + tr.get(1));
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        String header = "digraph automate {\n" +
                "\trankdir=LR;\n" +
                "\tsize=\"8,5\";\n" +
                "    node  [shape = circle];";
        String footer = "}";
        String attributsEtats = " [shape = circle];";
        String attributsEtatInitial = " [style = \"filled\",color =\"gray\"];";
        String attributsEtatsFinaux = " [shape = \"doublecircle\"];";
        String automateJson = "0\n" +
                "[[0, a], [1, b], [1, d]]\n" +
                "1\n" +
                "[[1, c], [3, a], [3, b]]\n" +
                "2\n" +
                "[[2, a], [3, a]]\n" +
                "4\n" +
                "[[2, a], [3, a]]\n";
        char transition[][] = new char[10][3];
        int g = 0;
        String transitionGv = "";
        char src;
        char dst;
        char lbl;
        char tabEtats[] = new char[30];
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("test.json"));
            JSONArray alphabet = (JSONArray) jsonObject.get("Alphabet");
            JSONArray etats = (JSONArray) jsonObject.get("Etats");
            String init = jsonObject.get("Init").toString();
            String fin = jsonObject.get("Fin").toString();
            // Converting JSONArray to ArrayList
            JSONArray transitions = (JSONArray) jsonObject.get("Transitions");
            String etatInitial = init;
            String etatFinal = fin;
            // Création du fichier .gv
            BufferedWriter b = new BufferedWriter(new FileWriter("/home/rayani00/IdeaProjects/Automates/test.dot"));
            System.out.println("Fichier cree avec succes");
            automateJson = automateJson.replace("]]\n", "]]\n\n").replace("[[", "[").replace("]]", "]");
            String chaineTransitions[] = automateJson.split("\n\n");
            for (int j = 0; j < chaineTransitions.length; j++) {
                // Initialiser le nombre de transitions pour un etat a 0
                int nbTrans = 0;
                // Calcul du nombre de transitions pour chaque etat
                for (int i = 0; i < chaineTransitions[j].length(); i++) {
                    if (chaineTransitions[j].charAt(i) == '[') {
                        nbTrans++;
                    }
                }
                chaineTransitions[j] = chaineTransitions[j].replace("[", "").replace("]", "").replace(",", "").replaceAll("\\s", "").trim();
                // Pour avancer dans la chaine de caractere
                int c = 0;
                // Recuperer le premier element qui est la source et le supprimer juste apres
                char d = chaineTransitions[j].charAt(0);
                chaineTransitions[j] = chaineTransitions[j].substring(1);
                // Remplissage du tableau transitions
                for (int k = g; k < g + nbTrans; k++) {
                    for (int h = 0; h < 3; h++) {
                        if (h == 0) {
                            transition[k][h] = d;
                        } else {
                            transition[k][h] = chaineTransitions[j].charAt(c);
                            c++;
                        }
                    }
                }
                g = g + nbTrans;
                c = 0;
            }
            // Recupere le nombre de lignes et de colonnes du la matrice transitions
            int nbCols = transition[0].length;
            int nbRows = transition.length;
            int v = 0;
            b.write(header);
            // Generer la ligne pour les etats initials
            b.write("\r\n" + "    " + etatInitial + " " + attributsEtatInitial);
            // Generer la ligne pour les etats finaux
            b.write("\r\n" + "    " + etatFinal + " " + attributsEtatsFinaux);

            // Recuperer les transitions a partir du JSONArray
            for (int i = 0; i < transitions.size(); i++) {
            }
            // Ecrire les transitions dans le fichier .gv
            for (int i = 0; i < g; i++) {
                src = transition[i][0];
                dst = transition[i][1];
                lbl = transition[i][2];
                transitionGv = "    " + src + " -> " + dst + " " + "[label= \"" + lbl + "\"];";
                b.write("\r\n" + transitionGv);
            }
            b.write("\r\n" + footer);
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
