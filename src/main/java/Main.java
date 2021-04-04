import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {
        Automates automates;
        JsonDeal jsonDeal = new JsonDeal();
        automates = jsonDeal.json_to_automate("test.json");
        Images images = new Images();
        images.jsonToDot("test.json", "test.dot");
        images.latexCreate(automates);
    }
}