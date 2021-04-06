import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException {
        Images images = new Images();
        CommandExec commandExec = new CommandExec();
        String texContent = "    \\begin{center}\n" +
                "    {\\Large \\textbf{\\textsc{Langages formels - contrôle continu 1}}}\n" +
                "        \\\\\n" +
                "        ~\\\\\n" +
                "        {\\Huge \\textbf{\\textsc{Sujet #NBSUJET#}}}\\\\\n" +
                "        ~\\\\\n" +
                "        Année \\the\\year\n" +
                "    \\end{center}\n" +
                "\n" +
                "    \\vskip 5mm\n" +
                "    \\textbf{Composer sur feuille papier; numérisez votre copie (photos, scanner), puis déposez-la sur Teams, dans l'équipe du cours de Langages Formels, dans le devoir \"CC1\" avant 13h50; prévoyez 10 minutes pour le scan/dépôt!} \\\\\n" +
                "\n" +
                "    Essayez si possible de déposer un fichier PDF unique avec vos différentes pages, que vous pouvez obtenir avec une app du type CamScanner.\n" +
                "\n" +
                "\n" +
                "    \\textbf{Les rendus en retard (après 13h50) pourront être pénalisés.}\n" +
                "\n" +
                "\n" +
                "    \\emph{A composer seul. Les échanges avec toute autre personne sont interdits.}\\\\\n" +
                "\n" +
                "\n" +
                "    \\section{(6 points)}\n" +
                "    \\begin{figure}[htbp]\n" +
                "        \\centering\n" +
                "        \\includegraphics[width=15cm]{#AUTOMATEINITIAL}\n" +
                "        \\caption{Initial}\\label{fig:3.1}\n" +
                "    \\end{figure}\\\n" +
                "\n" +
                "    \\begin{enumerate}\n" +
                "        \\item  Determiniser l'automate donné plus haut\n" +
                "\n" +
                "        \\item Minimiser l'automate donné plus haut\n" +
                "\n" +
                "        \\item Indiquer si les mots suivants sont reconnues par l'automate : {mots ..}\n" +
                "\n" +
                "    \\end{enumerate}\n" +
                "\n" +
                "    %------------------\n" +
                "\n" +
                "    \\newpage\n" +
                "\n" +
                "    \\begin{center}\n" +
                "    {\\Large \\textbf{\\textsc}}\n" +
                "        \\\\\n" +
                "        ~\\\\\n" +
                "        {\\Huge \\textbf{\\textsc{SOLUTIONS}}}\\\\\n" +
                "        ~\\\\\n" +
                "        Année \\the\\year\n" +
                "    \\end{center}\n" +
                "    \\begin{enumerate}\n" +
                "        \\setcounter{enumi}{0}\n" +
                "\n" +
                "        \\begin{figure}[htbp]\n" +
                "            \\centering\n" +
                "            \\includegraphics[width=15cm]{#AUTOMATEDETERMINISTE}\n" +
                "            \\caption{Deterministe.}\\label{fig:3.1}\n" +
                "        \\end{figure}\\\\\n" +
                "        \\begin{figure}[htbp]\n" +
                "            \\centering\n" +
                "            \\includegraphics[width=15cm]{#AUTOMATEMINIMISE}\n" +
                "            \\caption{Minimal}\\label{fig:3.1}\n" +
                "        \\end{figure}\\\\\n" +
                "        \\begin{figure}[htbp]\n" +
                "            \\centering\n" +
                "            \\includegraphics[width=15cm]{#AUTOMATESYNCHRO}\n" +
                "            \\caption{Synchro}\\label{fig:3.1}\n" +
                "        \\end{figure}\\\\\n" +
                "    \\end{enumerate}\n" +
                "    \\setcounter{section}{0}\n" +
                "    \\newpage";
        JsonDeal jsonDeal = new JsonDeal();
        Automates automates = new Automates();
        for (int i = 0; i < 3; i++) {
            String[] al = {"a", "b"};
            ArrayList<String> arra = new ArrayList<>();
            arra.add("a");
            arra.add("b");
            arra.add("c");
            Automates th = jsonDeal.random(arra, 5, 3, 9);
            texContent = texContent.replaceAll("#NBSUJET#", String.valueOf(i));
            jsonDeal.jsonToJsonFile(jsonDeal.automate_to_json(th), "test");
            images.jsonToDot("test.json", "test.dot");
            Files.write(Paths.get("test.tex"), ("\r\n" + texContent).getBytes(), StandardOpenOption.APPEND);
            images.latexCreate(th);
            commandExec.generatePdfCommand("test" + i + "");

        }
        Files.write(Paths.get("test.tex"), "\r\n\\end{document}".getBytes(), StandardOpenOption.APPEND);
    }
}