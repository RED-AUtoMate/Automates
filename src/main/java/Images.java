import com.groupdocs.*;
import com.groupdocs.conversion.options.convert.ConvertOptions;
import com.groupdocs.foundation.*;
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

import static com.groupdocs.metadata.MetadataKey.DocumentInfo.FileType;

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
//            // Load the source DOT file to be converted
//            Converter converter = new Converter("test.dot");
//            // Get the convert options ready for the target PNG format
//            ConvertOptions convertOptions = new FileType().fromExtension("png").getConvertOptions();
//            // Convert to PNG format
//            converter.convert("output.png", convertOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

