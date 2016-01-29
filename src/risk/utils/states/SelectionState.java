package risk.utils.states;

public class SelectionState implements IState {

    @Override
    public IState next() {
        return new GameState();
    }
}
