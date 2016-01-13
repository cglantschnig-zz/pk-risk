package risk.utils.states;

public class GameState implements IState {

    @Override
    public IState next() {
        return new ReinforcementState();
    }
}
