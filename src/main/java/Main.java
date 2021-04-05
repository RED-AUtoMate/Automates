import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {
        Automates automates = new Automates();
        JsonDeal jsonDeal = new JsonDeal();
        String expr = "(a+bb)*(b+aa)*";
        String[] alphebet = {"a","b"};
        automates = automates.thompson(expr, alphebet);
        automates.synch3();
        System.out.println(jsonDeal.automate_to_json(automates));
        automates.determiniser();
        System.out.println(jsonDeal.automate_to_json(automates));
        automates = automates.minimiser();
        System.out.println(jsonDeal.automate_to_json(automates));

        Images a = new Images();
        a.jsonToDot("test.json", "azerty.dot");

    }
}