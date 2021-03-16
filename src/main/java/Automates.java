//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Automates {
    Stack<String> automates = new Stack();
    private Etats etatDepart;
    private List<Etats> etatsArrivee;
    private List<Etats> etats;
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
        return this.etatDepart;
    }

    public void setEtatDepart(Etats etatDepart) {
        this.etatDepart = etatDepart;
    }

    public List<Etats> getEtatsArrivee() {
        return this.etatsArrivee;
    }

    public void setEtatsArrivee(List<Etats> etatsArrivee) {
        this.etatsArrivee = etatsArrivee;
    }

    public List<Etats> getEtats() {
        return this.etats;
    }

    public void setEtats(List<Etats> etats) {
        this.etats = etats;
    }

    public List<String> getAlphabet() {
        return this.alphabet;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public void toMatrice() {
    }

    public void determiniser() {
    }



    boolean presentIn(char mot, String[] alphabet) {
        boolean not_preset = true;

        for(int i = 0; i < alphabet.length; ++i) {
            if (alphabet[i].equals(String.valueOf(mot))) {
                return false;
            }
        }

        return true;
    }

    /* l'application de l'automate de thompson à une expression reguliere donnée */
    /* la fonction construit des sous-automates de thompson a fur et a mesure de l'analyse de
    * l'expression reguliere en utilisant une pile pour les operations (+, .) et une autre
    * pour les automates construit
    * -Pour un caractere appartenant à l'alphabet ou une etoile de kleen, on lui
    *  construit son automate associé
    * -Pour un . de concatenation on l'empile dans la pile d'operations
    * -Pour un + on depile de la pile d operation tant que le sommet est un . pour conserver la
    * priorité de . sur + en construisant l'automate associé à chaque iteration :(a.b) puis l'empiler
    * dans la pile d'automates
    * -Pour une parethese ouvrante, on l empile dans la pile d'operations
    * -Pour une parenthese fermante on depile un operateur de la pileM et deux automates, puis
    * construire l'automate de thompson associe à l auperation et finalement l'empiler
    * on repete jusqu'a avoir la parenthese ouvrante*/

    public Automates thompson(String expression, String[] alpha) {
        Stack<Automates> pileA = new Stack();
        Stack<Character> pileM = new Stack();
        int etati = 0;


        for(int i = 0; i < expression.length(); ++i) {
            Automates ap;
            ArrayList apEtats;

            /* cas ou c est un charactere de l alphabet */
            if (!this.presentIn(expression.charAt(i), alpha)) {
                ap = new Automates();
                Etats etd = new Etats();
                etd.setNom(String.valueOf(etati));
                ++etati;
                Etats etf = new Etats();
                etf.setNom(String.valueOf(etati));
                ++etati;
                ArrayList<String> config = new ArrayList();
                config.add(etf.getNom());
                config.add(String.valueOf(expression.charAt(i)));
                ArrayList trs = new ArrayList();
                ArrayList trs1 = new ArrayList();
                etf.setTransitions(trs);
                trs1.add(config);
                etd.setTransitions(trs1);
                List<Etats> etfs = new ArrayList();
                etfs.add(etf);
                List<String> al = this.alphabet;
                apEtats = new ArrayList();
                apEtats.add(etd);
                apEtats.add(etf);
                ap.setAlphabet(al);
                ap.setEtats(apEtats);
                ap.setEtatsArrivee(etfs);
                ap.setEtatDepart(etd);
                pileA.push(ap);
            }

            if (expression.charAt(i) == '(') {
                System.out.println('(');
                pileM.push('(');
            }

            if (expression.charAt(i) == '.') {
                pileM.push('.');
                System.out.println(".");
            }


            if (expression.charAt(i) == '+') {
                while (pileM.peek() == '.'){
                    ap = new Automates();
                    char exp = (Character)pileM.pop();
                    System.out.println("exp "+exp);
                    Automates ap2 = (Automates)pileA.pop();
                    Automates ap1 = (Automates)pileA.pop();
                    ap.setAlphabet(ap2.getAlphabet());
                    apEtats = new ArrayList();

                    int j;
                    for(j = 0; j < ap1.getEtats().size(); ++j) {
                        apEtats.add(ap1.getEtats().get(j));
                    }

                    for(j = 0; j < ap2.getEtats().size(); ++j) {
                        apEtats.add(ap2.getEtats().get(j));
                    }

                    ap.setEtats(apEtats);
                    int indiceF1 = ap.get_etat(ap, ((Etats)ap1.getEtatsArrivee().get(0)).getNom());
                    int indiceD2 = ap.get_etat(ap, ap2.getEtatDepart().getNom());
                    ((Etats)ap.getEtats().get(indiceF1)).getTransitions().addAll(ap2.getEtatDepart().getTransitions());
                    ap.getEtats().remove(indiceD2);
                    ap.setEtatDepart(ap1.getEtatDepart());
                    ap.setEtatsArrivee(ap2.getEtatsArrivee());
                    pileA.push(ap);
                }
                pileM.push('+');
                System.out.println("+");
            }

            ArrayList config1;
            ArrayList config2;
            ArrayList trF;
            String ed1;
            if (expression.charAt(i) == '*') {
                System.out.println("*");
                ap = (Automates)pileA.pop();
                Automates a = new Automates();
                new ArrayList();
                List<Etats> etats = ap.getEtats();
                String ed = ap.getEtatDepart().getNom();
                ed1 = ((Etats)ap.getEtatsArrivee().get(0)).getNom();
                Etats etatD = new Etats();
                etatD.setNom(String.valueOf(etati));
                ++etati;
                Etats etatF = new Etats();
                etatF.setNom(String.valueOf(etati));
                ArrayList etfss = new ArrayList();
                etatF.setTransitions(etfss);
                ++etati;
                apEtats = new ArrayList();
                apEtats.add(etatF);
                ArrayList trD = new ArrayList();
                config1 = new ArrayList();
                config1.add(etatF.getNom());
                config1.add("eps");
                trD.add(config1);
                config2 = new ArrayList();
                config2.add(ed);
                config2.add("eps");
                trD.add(config2);
                etatD.setTransitions(trD);

                for(int j = 0; j < etats.size(); ++j) {
                    if (((Etats)etats.get(j)).getNom().equals(ed1)) {
                        ArrayList<String> config3 = new ArrayList();
                        config3.add(etatF.getNom());
                        config3.add("eps");
                        ((Etats)etats.get(j)).getTransitions().add(config3);
                        trF = new ArrayList();
                        trF.add(ed);
                        trF.add("eps");
                        ((Etats)etats.get(j)).getTransitions().add(trF);
                    }
                }

                etats.add(etatD);
                etats.add(etatF);
                a.setEtatsArrivee(apEtats);
                a.setEtats(etats);
                a.setEtatDepart(etatD);
                a.setAlphabet(ap.getAlphabet());
                pileA.push(a);
            }

            if (expression.charAt(i) == ')') {
                System.out.println(")");
                while (pileM.peek() != '('){
                    ap = new Automates();
                    char exp = (Character)pileM.pop();
                    System.out.println("exp "+exp);
                    Automates ap2 = (Automates)pileA.pop();
                    Automates ap1 = (Automates)pileA.pop();
                    ed1 = ap1.getEtatDepart().getNom();
                    String ed2 = ap2.getEtatDepart().getNom();
                    String ef1 = ((Etats)ap1.getEtatsArrivee().get(0)).getNom();
                    String ef2 = ((Etats)ap2.getEtatsArrivee().get(0)).getNom();
                    ap.setAlphabet(ap2.getAlphabet());
                    apEtats = new ArrayList();

                    int j;
                    for(j = 0; j < ap1.getEtats().size(); ++j) {
                        apEtats.add(ap1.getEtats().get(j));
                    }

                    for(j = 0; j < ap2.getEtats().size(); ++j) {
                        apEtats.add(ap2.getEtats().get(j));
                    }

                    ap.setEtats(apEtats);
                    switch(exp) {
                        case '+':
                            Etats etatsD = new Etats();
                            etatsD.setNom(String.valueOf(etati));
                            ++etati;
                            config1 = new ArrayList();
                            config1.add(String.valueOf(ed1));
                            config1.add("eps");
                            config2 = new ArrayList();
                            config2.add(String.valueOf(ed2));
                            config2.add("eps");
                            ArrayList trD = new ArrayList();
                            trD.add(config1);
                            trD.add(config2);
                            etatsD.setTransitions(trD);
                            ap.getEtats().add(etatsD);
                            Etats etatsF = new Etats();
                            etatsF.setNom(String.valueOf(etati));
                            ++etati;
                            trF = new ArrayList();
                            etatsF.setTransitions(trF);
                            ap.getEtats().add(etatsF);
                            ArrayList<String> config3 = new ArrayList();
                            config3.add(String.valueOf(etatsF.getNom()));
                            config3.add("eps");
                            ArrayList<String> config4 = new ArrayList();
                            config4.add(String.valueOf(etatsF.getNom()));
                            config4.add("eps");
                            ((Etats)ap.getEtats().get(this.get_etat(ap, ef1))).getTransitions().add(config3);
                            ((Etats)ap.getEtats().get(this.get_etat(ap, ef2))).getTransitions().add(config4);
                            List<Etats> efs = new ArrayList();
                            efs.add(etatsF);
                            ap.setEtatDepart(etatsD);
                            ap.setEtatsArrivee(efs);
                            pileA.push(ap);
                            break;
                        case '.':
                            int indiceF1 = ap.get_etat(ap, ((Etats)ap1.getEtatsArrivee().get(0)).getNom());
                            int indiceD2 = ap.get_etat(ap, ap2.getEtatDepart().getNom());
                            ((Etats)ap.getEtats().get(indiceF1)).getTransitions().addAll(ap2.getEtatDepart().getTransitions());
                            ap.getEtats().remove(indiceD2);
                            ap.setEtatDepart(ap1.getEtatDepart());
                            ap.setEtatsArrivee(ap2.getEtatsArrivee());
                            pileA.push(ap);
                    }

                }
                pileM.pop();
            }
        }

        return (Automates)pileA.pop();
    }



    public int get_etat(Automates automates, String nom) {
        for(int i = 0; i < automates.getEtats().size(); ++i) {
            if (((Etats)automates.getEtats().get(i)).getNom().equals(nom)) {
                return i;
            }
        }

        return -1;
    }


    public void synch3(){

        List<Etats> etats = this.getEtats();

        /* etape1 : calcule des eps-transitivitées */
        for (int i = 0; i < etats.size(); i++){
            Etats etat0 = etats.get(i);
            for (int j = 0; j < etat0.getTransitions().size(); j++){
                ArrayList config0 = (ArrayList) etat0.getTransitions().get(j);
                if (config0.get(1) == "eps"){
                    int et = this.get_etat(this, config0.get(0).toString());

                    for (int s = 0; s < this.getEtatsArrivee().size(); s++){
                        if (this.getEtatsArrivee().get(s).getNom() == this.getEtats().get(et).getNom()){
                            this.getEtatsArrivee().add(etat0);
                        }
                    }
                    for (int k = 0; k < etats.get(et).getTransitions().size(); k++){
                        ArrayList config1 = (ArrayList) etats.get(et).getTransitions().get(k);
                        if (config1.get(1) == "eps"){
                            etat0.getTransitions().add(config1);
                        }
                    }
                }
            }
        }

        /* etape 2 calcule des transitivités sans epsilons */
        for (int i = 0; i < etats.size(); i++){
            Etats etat_courant = etats.get(i);

            for (int j = 0; j < etat_courant.getTransitions().size(); j++){
                ArrayList config = (ArrayList) etat_courant.getTransitions().get(j);
                if (config.get(1) == "eps"){
                    Etats etat_inter = etats.get(this.get_etat(this, config.get(0).toString()));
                    for (int s = 0; s < this.getEtatsArrivee().size(); s++){
                        if (this.getEtatsArrivee().get(s).getNom() == this.getEtats().get(this.get_etat(this, String.valueOf(config.get(0)))).getNom()){
                            this.getEtatsArrivee().add(etat_courant);
                        }
                    }

                    for (int k = 0; k < etat_inter.getTransitions().size(); k++){
                        ArrayList trans = etat_inter.getTransitions();
                        for (int s = 0; s < trans.size(); s++){
                            ArrayList config1 = (ArrayList) trans.get(s);
                            if (config1.get(1) != "eps"){
                                etat_courant.getTransitions().add(config1);
                            }
                        }
                    }
                }
            }
        }


        /* etape 3 : suppression des eps-transitions */
        for ( int i = 0 ; i < this.getEtats().size(); i++){
            ArrayList trs = new ArrayList();
            for ( int j = 0; j < this.getEtats().get(i).getTransitions().size(); j++){
                ArrayList a = (ArrayList) this.getEtats().get(i).getTransitions().get(j);
                if(a.get(1) != "eps"){
                    if ( ! trs.contains(a)){
                        trs.add(a);
                    }
                }
            }
            this.getEtats().get(i).setTransitions(trs);
        }

        /* etape 4: suppression des etats non accessibles et des transitions double */
        ArrayList<String> etats_acce = new ArrayList<String>();
        for (int i = 0; i < this.getEtats().size(); i++){
            String nom = this.getEtats().get(i).getNom();
            for (int j = 0; j < this.getEtats().size(); j++){
                ArrayList transitions = this.getEtats().get(j).getTransitions();
                for ( int k = 0; k < transitions.size(); k++){
                    ArrayList conf = (ArrayList) transitions.get(k);
                    if (conf.get(0) == nom){
                        etats_acce.add(nom);
                    }
                }
            }
            if (this.getEtats().get(i).getNom() == this.getEtatDepart().getNom()){
                etats_acce.add(this.getEtats().get(i).getNom());
            }
        }


        /* definition des nouveaux etats */
        ArrayList<Etats> etts = new ArrayList<Etats>();
        for (int i = 0; i < this.getEtats().size(); i++){
            if (etats_acce.contains(this.getEtats().get(i).getNom())){
                etts.add(this.getEtats().get(i));
            }
        }
        this.setEtats(etts);

        /* definition des nouveaux etats finaux */
        ArrayList<Etats> arr = new ArrayList<Etats>();
        for (int i = 0; i < this.getEtatsArrivee().size(); i++){
            if (etats_acce.contains(this.getEtatsArrivee().get(i).getNom())){
                Etats ett = this.getEtats().get(this.get_etat(this, this.getEtatsArrivee().get(i).getNom()));
                if (!arr.contains(ett)){
                    arr.add(ett);
                }
            }
        }
        this.setEtatsArrivee(arr);

    }

    public boolean estFinale(Automates automates, String nom_E){
        boolean nfinale = true;
        int i = 0;
        while (nfinale && i < this.getEtatsArrivee().size()){
            if (this.getEtatsArrivee().get(i).getNom().equals(nom_E)){
                nfinale = false;
            }
            i++;
        }
        return nfinale;
    }

    public Automates minimiser(){

        /* pour chaque etat a l indice i on marque la partition auquel il appartient */
        ArrayList<String> partitions = new ArrayList<String>();
        ArrayList<String> noms = new ArrayList<String>();
        for (int i = 0; i < this.getEtats().size(); i++){
            noms.add(this.getEtats().get(i).getNom());
            if (estFinale(this, this.getEtats().get(i).getNom())){
                partitions.add("0");
            }else {
                partitions.add("1");
            }
        }
        System.out.println(noms);
        System.out.println(partitions);

        ArrayList classes = new ArrayList();
        HashMap<ArrayList, String> trs= new HashMap<ArrayList, String>();
        Etats etatD = new Etats();
        boolean modif = true;

        while (modif){
            ArrayList table_transitions = new ArrayList();
            ArrayList t_class = new ArrayList();
            HashMap<ArrayList, String> t_partition= new HashMap<ArrayList, String>();
            int vals = 0;
            for (int i = 0; i < this.getEtats().size(); i++){
                ArrayList class_transition = new ArrayList();
                ArrayList transi = new ArrayList();
                class_transition.add(partitions.get(i));
                for (int j = 0; j < this.getAlphabet().size(); j++){
                    String destination = this.avec_mot(this.getAlphabet().get(j), this.getEtats().get(i).getNom());
                    transi.add(destination);
                    if (destination == "-1"){
                        class_transition.add("-1");
                    }else {
                        class_transition.add(partitions.get(noms.indexOf(destination)));
                    }
                }
                t_class.add(class_transition);
                table_transitions.add(transi);
                if (!t_partition.containsKey(class_transition)){
                    t_partition.put(class_transition,String.valueOf(vals));
                    vals++;
                }
            }
            ArrayList nv_partition = new ArrayList();
            for (int i = 0; i < this.getEtats().size(); i++){
                nv_partition.add(t_partition.get(t_class.get(i)));
            }

            if (partitions.containsAll(nv_partition)){
                modif = false;
            }else {
                partitions = nv_partition;
            }
            classes = t_class;
            trs = t_partition;
        }

        System.out.println(classes);
        System.out.println(trs);


        HashMap<String, ArrayList> partition_finale = new HashMap<String, ArrayList>();
        for (int i = 0; i < partitions.size(); i++){
            String part = partitions.get(i);
            if (partition_finale.containsKey(part)){
                partition_finale.get(part).add(noms.get(i));
            }else {
                ArrayList a = new ArrayList();
                a.add(noms.get(i));
                partition_finale.put(part, a);
            }
        }
        System.out.println(partition_finale);

        ArrayList<Etats> etats2 = new ArrayList<Etats>();
        ArrayList<Etats> etfs = new ArrayList<Etats>();
        for (ArrayList key : trs.keySet()){
            Etats etat = new Etats();
            ArrayList t = new ArrayList();
            for (int q = 1; q < key.size(); q++){
                String a = this.alphabet.get(q-1);
                ArrayList<String > con = new ArrayList<String>();
                String tD = (String) key.get(q);
                System.out.println(tD);
                System.out.println(partition_finale.get(tD));
                con.add(partition_finale.get(tD).toString());
                con.add(alphabet.get(q-1));
                t.add(con);
            }

            boolean nnfi = true;
            int y = 0;
            while (nnfi && y < partition_finale.get(trs.get(key)).size()){
                if (estFinale(this, (String) partition_finale.get(trs.get(key)).get(y))){
                    nnfi = false;
                }
                y++;
            }
            int s =0;
            boolean deb = false;
            while (s < partition_finale.get(trs.get(key)).size()){
                if(this.getEtatDepart().getNom().equals((String) partition_finale.get(trs.get(key)).get(s))){
                    deb = true;
                }
                s++;
            }
            etat.setNom(partition_finale.get(trs.get(key)).toString());
            etat.setTransitions(t);
            if (nnfi){
                etfs.add(etat);
            }
            etats2.add(etat);
            etatD = etat;
        }

        Automates automate = new Automates();
        automate.setAlphabet(this.getAlphabet());
        automate.setEtats(etats2);
        automate.setEtatDepart(etatD);
        automate.setEtatsArrivee(etfs);


        return automate;

    }

    private String avec_mot(String mot, String etat){
        for (int i = 0; i < this.getEtats().get(this.get_etat(this, etat)).getTransitions().size(); i++){
            ArrayList<String> config = (ArrayList<String>) this.getEtats().get(this.get_etat(this, etat)).getTransitions().get(i);
            if (config.get(1).equals(mot)){
                return config.get(0);
            }
        }
        return "-1";
    }
}
