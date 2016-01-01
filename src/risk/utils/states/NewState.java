package risk.utils.states;

public class NewState implements State {

    @Override
    public State next() {
        return new SelectionState();
    }
}
