import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Images {
    public static void main(String[] args) {
        String header = "digraph automate {\n" +
                "\trankdir=LR;\n" +
                "\tsize=\"8,5\"";
        String footer = "}";
        String attributsEtats = "    [shape = circle];";
        String attributsEtatInitial = "    [style = \"filled\",color =\"gray\"];";
        String attributsEtatsFinaux = "    [shape = \"doublecircle\"];";
        String automateJson = "0\n" +
                "[[0, a], [1, b], [1, d]]\n" +
                "1\n" +
                "[[1, c], [3, a], [3, b]]\n" +
                "2\n" +
                "[[2, a], [3, a]]\n";
        char transition[][] = new char[200][3];

        try {
            automateJson = automateJson.replace("]]\n", "]]\n\n").replace("[[", "[").replace("]]", "]");
            String chaineTransitions[] = automateJson.split("\n\n");
            for (int j = 0; j < chaineTransitions.length; j++) {
                // Initialiser le nombre de trasition pour un etat a 0
                int nbTrans = 0;
                // Calcul du nombre de transitions pour chaque etat
                for (int i = 0; i < chaineTransitions[j].length(); i++) {
                    if (chaineTransitions[j].charAt(i) == '[') {
                        nbTrans++;
                    }
                }
                chaineTransitions[j] = chaineTransitions[j].replace("[", "").replace("]", "").replace(",", "").replaceAll("\\s", "").trim();
                int b = 0;
                int c = 0;
                char d = chaineTransitions[j].charAt(0);
                chaineTransitions[j] = chaineTransitions[j].substring(1);
                // Remplissage du tableau transitions
                for (int k = b; k < k + nbTrans; k++) {
                    for (int h = 0; h < 3; h++) {
                        if (h == 0) {
                            transition[k][h] = d;
                        } else {
                            transition[k][h] = chaineTransitions[j].charAt(c);
                            c++;
                        }
                    }
                    b = k;
                }

            }
            BufferedWriter b = new BufferedWriter(new FileWriter("/home/rayani00/test.gv"));
            System.out.println("Fichier cree avec succes");
            b.write(header);
            b.write("\r\n" + attributsEtats);
            b.write("\r\n" + attributsEtatInitial);
            b.write("\r\n" + attributsEtatsFinaux);
            b.write("\r\n" + footer);
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
