import java.io.IOException;

public class CommandExec {
    /**
     * @param fileName the name of the dot file we want to convert to png
     * @brief a function that creates an image from an existing .dot file
     */
    public void generateImageCommand(String fileName) {
        try {
            Process process = Runtime.getRuntime().exec("dot -Tpng " + fileName + ".dot -o " + fileName + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param texFileName the name of the .tex file
     * @brief a function that creates a pdf from a given .tex file
     */
    public void generatePdfCommand(String texFileName) {
        try {
            Process process = Runtime.getRuntime().exec("pdflatex " + texFileName + ".tex");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
