import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Images images = new Images();
        CommandExec commandExec = new CommandExec();
        JsonDeal jsonDeal = new JsonDeal();
        Automates automates = new Automates();
        String nomPdf;
        ArrayList<String> alphabet =new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            alphabet.add("a");
            alphabet.add("b");
            alphabet.add("c");
            automates = jsonDeal.random(alphabet,5,4,3);
            JSONObject object = jsonDeal.automate_to_json(automates);
            jsonDeal.jsonToJsonFile(object,"test");
            images.jsonToDot("test.json", "test.dot");
            images.latexCreate(automates);
            commandExec.generatePdfCommand();
            nomPdf = "test" + i + "";
            Runtime.getRuntime().exec("mv test.pdf " + nomPdf + ".pdf");
        }
    }
}
