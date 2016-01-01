package risk.utils.states;

public class SelectionState implements State {

    @Override
    public State next() {
        return new GameState();
    }
}
