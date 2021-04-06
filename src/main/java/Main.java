import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        Images images = new Images();
        JsonDeal jsonDeal = new JsonDeal();
        Automates automates = new Automates();
        String[] al = {"a", "b"};
        Automates th = automates.thompson("b(ab)*+(ba)*b", al);
        jsonDeal.jsonToJsonFile(jsonDeal.automate_to_json(th), "test");
        images.jsonToDot("test.json", "test.dot");
        images.latexCreate(th);
    }
}