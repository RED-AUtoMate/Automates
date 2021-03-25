import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.lang.Object;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            b.write("\r\n" + "    " + this.entreGuillemets(etatInitial) + " " + attributsEtatInitial);
            // Generer la ligne pour les etats finaux
            for (int i = 0; i < etatFinal.size(); i++) {
                b.write("\r\n" + "    " + this.entreGuillemets(etatFinal.get(i).getNom().toString().trim()) + " " + attributsEtatsFinaux);
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
                    transitionGv = "    " + this.entreGuillemets(src) + " -> " + this.entreGuillemets(dst) + " " + "[label= \"" + lbl + "\"];";
                    b.write("\r\n" + transitionGv);
                }
            }

            b.write("\r\n" + footer);
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String entreGuillemets(String mot) {
        return ("\"" + mot + "\"");
    }

    public void latexCreate(Automates automates) throws IOException {
        Images images = new Images();
        JsonDeal jsonDeal = new JsonDeal();
        CommandExec commandExec = new CommandExec();


        // Create the JSONObject for the initial automate
        JSONObject object = jsonDeal.automate_to_json(automates);
        // Create the .json for initial automate
        jsonDeal.jsonToJsonFile(object, "automateInitial");
        // Create initial Automate .dot file
        images.jsonToDot("automateInitial.json", "automateInitial.dot");
        // Create initial automate .png
        commandExec.generateImageCommand("automateInitial");


        // Create the JSONObject for the determinist automate
        object = jsonDeal.automate_to_json(automates.determiniser());
        // Create the .json for determinist automate
        jsonDeal.jsonToJsonFile(object, "automateDeterministe");
        // Create determinist Automate .dot file
        images.jsonToDot("automateDeterministe.json", "automateDeterministe.dot");
        // Create determinist automate .png
        commandExec.generateImageCommand("automateDeterministe");


        // Create the JSONObject for the minimal automate
        object = jsonDeal.automate_to_json(automates.minimiser());
        // Create the .json for minimal automate
        jsonDeal.jsonToJsonFile(object, "automateMinimal");
        // Create minimal Automate .dot file
        images.jsonToDot("automateMinimal.json", "automateMinimal.dot");
        // Create minimal automate .png
        commandExec.generateImageCommand("automateMinimal");


        // Create the JSONObject for the sychro automate
        object = jsonDeal.automate_to_json(automates.synch3());
        // Create the .json for synchro automate
        jsonDeal.jsonToJsonFile(object, "automateSynchro");
        // Create synchro Automate .dot file
        images.jsonToDot("automateSynchro.json", "automateSynchro.dot");
        // Create synchro automate .png
        commandExec.generateImageCommand("automateSynchro");


        // Replace the picture into the TeX file
        Path path = Paths.get("test.tex");
        Charset charset = StandardCharsets.UTF_8;

        String content = new String(Files.readAllBytes(path), charset);
        content = content.replaceAll("#AUTOMATEINITIAL", "automateInitial.png");
        content = content.replaceAll("#AUTOMATEDETERMINISTE", "automateDeterministe.png");
        content = content.replaceAll("#AUTOMATEMINIMISE", "automateMinimal.png");
        content = content.replaceAll("#AUTOMATESYNCHRO", "automateSynchro.png");
        try {
            Files.write(path, content.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
