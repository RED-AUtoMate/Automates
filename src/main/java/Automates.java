import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Automates {

    Stack<String> automates = new Stack<String>();
    private Etats etatDepart;
    private List<Etats> etatsArrivee, etats;
    private List<String> alphabet;

    public Automates() {

    }

    public Automates(Etats etatDepart, List<Etats> etatsArrivee, List<Etats> etats, List<String> alphabet) {
        this.etatDepart = etatDepart;
        this.etatsArrivee = etatsArrivee;
        this.etats = etats;
        this.alphabet = alphabet;
    }

    public Etats getEtatDepart() {
        return etatDepart;
    }

    public void setEtatDepart(Etats etatDepart) {
        this.etatDepart = etatDepart;
    }

    public List<Etats> getEtatsArrivee() {
        return etatsArrivee;
    }

    public void setEtatsArrivee(List<Etats> etatsArrivee) {
        this.etatsArrivee = etatsArrivee;
    }

    public List<Etats> getEtats() {
        return etats;
    }

    public void setEtats(List<Etats> etats) {
        this.etats = etats;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    // ALGORITHMES UTILES


    public void toMatrice() {
        //  TO DO
    }


    // ALGORITHMES

    public void determiniser() {
        // TO DO
    }


    /* Obtenir l'equivalent de l'automate sans eps-transitions */
    public Automates synchroniser() {



        /*  Pour chaque etat on verifie s'il contient des eps-transitions
         *   avec deux cas possible
         *       1- la transition mene vers un etat final
         *       2- la transition mene vers un etat non final
         *           le traitement est le meme mais dans le 1er cas on
         *           ajoute l'etat dont il s'agit a l'ensemble des
         *           etats finaux    */

        List<Etats> etats_finaux = this.getEtatsArrivee();
        for (int i = 0; i < this.getEtats().size(); i++) {

            /* pour chaque etat on obtient son ensemble de transitions */
            Etats etat = this.getEtats().get(i);
            System.out.println(etat.getNom());
            System.out.println(etat.getTransitions());
            System.out.println("***********");
            ArrayList transitions = etat.getTransitions();

            /* pour chaque transition on verifie s'il s'agit d'une eps-transition */
            for (int j = 0; j < transitions.size(); j++) {
                ArrayList configs = (ArrayList) transitions.get(j);
                if (configs.get(1).equals("eps")) {
                    Etats eps = new Etats();
                    for (int a = 0; a < this.getEtats().size(); a++) {
                        Etats et = this.getEtats().get(a);
                        if (et.getNom().equals(configs.get(0))) {
                            eps = et;
                        }
                    }

                    /* puis on verifie si elle mene vers un etat final ou non */
                    int not_final = 1;
                    int n = 0;
                    while (n < etats_finaux.size() && not_final == 1) {
                        Etats etat_final = etats_finaux.get(n);
                        if (etat_final.getNom().equals(configs.get(0))) {
                            not_final = 0;
                        }
                        n++;
                    }

                    /* 1erement on supprime la transition */
                    transitions.remove(j);

                    /* si elle mene a un etat final on ajoute l'etat actuel a l'ensemble des finaux*/
                    if (not_final == 0) {
                        etats_finaux.add(etat);
                    }

                    /* on ajoute les non epsilon transitions de l'etat auquel l'eps-transition mene
                     * a l'etat actuel */
                    for (int q = 0; q < eps.getTransitions().size(); q++) {
                        ArrayList trs = (ArrayList) eps.getTransitions().get(q);
                        if (!trs.get(1).equals("eps")) {
                            transitions.add(trs);
                        }
                    }
                }
            }
        }

        System.out.println("****** apres ******");

        for (int i = 0; i < this.getEtats().size(); i++) {
            Etats etats = this.getEtats().get(i);
            System.out.println(etats.getNom());
            System.out.println(etats.getTransitions());
            System.out.println("***************");
        }

        System.out.println("*****finaux******");
        for (int i = 0; i < etats_finaux.size(); i++) {
            System.out.println(etats_finaux.get(i).getNom());
        }

        return this;
    }

//
//    Automates thompson(String expression, char[] alphabet, int i) {
//        if(expression == "#"){
//
//        }
//
//        /* Le tableau automates va contenir les automates construit au fur et a mesur de l'analyse
//         * de l'expression reguliere, a la fin on aura un seul automate issue des unions successifs
//         * des automates construits */
//
//        Automates automate1 = new Automates();
//        int k =0;
//
//        /* construire un automate de thompson correspondant à l'expression "a" ou "a" est un symbole d'alphabet */
//        if (presentIn(expression[i], alphabet)) {
//
//            ArrayList<Etats> etats = new ArrayList<Etats>();
//            ArrayList<Etats> etats_finaux = new ArrayList<Etats>();
//            List<String> alpha = new ArrayList<String>();
//
//            /* Definir l'ensemble d'alphabet */
//            alpha.add(String.valueOf(expression[i]));
//
//            /* Definir l'etat de depart */
//            Etats etat_dep = new Etats();
//            ArrayList dep = new ArrayList();
//            dep.add(k + 1);
//            dep.add(expression[i]);
//            etat_dep.setNom(String.valueOf(k));
//            etat_dep.setTransitions(dep);
//
//            /* Definir l'etat final et l'ajouter a l'ensemble d'etats finaux */
//            Etats etat_arr = new Etats();
//            ArrayList fin = new ArrayList();
//            etat_arr.setNom(String.valueOf(k + 1));
//            etat_arr.setTransitions(fin);
//            k = k + 2;
//            etats_finaux.add(etat_arr);
//
//            /* Definir l'ensemble des etats */
//            etats.add(etat_dep);
//            etats.add(etat_arr);
//
//            /* Construction de l'automate */
//            automate1.setEtats(etats);
//            automate1.setEtatDepart(etat_dep);
//            automate1.setEtatsArrivee(etats_finaux);
//            automate1.setAlphabet(alpha);
//        }
//
//
//        Automates automate = new Automates();
//        return automate;
//    }
//
//    Stack abc(String a){
//
//        if (a.length() == 0){
//            return automates;
//        }
//
//        if (a.charAt(0) == 'a' || a.charAt(0) == 'b' || a.charAt(0) == 'c'){
//            String b;
//            b = "1";
//            automates.push(b);
//
//        }
//
//        if (a.charAt(0) == '+'){
//            String b = automates.pop();
//            Stack<String> s = new Stack<String>();
//            a = a.substring(1);
//            s = abc(a);
//            b+= s.pop();
//            automates.push(b);
//            return automates;
//        }
//
//        return automates;
//    }


    boolean presentIn(char mot, String[] alphabet) {
        boolean not_preset = true;
        int i = 0;
        while (i < alphabet.length) {
            if (alphabet[i].equals(String.valueOf(mot))) {
                return false;
            }
            i++;
        }

       return true;
    }



    public Automates thompson(String expression, String[] alpha){
        Stack<Automates> pileA = new Stack<Automates>();
        Stack<Character> pileM = new Stack<Character>();
        int etati = 0;


       for (int i = 0; i < expression.length(); i++){



            /* **************************************************** */
            /* Cas ou le character courant est un mot de l alphabet */
            if (!presentIn(expression.charAt(i), alpha)){
                Automates a = new Automates();

                /* etat depart */
                Etats etd = new Etats();
                etd.setNom(String.valueOf(etati));
                etati ++;

                /* etat final */
                Etats etf = new Etats();
                etf.setNom(String.valueOf(etati));
                etati++;

                /* ajout de la config dep --> mot ---> fin */
                ArrayList<String> config = new ArrayList<String>();
                config.add(etf.getNom());
                config.add(String.valueOf(expression.charAt(i)));

                /* transitions des deux etats */
                ArrayList trs = new ArrayList();
                ArrayList trs1 = new ArrayList();
                etf.setTransitions(trs);

                trs1.add(config);
                etd.setTransitions(trs1);


                List<Etats> etfs = new ArrayList<Etats>();
                etfs.add(etf);


                /* liste d alphabet */
                List<String> al = alphabet;




                /* liste d etats */
                List<Etats> ets = new ArrayList<Etats>();
                ets.add(etd);
                ets.add(etf);


                /* parametres de l automate */
                a.setAlphabet(al);
                a.setEtats(ets);
                a.setEtatsArrivee(etfs);
                a.setEtatDepart(etd);


                /* On empile l'automate construit pour le mot dans la pile d'automate */
                pileA.push(a);


            }



            /* ************************************************ */
            /* cas ou le charactere est une parenthese ouvrante */
            if (expression.charAt(i) == '('){
                System.out.println("(");

            }



            /* ********************************************************** */
            /* cas ou le caractere courant est le point de concatination */
            if (expression.charAt(i) == '.'){
                pileM.push('.');
                System.out.println(".");

            }



            /* ************************************* */
            /* cas ou le caractere courant est un + */
            if (expression.charAt(i) == '+'){
                pileM.push('+');
                System.out.println("+");

            }



            /* ************************************* */
            /* cas ou le caractere courant est l etoile de kleen */
            if (expression.charAt(i) == '*'){

                System.out.println("*");
                /* Depiler le plus recent automate construit */
                Automates ap = pileA.pop();


                /* Chercher la liste de ses etats */
                List<Etats> etats= ap.getEtats();


                /* Ancien Etat depart et final */
                String ef = ap.getEtatsArrivee().get(0).getNom();
                String ed = ap.getEtatDepart().getNom();



                /* nouveux etats depart et final */
                Etats etatD = new Etats();
                etatD.setNom(String.valueOf(etati));
                etati++;


                Etats etatF = new Etats();
                etatF.setNom(String.valueOf(etati));
                etati++;


                /* ajout de eps-transition entre l ancien etatF et lancien etatD */
                ArrayList<String> config1 = new ArrayList<String>();
                config1.add(String.valueOf(ed));
                config1.add("eps");
                for (int j = 0; j < ap.getEtats().size(); j++){
                    System.out.println(ap.getEtats().get(j).getNom());
                }
                ap.getEtats().get(get_etat(ap,ef)).getTransitions().add(config1);


                /* ajout de eps-transition entre l'ancien et le nouvel etatF */
                ArrayList<String> config2 = new ArrayList<String>();
                config1.add(String.valueOf(etatF.getNom()));
                config1.add("eps");
                ap.getEtats().get(get_etat(ap,ef)).getTransitions().add(config2);


                /* ajout de eps transition entre le nouvel etatD et le nouvel etatF */
                ArrayList<String> config3 = new ArrayList<String>();
                config1.add(String.valueOf(etatF.getNom()));
                config1.add("eps");
                etatD.setTransitions(config3);


                /* ajout du nouvel etat final et de nouvel etat de depart a l ensemble des etats */
                ap.getEtats().add(etatD);
                ap.getEtats().add(etatF);


                ap.getEtatsArrivee().remove(0);
                ap.etatDepart = null;


                /* mise a jour de l etat final et de l etat de depart */
                ap.setEtatDepart(etatD);
                ap.getEtatsArrivee().add(etatF);

                pileA.push(ap);

            }



            /* ***************************************************** */
            /* cas ou le caractere courant est une parethese fermant */
            if (expression.charAt(i) == ')'){

                System.out.println(")");
                Automates ap = new Automates();

                /* chercher l'operation dans la pile d'operations */
                char exp = pileM.pop();

                /* on depile deux automates */
                Automates ap2 = pileA.pop();
                Automates ap1 = pileA.pop();

                /* on cherche l etat depart et final pour les deux automates */
                String ed1 = ap1.getEtatDepart().getNom();
                String ed2 = ap2.getEtatDepart().getNom();
                String ef1 = ap1.getEtatsArrivee().get(0).getNom();
                String ef2 = ap2.getEtatsArrivee().get(0).getNom();



                ap.setAlphabet(ap2.getAlphabet());
                List<Etats> apEtats = new ArrayList<Etats>();
                for (int j = 0; j < ap1.getEtats().size(); j++){
                    apEtats.add(ap1.getEtats().get(j));
                }
                for (int j = 0; j < ap2.getEtats().size(); j++){
                    apEtats.add(ap2.getEtats().get(j));
                }
                ap.setEtats(apEtats);
                System.out.println("aut deb");
                for (int k = 0; k < ap.getEtats().size(); k++){
                    System.out.println("deb etat");
                    System.out.println(ap.getEtats().get(k).getNom());
                    System.out.println(ap.getEtats().get(k).getTransitions());
                    System.out.println("fin etat");
                }
                System.out.println("aut fin");


                switch (exp){

                    case '+' :
                        /* nouvel etat depart */
                        Etats etatsD = new Etats();
                        etatsD.setNom(String.valueOf(etati));
                        etati++;


                        /* ajout des deux eps-transitions entre le nouvel ED et les deux anciens ED */
                        ArrayList<String> config1 = new ArrayList<String>();
                        config1.add(String.valueOf(ed1));
                        config1.add("eps");

                        ArrayList<String> config2 = new ArrayList<String>();
                        config1.add(String.valueOf(ed2));
                        config1.add("eps");

                        ArrayList trD = new ArrayList();
                        trD.add(config1);
                        trD.add(config2);

                        etatsD.setTransitions(trD);
                        ap.getEtats().add(etatsD);



                        /* nouvel etat final */
                        Etats etatsF = new Etats();
                        etatsF.setNom(String.valueOf(etati));
                        etati++;
                        ArrayList trF = new ArrayList();
                        etatsF.setTransitions(trF);
                        ap.getEtats().add(etatsF);

                        /* ajout des deux eps-transitions entre le nouvel EF et les deux anciens EF */
                        ArrayList<String> config3 = new ArrayList<String>();
                        config3.add(String.valueOf(etatsF.getNom()));
                        config3.add("eps");

                        ArrayList<String> config4 = new ArrayList<String>();
                        config4.add(String.valueOf(etatsF.getNom()));
                        config4.add("eps");


                        ap.getEtats().get(get_etat(ap,ef1)).getTransitions().add(config3);
                        ap.getEtats().get(get_etat(ap,ef2)).getTransitions().add(config4);




                        List<Etats> efs = new ArrayList<Etats>();
                        efs.add(etatsF);




                        ap.setEtatDepart(etatsD);
                        ap.setEtatsArrivee(efs);

                        pileA.push(ap);
                    break;


                    case '.' :

                    break;

                }


            }


        }

        return pileA.pop();
    }

    public String next_parenthese(String expression){

        int n = expression.length();
        int ouv = 1;
        int ferm = 0;
        int i =1;

        while (i < n && ouv > ferm){
            if(expression.charAt(i) == '('){
                ouv +=1 ;
            }
            if (expression.charAt(i) == ')'){
                ferm += 1;
            }

            i++;
        }

        System.out.println(i);

        return expression.substring(1, i-1);
    }

    public int get_etat(Automates automates, String nom){
        for(int i = 0; i < automates.getEtats().size(); i++){
            if (automates.getEtats().get(i).getNom().equals(nom)){
                return i;
            }
        }
        return -1;
    }




}
