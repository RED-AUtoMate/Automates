public class Transitions {
    private Etats depart,destination;
    private String caractere;

    public Transitions(Etats depart, Etats destination, String caractere) {
        this.depart = depart;
        this.destination = destination;
        this.caractere = caractere;
    }

    public Etats getDepart() {
        return depart;
    }

    public void setDepart(Etats depart) {
        this.depart = depart;
    }

    public Etats getDestination() {
        return destination;
    }

    public void setDestination(Etats destination) {
        this.destination = destination;
    }

    public String getCaractere() {
        return caractere;
    }

    public void setCaractere(String caractere) {
        this.caractere = caractere;
    }
}
