import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {
        Automates automates = new Automates();
        JsonDeal jsonDeal = new JsonDeal();
        String expr = "(b.(a.b)*+(b.a)*.b)";
        String[] alphebet = {"a","b"};
        automates = automates.thompson(expr, alphebet);
        automates.synch3();
        automates.determiniser();
        System.out.println(jsonDeal.automate_to_json(automates));

    }
}