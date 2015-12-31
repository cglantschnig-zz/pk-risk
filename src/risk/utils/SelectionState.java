package risk.utils;

public class SelectionState implements State {
    public State next() {
        return new GameState();
    }
}
