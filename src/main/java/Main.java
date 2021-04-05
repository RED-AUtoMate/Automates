import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException{
        Images images = new Images();
        JsonDeal jsonDeal = new JsonDeal();
        Automates automates = new Automates();

        ArrayList<String> alphabet= new ArrayList<>();
        alphabet.add("a");
        alphabet.add("b");
        alphabet.add("c");
        jsonDeal.random(alphabet, 6,  3);


//
//        /* l'ésxpression régulière et l'alphabet utilisé */
//        String expression = "b(ab)*+(ba)*b";
//        String[] alphabet = {"a","b"};
//
//        /* création de l'automate de Thompson */
//        Automates thompson = automates.thompson(expression, alphabet);
//
//        /* Transformation en un objet JSON */
//        jsonDeal.jsonToJsonFile(jsonDeal.automate_to_json(thompson),"test");
//
//        /* Creation du fichier dot */
//        images.jsonToDot("test.json", "test.dot");
//
//        /* Générationdu sujet */
//        images.latexCreate(thompson);


    }
}