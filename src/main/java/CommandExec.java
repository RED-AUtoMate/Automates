import java.io.IOException;

public class CommandExec {
    /**
     * @param fileName : Nom du fichier .dot a convertir en png
     * @bief : Fonction qui a partir d'un fichier dot crée une image.png*/
    public void generateImageCommand(String fileName) {
        try {
            Process process = Runtime.getRuntime().exec("dot -Tpng " + fileName + ".dot -o " + fileName + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generatePdfCommand(String texFileName) {
        try {
            Process process = Runtime.getRuntime().exec("pdflatex " + texFileName + ".tex");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
