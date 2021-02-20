import java.util.List;

public class Transitions {
    //private AbstractEtats depart;
    private List transitions; // [0,"a"]

    public Transitions( List transitions) {
        this.transitions = transitions;
    }

    public List getTransitions() {
        return transitions;
    }

    public void setTransitions(List transitions) {
        this.transitions = transitions;
    }


}
