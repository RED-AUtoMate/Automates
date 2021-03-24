
import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.lang.Object;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Images {
    /**
     * @param jsonFile : Nom du fichier json a convertir en dot
     * @param path     : le chemin dans lequel on veut créer le fichier dot
     * @brief : cette fonction crée un fichier .dot à partir d'un fichier .json
     */
    public void jsonToDot(String jsonFile, String path) {
        String header = "digraph automate {\n" +
                "\trankdir=LR;\n" +
                "\tsize=\"8,5\";\n" +
                "    node  [shape = circle];";
        String footer = "}";
        String attributsEtats = " [shape = circle];";
        String attributsEtatInitial = " [style = \"filled\",color =\"gray\"];";
        String attributsEtatsFinaux = " [shape = \"doublecircle\"];";
        String transitionGv = "";
        String src;
        String dst;
        String lbl;
        String etatfinalChaine = "";
        JSONParser jsonParser = new JSONParser();
        try {

            JsonDeal jsonDeal = new JsonDeal();
            Automates automates = jsonDeal.json_to_automate(jsonFile);
            String etatInitial = automates.getEtatDepart().getNom();
            List<Etats> etatFinal = automates.getEtatsArrivee();
            // Création du fichier .gv
            BufferedWriter b = new BufferedWriter(new FileWriter(path));
            System.out.println("Fichier cree avec succes");
            b.write(header);
            // Generer la ligne pour les etats initials
            b.write("\r\n" + "    " + etatInitial + " " + attributsEtatInitial);
            // Generer la ligne pour les etats finaux
            for (int i = 0; i < etatFinal.size(); i++) {
                b.write("\r\n" + "    " + etatFinal.get(i).getNom().toString().trim() + " " + attributsEtatsFinaux);
            }

            // Ecrire les transitions dans le fichier .gv
            for (int i = 0; i < automates.getEtats().size(); i++) {
                Etats e = automates.getEtats().get(i);
                src = e.getNom().toString().trim();
                ArrayList ar = e.getTransitions();
                for (int j = 0; j < ar.size(); j++) {
                    ArrayList tr = (ArrayList) ar.get(j);
                    dst = tr.get(0).toString().trim();
                    lbl = tr.get(1).toString().trim();
                    transitionGv = "    " + src + " -> " + dst + " " + "[label= \"" + lbl + "\"];";
                    b.write("\r\n" + transitionGv);
                }
            }

            b.write("\r\n" + footer);
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void latexCreate() {
        String latexString = "\\documentclass{minimal}\n" +
                "\\usepackage[autosize]{dot2texi}\n" +
                "\\usepackage[pdf]{graphviz}\n" +
                "\\usepackage{amsmath}\n" +
                "\\usepackage{graphicx}\n" +
                "\\usepackage{tikz}\n" +
                "\\usetikzlibrary{shapes,arrows}\n" +
                "\n" +
                "\\begin{document}\n" +
                "    \\vskip 5mm\n" +
                "    \\textbf{Composer sur feuille papier; numérisez votre copie (photos, scanner), puis déposez-la sur Teams, dans l'équipe du cours de Langages Formels, dans le devoir \"CC1\" avant 13h50; prévoyez 10 minutes pour le scan/dépôt!} \\\\\n" +
                "\n" +
                "    Essayez si possible de deposer un fichier PDF unique avec vos differentes pages, que vous pouvez obtenir avec une app du type CamScanner.\n" +
                "\n" +
                "\n" +
                "    \\textbf{Les rendus en retard (après 13h50) pourront être pénalisés.}\n" +
                "\n" +
                "\n" +
                "    \\emph{A composer seul. Les échanges avec toute autre personne sont interdits.}\\\\\n" +
                "\n" +
                "    On dispose de l'automate suivant\n" +
                "\n" +
                "    \\begin{enumerate}\n" +
                "        \\setcounter{enumi}{-1}\n" +
                "        \\begin{dot2tex}[neato,mathmode]\n" +
                "            %AutomateInitial\n" +
                "        \\end{dot2tex}\n" +
                "        \\newline\n" +
                "        \\item 1 - Déterminisez l'automate.\n" +
                "        \\newline\n" +
                "        \\item 2 - Normalisez l'automate.\n" +
                "        \\newline\n" +
                "        \\item 3 - Indiquez si l'automate accepte les mots suivants..\n" +
                "        \\newline\n" +
                "    \\end{enumerate}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "    \\textbf{SOLUTIONS : }\n" +
                "    \\begin{enumerate}\n" +
                "\n" +
                "        \\item 1- Automate déterministe :\n" +
                "        \\newline\n" +
                "        \\begin{dot2tex}[neato,mathmode]\n" +
                "            %AutomateDeterministe\n" +
                "        \\end{dot2tex}\n" +
                "        \\newline\n" +
                "        \\item 2 - Automate normalisé :\n" +
                "        \\newline\n" +
                "        \\begin{dot2tex}[neato,mathmode]\n" +
                "            %AutomateNormalise\n" +
                "        \\end{dot2tex}\n" +
                "\n" +
                "    \\end{enumerate}\n" +
                "\n" +
                "\\end{document}\n" +
                "\n";
        try {
            BufferedWriter b = new BufferedWriter(new FileWriter("/home/rayani00/IdeaProjects/Automates/latex.tex"));
            System.out.println("Fichier cree avec succes");
            b.write(latexString);
            b.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

