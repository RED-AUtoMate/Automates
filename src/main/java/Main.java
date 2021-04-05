import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {
        Automates automates = new Automates();
        JsonDeal jsonDeal = new JsonDeal();
        String expr = "((a+b.b)*.(b+a.a)*)";
        String[] alphebet = {"a","b","c"};
        automates = automates.thompson(expr, alphebet);
        System.out.println(jsonDeal.automate_to_json(automates));
        automates.synch3();
        System.out.println(jsonDeal.automate_to_json(automates));

    }
}