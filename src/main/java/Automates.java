//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.groupdocs.conversion.internal.a.a.Ar;
import org.a.a.S;

import java.util.*;

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


    /**
     * @param mot      The word we're searching for
     * @param alphabet The automata's alphabet
     * @return boolean
     * @brief A function that search for a given word in an automata's alphabet
     */
    boolean presentIn(char mot, String[] alphabet) {
        boolean not_preset = true;

        for (int i = 0; i < alphabet.length; ++i) {
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

    /**
     * @param alpha      The automata's alphabet
     * @param expression The regular expression we want to convert into an automata
     * @return Automate
     * @brief A function that creates an automata from any given regular expression
     */
    public Automates thompson(String expression, String[] alpha) {
        Stack<Automates> pileA = new Stack();
        Stack<Character> pileM = new Stack();
        List<String> ab = Arrays.asList(alpha);
        expression = "(" + expression + ")";
        String e = "";
        e += expression.charAt(0);
        for (int i = 1; i < expression.length(); i++) {
            if (ab.contains(String.valueOf(expression.charAt(i))) || expression.charAt(i) == '(') {
                if (ab.contains(String.valueOf(expression.charAt(i - 1))) || expression.charAt(i - 1) == ')' || expression.charAt(i - 1) == '*') {
                    e += ".";
                }
            }
            e += expression.charAt(i);
        }
        System.out.println(e);
        expression = e;
        int etati = 0;

        int l = 0;

        for (int i = 0; i < expression.length(); ++i) {
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
                apEtats = new ArrayList();
                apEtats.add(etd);
                apEtats.add(etf);
                ap.setAlphabet(Arrays.asList(alpha));
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
                while (pileM.peek() == '.') {
                    ap = new Automates();
                    char exp = (Character) pileM.pop();
                    Automates ap2 = (Automates) pileA.pop();
                    Automates ap1 = (Automates) pileA.pop();
                    ap.setAlphabet(ap2.getAlphabet());
                    apEtats = new ArrayList();

                    int j;
                    for (j = 0; j < ap1.getEtats().size(); ++j) {
                        apEtats.add(ap1.getEtats().get(j));
                    }

                    for (j = 0; j < ap2.getEtats().size(); ++j) {
                        apEtats.add(ap2.getEtats().get(j));
                    }

                    ap.setEtats(apEtats);
                    int indiceF1 = ap.get_etat(ap, ((Etats) ap1.getEtatsArrivee().get(0)).getNom());
                    int indiceD2 = ap.get_etat(ap, ap2.getEtatDepart().getNom());
                    ((Etats) ap.getEtats().get(indiceF1)).getTransitions().addAll(ap2.getEtatDepart().getTransitions());
                    ap.getEtats().remove(indiceD2);
                    ap.setEtatDepart(ap1.getEtatDepart());
                    ap.setEtatsArrivee(ap2.getEtatsArrivee());
                    pileA.push(ap);
                }
                pileM.push('+');
                System.out.println("+");
            }


            if (expression.charAt(i) == '*') {
                ArrayList config1;
                ArrayList config2;
                ArrayList trF;
                String ed1;
                System.out.println("*");
                ap = (Automates) pileA.pop();
                Automates a = new Automates();
                new ArrayList();
                List<Etats> etats = ap.getEtats();
                String ed = ap.getEtatDepart().getNom();
                ed1 = ((Etats) ap.getEtatsArrivee().get(0)).getNom();
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

                for (int j = 0; j < etats.size(); ++j) {
                    if (((Etats) etats.get(j)).getNom().equals(ed1)) {
                        ArrayList<String> config3 = new ArrayList();
                        config3.add(etatF.getNom());
                        config3.add("eps");
                        ((Etats) etats.get(j)).getTransitions().add(config3);
                        trF = new ArrayList();
                        trF.add(ed);
                        trF.add("eps");
                        ((Etats) etats.get(j)).getTransitions().add(trF);
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
                System.out.println(pileM);
                System.out.println(")");
                while (pileM.peek() != '(') {
                    ArrayList config1;
                    ArrayList config2;
                    ArrayList trF;
                    String ed1;
                    ap = new Automates();
                    char exp = (Character) pileM.pop();
                    Automates ap2 = (Automates) pileA.pop();
                    Automates ap1 = (Automates) pileA.pop();
                    ed1 = ap1.getEtatDepart().getNom();
                    String ed2 = ap2.getEtatDepart().getNom();
                    String ef1 = ((Etats) ap1.getEtatsArrivee().get(0)).getNom();
                    String ef2 = ((Etats) ap2.getEtatsArrivee().get(0)).getNom();
                    ap.setAlphabet(ap2.getAlphabet());
                    apEtats = new ArrayList();

                    int j;
                    for (j = 0; j < ap1.getEtats().size(); ++j) {
                        apEtats.add(ap1.getEtats().get(j));
                    }

                    for (j = 0; j < ap2.getEtats().size(); ++j) {
                        apEtats.add(ap2.getEtats().get(j));
                    }

                    ap.setAlphabet(Arrays.asList(alpha));
                    ap.setEtats(apEtats);
                    switch (exp) {
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
                            ((Etats) ap.getEtats().get(this.get_etat(ap, ef1))).getTransitions().add(config3);
                            ((Etats) ap.getEtats().get(this.get_etat(ap, ef2))).getTransitions().add(config4);
                            List<Etats> efs = new ArrayList();
                            efs.add(etatsF);
                            ap.setEtatDepart(etatsD);
                            ap.setEtatsArrivee(efs);
                            pileA.push(ap);
                            break;
                        case '.':
                            int indiceF1 = ap.get_etat(ap, ((Etats) ap1.getEtatsArrivee().get(0)).getNom());
                            int indiceD2 = ap.get_etat(ap, ap2.getEtatDepart().getNom());
                            ((Etats) ap.getEtats().get(indiceF1)).getTransitions().addAll(ap2.getEtatDepart().getTransitions());
                            ap.getEtats().remove(indiceD2);
                            ap.setEtatDepart(ap1.getEtatDepart());
                            ap.setEtatsArrivee(ap2.getEtatsArrivee());
                            pileA.push(ap);
                            break;
                    }

                }
                pileM.pop();
            }
        }
        return (Automates) pileA.pop();
    }


    public int get_etat(Automates automates, String nom) {
        for (int i = 0; i < automates.getEtats().size(); ++i) {
            if (((Etats) automates.getEtats().get(i)).getNom().equals(nom)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * @return Automate
     * @brif Function that synchronise an automata by removing its epsilon transitions
     */
    public Automates synch3() {

        List<Etats> etats = this.getEtats();

        /* etape1 : calcule des eps-transitivitées */

        for (int i = 0; i < etats.size(); i++) {
            for (int j = 0; j < etats.size(); j++) {
                for (int s = 0; s < etats.get(j).getTransitions().size(); s++) {
                    ArrayList<String> st = (ArrayList<String>) etats.get(j).getTransitions().get(s);
                    if (st.get(1).equals("eps") && st.get(0).equals(etats.get(i).getNom())) {
                        for (int l = 0; l < etats.get(i).getTransitions().size(); l++) {
                            ArrayList st1 = (ArrayList) etats.get(i).getTransitions().get(l);
                            if (estFinale(this, etats.get(j).getNom())) {
                                this.getEtatsArrivee().add(etats.get(j));
                            }

                            if (st1.get(1).equals("eps")) {
                                etats.get(j).getTransitions().add(st1);
                                etats.get(j).getTransitions().remove(s);
                            } else {
                                etats.get(j).getTransitions().add(st1);
                            }
                        }
                    }
                }
            }
        }



        /* etape 2 calcule des transitivités sans epsilons */
        for (int i = 0; i < etats.size(); i++) {
            Etats etat_courant = etats.get(i);

            for (int j = 0; j < etat_courant.getTransitions().size(); j++) {
                ArrayList config = (ArrayList) etat_courant.getTransitions().get(j);
                if (config.get(1).equals("eps")) {
                    Etats etat_inter = etats.get(this.get_etat(this, config.get(0).toString()));
                    for (int s = 0; s < this.getEtatsArrivee().size(); s++) {
                        if (this.getEtatsArrivee().get(s).getNom().equals(this.getEtats().get(this.get_etat(this, String.valueOf(config.get(0)))).getNom())) {
                            this.getEtatsArrivee().add(etat_courant);
                        }
                    }

                    for (int k = 0; k < etat_inter.getTransitions().size(); k++) {
                        ArrayList trans = etat_inter.getTransitions();
                        for (int s = 0; s < trans.size(); s++) {
                            ArrayList config1 = (ArrayList) trans.get(s);
                            if (!config1.get(1).equals("eps")) {
                                etat_courant.getTransitions().add(config1);
                            }
                        }
                    }
                }
            }
        }



        /* etape 3 : suppression des eps-transitions */
        for (int i = 0; i < this.getEtats().size(); i++) {
            ArrayList trs = new ArrayList();
            for (int j = 0; j < this.getEtats().get(i).getTransitions().size(); j++) {
                ArrayList a = (ArrayList) this.getEtats().get(i).getTransitions().get(j);
                if (!a.get(1).equals("eps")) {
                    if (!trs.contains(a)) {
                        trs.add(a);
                    }
                }
            }
            this.getEtats().get(i).setTransitions(trs);
        }

        /* etape 4: suppression des etats non accessibles et des transitions double */
        ArrayList<String> etats_acce = new ArrayList<String>();
        for (int i = 0; i < this.getEtats().size(); i++) {
            String nom = this.getEtats().get(i).getNom();
            for (int j = 0; j < this.getEtats().size(); j++) {
                ArrayList transitions = this.getEtats().get(j).getTransitions();
                for (int k = 0; k < transitions.size(); k++) {
                    ArrayList conf = (ArrayList) transitions.get(k);
                    if (conf.get(0).equals(nom)) {
                        etats_acce.add(nom);
                    }
                }
            }
            if (this.getEtats().get(i).getNom().equals(this.getEtatDepart().getNom())) {
                etats_acce.add(this.getEtats().get(i).getNom());
            }
        }




        /* definition des nouveaux etats */
        ArrayList<Etats> etts = new ArrayList<Etats>();
        for (int i = 0; i < this.getEtats().size(); i++) {
            if (etats_acce.contains(this.getEtats().get(i).getNom())) {
                etts.add(this.getEtats().get(i));
            }
        }

        this.setEtats(etts);

        /* definition des nouveaux etats finaux */
        ArrayList<Etats> arr = new ArrayList<Etats>();
        for (int i = 0; i < this.getEtatsArrivee().size(); i++) {
            if (etats_acce.contains(this.getEtatsArrivee().get(i).getNom())) {
                Etats ett = this.getEtats().get(this.get_etat(this, this.getEtatsArrivee().get(i).getNom()));
                if (!arr.contains(ett)) {
                    arr.add(ett);
                }
            }
        }
        this.setEtatsArrivee(arr);
        return this;
    }


    public String concatener(List etatsG) {
        if (etats == null) return "";
        else {
            String str = "";
            for (int a = 0; a < etatsG.size(); a++) {
                if (a == 0) {
                    str = (String) etatsG.get(a);
                } else {
                    str = str + "," + (String) etatsG.get(a);
                }
            }
            return str;
        }
    }

    /**
     * @param l the list we want to convert
     * @return String
     * @brief Convert a list to string
     */
    public String listToString(List l) {
        String s = "";
        for (int i = 0; i < l.size(); i++) {
            if (i == 0 && i == l.size() - 1) {
                s = "[" + l.get(i) + "]";
            } else {
                if (i == 0) {
                    s = "[" + l.get(i) + ",";
                } else {
                    if (i == l.size() - 1) {
                        s = s + l.get(i) + "]";
                    } else {
                        s = s + l.get(i) + ",";
                    }
                }
            }

        }
        return s;
    }


    public Automates determiniser() {
        // contient les lignes du tableau de determinaisation ( on l'initialise avec l'etat de depart )
        Queue<List> file_etat_cree = new LinkedList<List>();
        List debut = new ArrayList();
        debut.add(this.getEtatDepart().getNom());
        file_etat_cree.add(debut);
        List<Etats> etatsList = new ArrayList<Etats>();

        while (file_etat_cree.size() != 0) {
            List<String> etats_preced = file_etat_cree.poll();
            Etats etat = new Etats();
            etat.setTransitions(new ArrayList());
            // concatener les etats groupés : à définir
            etat.setNom(listToString(etats_preced));
            // contient les colonnes du tableau
            HashMap<String, List> hash = new HashMap<String, List>();
            for (int i = 0; i < etats_preced.size(); i++) {
                for (int j = 0; j < this.alphabet.size(); j++) {
                    Etats e = this.getEtats().get(get_etat(this, etats_preced.get(i)));
                    for (int k = 0; k < e.getTransitions().size(); k++) {
                        ArrayList al = (ArrayList) e.getTransitions().get(k);
                        if (al.get(1).equals(this.alphabet.get(j))) {
                            if (hash.containsKey(this.alphabet.get(j))) {
                                if (!hash.get(this.alphabet.get(j)).contains(((ArrayList<?>) e.getTransitions().get(k)).get(0))) {
                                    List l = (List) hash.get(this.alphabet.get(j));
                                    l.add(((ArrayList<?>) e.getTransitions().get(k)).get(0));
                                    hash.put(this.alphabet.get(j), l);
                                }
                            } else {
                                List l = new ArrayList();
                                l.add(((ArrayList<?>) e.getTransitions().get(k)).get(0));
                                hash.put(this.alphabet.get(j), l);
                            }
                        }
                    }
                }
            }
            ArrayList hashToList = new ArrayList();
            for (String key : hash.keySet()) {
                List l = hash.get(key);
                Collections.sort(l);
                ArrayList config = new ArrayList();
                config.add(0, listToString(l));
                config.add(1, key);
                hashToList.add(config);
                etat.setTransitions(hashToList);
                boolean trouve = false;
                int s = 0;
                while (!trouve && s < etatsList.size()) {
                    if (etatsList.get(s).getNom().equals(etat.getNom())) {
                        trouve = true;
                    }
                    s++;
                }
                if (!trouve || s >= etatsList.size()) {
                    etatsList.add(etat);
                    file_etat_cree.add(l);
                }
            }
        }

        List<String> q = new ArrayList<String>();
        List<Etats> new_fin = new ArrayList<Etats>();
        Iterator<Etats> it = etatsList.iterator();
        while (it.hasNext()) {
            Etats c = it.next();
            String s = c.getNom().substring(1, c.getNom().length() - 1);
            List<String> myList = new ArrayList<String>(Arrays.asList(s.split(",")));
            // l'idée serait d'ordonner c ici
            Collections.sort(myList);
            c.setNom(listToString(myList));
            if (q.contains(c.getNom())) {
                // une condition qui indique que l'on doit retirer l'élément
                it.remove();
            } else {
                q.add(c.getNom());
            }
        }
        it = etatsList.iterator();
        while (it.hasNext()) {
            Etats c = it.next();
            String s = c.getNom().substring(1, c.getNom().length() - 1);
            List<String> myList = new ArrayList<String>(Arrays.asList(s.split(",")));
            loop:
            for (String p : myList) {
                int i = get_etat(this, p.trim());
                for (int o = 0; o < this.etatsArrivee.size(); o++) {
                    if (etatsArrivee.get(o).getNom().equals(p.trim())) {
                        new_fin.add(c);
                        break loop;
                    }
                }
            }
        }
        this.etatsArrivee = new_fin;
        this.setEtats(new ArrayList<Etats>(etatsList));
        this.setEtatDepart(this.getEtats().get(0));

        return this;
    }

    /**
     * @param etat the starting etat in our case from where we want to get transitions
     * @param nom  the name of the etat
     * @brief get all the taget etat from a given transition
     */
    public int get_etat_by_transition(Etats etat, String nom) {
        for (int i = 0; i < etat.getTransitions().size(); ++i) {
            ArrayList l = (ArrayList) (etat.getTransitions().get(i));
            if (l.get(1).equals(nom)) {
                return i;
            }
        }

        return -1;
    }


    public void afficher_etats(Automates aut) {
        for (int i = 0; i < aut.getEtats().size(); i++) {
            System.out.println("Etat " + i + " : " + aut.getEtats().get(i).getNom() + " " + aut.getEtats().get(i).getTransitions());
        }
    }

    /**
     * @param s The word we're seaching for
     * @return boolean
     * @brief a function that says if a word is accepted or not by an automata
     */
    public boolean accept(String s) {
        afficher_etats(this);
        this.determiniser();
        System.out.println("\n\n\n");
        afficher_etats(this);
        int i = 0;
        Etats ec = this.etatDepart;
        List<String> transit = new ArrayList<String>();
        while (!s.equals("") && ec != null) {
            char c = s.charAt(0);

            System.out.println("juste pour le test");
            int test = get_etat_by_transition(ec, String.valueOf(c));
            if (test == -1) {
                ec = null;
            } else {
                List w = (ArrayList) ec.getTransitions().get(test);
                System.out.println(this.etats.get(1).getNom() + "   [" + w.get(0) + "]");
                if (this.etats.get(0).getNom().equals("[" + w.get(0) + "]")) {
                    System.out.println("réussie");
                }
                System.out.println("avant get etat");
                if (w.get(0).equals(this.etatDepart.getNom())) {
                    System.out.println("get etat " + get_etat(this, "[" + (String) w.get(0) + "]") + " " + w.get(0));
                    ec = this.etats.get(get_etat(this, "[" + (String) w.get(0) + "]"));
                } else {
                    ec = this.etats.get(get_etat(this, (String) w.get(0)));
                    System.out.println("get etat " + get_etat(this, (String) w.get(0)) + " " + w.get(0));
                }
                s = s.substring(1);
            }
        }

        System.out.println(s);
        if (s.equals("") && this.etatsArrivee.contains(ec)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @param automates The automate we search in
     * @param nom_E     the etat's name
     * @return boolean
     * @brief Function that says whether an etat is a final etat or not in a given automata
     */
    public boolean estFinale(Automates automates, String nom_E) {
        boolean nfinale = true;
        int i = 0;
        while (nfinale && i < this.getEtatsArrivee().size()) {
            if (this.getEtatsArrivee().get(i).getNom().equals(nom_E)) {
                nfinale = false;
            }
            i++;
        }
        return nfinale;
    }

    /**
     * @return Automates
     * @brief A function that mminimise an automata using Moore algorithm
     */
    public Automates minimiser() {

        /* pour chaque etat a l indice i on marque la partition auquel il appartient */
        ArrayList<String> partitions = new ArrayList<String>();
        ArrayList<String> noms = new ArrayList<String>();
        for (int i = 0; i < this.getEtats().size(); i++) {
            noms.add(this.getEtats().get(i).getNom());
            if (estFinale(this, this.getEtats().get(i).getNom())) {
                partitions.add("0");
            } else {
                partitions.add("1");
            }
        }

        ArrayList classes = new ArrayList();
        HashMap<ArrayList, String> trs = new HashMap<ArrayList, String>();
        Etats etatD = new Etats();
        boolean modif = true;

        while (modif) {
            ArrayList table_transitions = new ArrayList();
            ArrayList t_class = new ArrayList();
            HashMap<ArrayList, String> t_partition = new HashMap<ArrayList, String>();
            int vals = 0;
            for (int i = 0; i < this.getEtats().size(); i++) {
                ArrayList class_transition = new ArrayList();
                ArrayList transi = new ArrayList();
                class_transition.add(partitions.get(i));
                for (int j = 0; j < this.getAlphabet().size(); j++) {
                    String destination = this.avec_mot(this.getAlphabet().get(j), this.getEtats().get(i).getNom());
                    transi.add(destination);
                    if (destination == "-1") {
                        class_transition.add("-1");
                    } else {
                        class_transition.add(partitions.get(noms.indexOf(destination)));
                    }
                }
                t_class.add(class_transition);
                table_transitions.add(transi);
                if (!t_partition.containsKey(class_transition)) {
                    t_partition.put(class_transition, String.valueOf(vals));
                    vals++;
                }
            }
            ArrayList nv_partition = new ArrayList();
            for (int i = 0; i < this.getEtats().size(); i++) {
                nv_partition.add(t_partition.get(t_class.get(i)));
            }

            if (partitions.containsAll(nv_partition)) {
                modif = false;
            } else {
                partitions = nv_partition;
            }
            classes = t_class;
            trs = t_partition;
        }


        HashMap<String, ArrayList> partition_finale = new HashMap<String, ArrayList>();
        for (int i = 0; i < partitions.size(); i++) {
            String part = partitions.get(i);
            if (partition_finale.containsKey(part)) {
                partition_finale.get(part).add(noms.get(i));
            } else {
                ArrayList a = new ArrayList();
                a.add(noms.get(i));
                partition_finale.put(part, a);
            }
        }

        ArrayList<Etats> etats2 = new ArrayList<Etats>();
        ArrayList<Etats> etfs = new ArrayList<Etats>();
        for (ArrayList key : trs.keySet()) {
            Etats etat = new Etats();
            ArrayList t = new ArrayList();
            for (int q = 1; q < key.size(); q++) {
                String a = this.alphabet.get(q - 1);
                ArrayList<String> con = new ArrayList<String>();
                String tD = (String) key.get(q);
                if (partition_finale.get(tD) != null) {
                    con.add(partition_finale.get(tD).toString());
                    con.add(alphabet.get(q - 1));
                    t.add(con);
                }
            }


            boolean nnfi = true;
            int y = 0;
            while (nnfi && y < partition_finale.get(trs.get(key)).size()) {
                if (estFinale(this, (String) partition_finale.get(trs.get(key)).get(y))) {
                    nnfi = false;
                }
                y++;
            }


            if (nnfi) {
                etfs.add(etat);
            }

            etat.setNom(partition_finale.get(trs.get(key)).toString());
            etat.setTransitions(t);

            if (etat.getNom().contains(this.getEtatDepart().getNom())) {
                etatD = etat;
            }

            etats2.add(etat);

        }

        Automates automate = new Automates();
        automate.setAlphabet(this.getAlphabet());
        automate.setEtats(etats2);
        automate.setEtatDepart(etatD);

        automate.setEtatsArrivee(etfs);


        return automate;

    }

    /**
     * @param mot  The given word
     * @param etat the target etat
     * @return String
     * @brief a function that says if we can go to the given etat using the given word
     */
    private String avec_mot(String mot, String etat) {
        for (int i = 0; i < this.getEtats().get(this.get_etat(this, etat)).getTransitions().size(); i++) {
            ArrayList<String> config = (ArrayList<String>) this.getEtats().get(this.get_etat(this, etat)).getTransitions().get(i);
            if (config.get(1).equals(mot)) {
                return config.get(0);
            }
        }
        return "-1";
    }


}
