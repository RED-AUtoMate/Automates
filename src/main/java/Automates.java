//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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

    public Automates thompson(String expression, String[] alpha) { Stack<Automates> pileA = new Stack();
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


    public Automates synch3(){

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

        return this;

    }


    public String concatener(List etatsG){
        if(etats == null) return "";
        else {
            String str = "";
            for(int a=0;a<etatsG.size();a++){
                if ( a == 0 ){
                    str = (String) etatsG.get(a);
                }else {
                    str = str + "," + (String) etatsG.get(a);
                }
            }
            return str;
        }
    }




    public String listToString(List l){
        String s ="";
        for (int i =0 ; i<l.size();i++){
            if (i == 0 && i == l.size()-1){
                s = "[" + l.get(i) + "]";}
            else {
                if (i == 0){
                    s = "[" + l.get(i) + ",";
                }
                else {if(i == l.size()-1){
                    s = s + l.get(i) + "]";
                }
                else {
                    s = s + l.get(i) +",";
                }
                }}

        }
        return s;
    }



    public Automates determiniser(){
        // contient les lignes du tableau de determinaisation ( on l'initialise avec l'etat de depart )
        Queue<List> file_etat_cree = new LinkedList<List>();
        List debut = new ArrayList();
        debut.add(this.getEtatDepart().getNom());
        file_etat_cree.add(debut);
        List<Etats> etatsList = new ArrayList<Etats>();

        while (file_etat_cree.size()!=0){
            List<String> etats_preced = file_etat_cree.poll();
//            System.out.println(etats_preced);
//            file_etat_cree.remove(etats_preced);
//            System.out.println(file_etat_cree);
            Etats etat = new Etats();
            etat.setTransitions(new ArrayList());
            // concatener les etats groupés : à définir
//            etat.setNom(etats_preced.toString());
            etat.setNom(listToString(etats_preced));
            // contient les colonnes du tableau
            HashMap<String,List> hash = new HashMap<String,List>();

            for(int i =0;i<etats_preced.size();i++){
                // on pourrait declarer l ici
                // ICI on pourrait faire un parcours des successeurs de etats_preced pour les ajouter à hash
                // on pourrait meme remplacer la boucle suivante
                // sans oublier de remplacer les alphabet.get/size par les vriables adéquates
                for (int j=0;j<this.alphabet.size();j++) {
                    Etats e = this.getEtats().get(get_etat(this, etats_preced.get(i)));
//                    System.out.println(e.getNom());
                    for (int k = 0; k < e.getTransitions().size(); k++) {
                        //verifier cette ligne la condition est inutile : on ajoute tous les etats sans distinction
                        // on devrait plutot verifier si ils ont alphabet.get(j) comme successeur
                        ArrayList al = (ArrayList) e.getTransitions().get(k);
                        if (al.get(1).equals(this.alphabet.get(j))) {
                            if (hash.containsKey(this.alphabet.get(j))) {
                                if (!hash.get(this.alphabet.get(j)).contains(((ArrayList<?>) e.getTransitions().get(k)).get(0))){
                                    // l'idée est là
                                    List l = (List) hash.get(this.alphabet.get(j));
                                    // pourquoi etats_preced alors qu'en bas on met un get(i)???
                                    l.add(((ArrayList<?>) e.getTransitions().get(k)).get(0));
                                    hash.put(this.alphabet.get(j), l);}
                            } else {
                                //ici ce sera le nom de l'etat i qui nous sera utile
                                List l = new ArrayList();
                                l.add(((ArrayList<?>) e.getTransitions().get(k)).get(0));
                                hash.put(this.alphabet.get(j), l);
                            }
                        }
                    }
                }
            }
//            System.out.println(hash);
            // à revoir
            // le fait que c'est à l'exterieur de la boucle peut poser des problemes de reinnitialisation
            ArrayList hashToList = new ArrayList();
            for(String key: hash.keySet()){


                List l = hash.get(key);
                Collections.sort(l);


                ArrayList config = new ArrayList();
                // je pense qu'ici on devrait ajouter la key au lieu de get(key) PAS SÛR
//                config.add(0,hash.get(key).toString());
                config.add(0,listToString(l));
//                config.add(1,hash.get(key));
                config.add(1,key);
//                System.out.println(config.get(0).getClass());
                hashToList.add(config);
                etat.setTransitions(hashToList);
//                System.out.println(etat.getNom()+" "+etat.getTransitions() +file_etat_cree.size());
//                if (!etatsList.contains(etat)) {
                boolean trouve = false;
                int s=0;
                while(!trouve && s < etatsList.size()){
                    if (etatsList.get(s).getNom().equals(etat.getNom())){
                        trouve = true;
                    }
                    s++;
                }
                if(!trouve || s>=etatsList.size()) {
                    etatsList.add(etat);
//                if(!etat.getTransitions().contains(config)) {
//                    file_etat_cree.add(hash.get(key));
//                    System.out.println("hash = " + hash.get(key));
//                    List l = hash.get(key);
//                    Collections.sort(l);
//                    System.out.println("l = "+l);
                    file_etat_cree.add(l);
//                }
//                }
                }
            }
        }
//        System.out.println(etatsList);
//        for(Etats e : etatsList){
//            String s = e.getNom().substring(1,e.getNom().length()-1);
//            List<String> myList = new ArrayList<String>(Arrays.asList(s.split(",")));
//            Collections.sort(myList);
////            System.out.println(myList);
//            e.setNom(myList.toString());
//        }
        List<String> q = new ArrayList<String>();
//        for(Etats e : etatsList){
//            if(!q.contains(e.getNom()))
//            q.add(e.getNom());
//            else
//                etatsList.remove(e);
//        }

        List<Etats> new_fin = new ArrayList<Etats>();
        Iterator<Etats> it = etatsList.iterator();
        while( it.hasNext() ) {

            Etats c = it.next();

            String s = c.getNom().substring(1,c.getNom().length()-1);
            List<String> myList = new ArrayList<String>(Arrays.asList(s.split(",")));

            // l'idée serait d'ordonner c ici
            Collections.sort(myList);
//            System.out.println(myList);
            c.setNom(listToString(myList));
//            System.out.println("c= "+c.getNom());
//            for (int b =0;b<c.getTransitions().size();b++){
//                List tr = (List)c.getTransitions().get(b);
//                List<String> ml = new ArrayList<String>(Arrays.asList(((String)tr.get(0)).split(",")));
//                Collections.sort(ml);
////                ((List) c.getTransitions().get(b)).get(0) = listToString(ml);
//                List l = new ArrayList();
//                l.add(listToString(ml));
//                l.add(((List<?>) c.getTransitions().get(b)).get(1));
//                ((List) c.getTransitions().get(b)).set(b,l);
//
//            }
//            System.out.println("s="+s);
            if( q.contains(c.getNom()) ) { // une condition qui indique que l'on doit retirer l'élément
                it.remove();
            }
            else {
                q.add(c.getNom());
            }

        }
        System.out.println("q="+q);

        it = etatsList.iterator();
        while( it.hasNext() ) {

            Etats c = it.next();
            String s = c.getNom().substring(1,c.getNom().length()-1);
            List<String> myList = new ArrayList<String>(Arrays.asList(s.split(",")));
            loop:
            for (String p : myList) {
                int i = get_etat(this, p.trim());
//                System.out.println(i);
                for (int o = 0; o < this.etatsArrivee.size(); o++) {
                    if (etatsArrivee.get(o).getNom().equals(p.trim())) {
                        new_fin.add(c);
                        break loop;
                    }
                }
            }
        }
        System.out.println("etatlist " + etatsList);


        this.etatsArrivee = new_fin;

//        Set<Etats> mySet = new HashSet<Etats>(etatsList);
//        System.out.println(etatsList);
//        for (int j =0;j<etatsArrivee.size();j++){
//        System.out.println(etatsArrivee.get(j).getNom()+" 1");}
        this.setEtats(new ArrayList<Etats>(etatsList));
//        System.out.println(etatDepart.getNom()+" 3");
//        this.setEtats(new ArrayList<Etats>(mySet));
        return this;
    }



    public int get_etat_by_transition(Etats etat, String nom) {
        for(int i = 0; i < etat.getTransitions().size(); ++i) {
            ArrayList l = (ArrayList)(etat.getTransitions().get(i));
            if (l.get(1).equals(nom)) {
                return i;
            }
        }

        return -1;
    }



    public void afficher_etats(Automates aut){
        for (int i=0;i<aut.getEtats().size();i++){
            System.out.println("Etat "+i+" : "+aut.getEtats().get(i).getNom()+" "+aut.getEtats().get(i).getTransitions());
        }
    }



    public boolean accept(String s){
//        System.out.println(etatDepart.getNom());
        afficher_etats(this);
        this.determiniser();
        System.out.println("\n\n\n");
        afficher_etats(this);
//        this.determiniser();
//        boolean continuer = true;
        int i =0;
        Etats ec = this.etatDepart;
//        System.out.println(etatDepart.getNom());
        // vaudrait mieux utiliser une hashmap
        List<String> transit = new ArrayList<String>();
        while(!s.equals("") && ec != null){
            char c = s.charAt(0);

            System.out.println("juste pour le test");
            //on test la fct
            int test = get_etat_by_transition(ec,String.valueOf(c));
//            System.out.println("test = "+test);
            if (test == -1){
                ec = null;
            }
            else {
                List w = (ArrayList) ec.getTransitions().get(test);
//                System.out.println("w0 = "+w.get(0));
//                System.out.println("nom = "+this.getEtats().get(1).getNom());

//                System.out.println("transsss "+((List)this.getEtats().get(2).getTransitions().get(0)).get(0));
//                System.out.println("nooom "+this.getEtats().get(3).getNom());

//                System.out.println(etats.get(3).getNom());
//                System.out.println("get etat "+get_etat(this,"["+w.get(0)+"]"));
                // ICI ERREUR
                // PROBABLEMENT LES NOM GENERES
                System.out.println(this.etats.get(1).getNom()+"   ["+w.get(0)+"]");
                if (this.etats.get(0).getNom().equals("["+w.get(0)+"]")){
                    System.out.println("réussie");
                }
//                System.out.println("w="+w);
//                System.out.println("saluuut "+((List)this.getEtats().get(3).getTransitions().get(1)).get(0));
                System.out.println("avant get etat");
//                ec = this.etats.get(get_etat(this,"["+(String)w.get(0)+"]"));
                // ici ce ser plutot un get key : key est le mot lu
//                ec = this.etats.get(get_etat(this,"["+(String)w.get(0)+"]"));
                if(w.get(0).equals(this.etatDepart.getNom()))
                {
                    System.out.println("get etat "+get_etat(this,"["+(String)w.get(0)+"]")+" "+w.get(0));
                    ec = this.etats.get(get_etat(this,"["+(String)w.get(0)+"]"));}
                else{
                    ec = this.etats.get(get_etat(this,(String)w.get(0)));
                    System.out.println("get etat "+get_etat(this,(String)w.get(0))+" "+w.get(0));}
//                }
                s = s.substring(1);
            }


//                transit = ec.getTransitions();
//                for (int l =0;l<transit.size();l++){
//                    List<String> tr = (List)ec.getTransitions().get(l);
//                    System.out.println("avant sub "+" tr="+tr.get(1)+" c="+c+" s="+s+ " ec="+ec.getNom());
//                    char comp = tr.get(1).charAt(0);
////                    System.out.println(comp);
//                    if (comp == c){
//                        // ici ecrire une fonction qui renvoie un etat en prenant son nom
////                        System.out.println("sub");
//                        // get_etat prend le nom de l'etat pas de la transition!!!!!
//                        System.out.println(get_etat(this,tr.get(1)));
//                        if (get_etat(this,tr.get(1)) != -1){
//                            System.out.println(ec.getNom()+" avant");
//                            ec = this.etats.get(get_etat(this,tr.get(1)));
//                            System.out.println(ec.getNom()+" apres");}
//                        else {
//                            if (l == transit.size()-1){
//                                continuer = false;
//                            }
//                        }
////                        System.out.println("avant "+s);
//                        s = s.substring(1);
////                        System.out.println("apres "+s);
//                        break;
//                    }
//                    else {
//                        if (l == transit.size()-1){
//                            continuer = false;
//                        }
//                    }
//                }

//                i++;
        }
//        System.out.println(ec.getNom()+" "+continuer+" "+s+1);
//        for (int m=0;i<getEtatsArrivee().size();m++){
//            System.out.println(getEtatsArrivee().get(m).getNom()+" "+getEtatsArrivee().get(m).getTransitions());
//        }
        System.out.println(s);
//        System.out.println(ec.getNom());
        if(s.equals("") && this.etatsArrivee.contains(ec)){
            return true;
        }
        else{
            return false;
        }

    }





}
