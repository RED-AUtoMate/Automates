import java.io.IOException;

public class CommandExec {
    public void generateImageCommand() {
        try {
            Process process = Runtime.getRuntime().exec("dot -Tpng test.dot -o file.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generatePdfCommand() {
        try {
            Process process = Runtime.getRuntime().exec("pdflatex test.tex");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
