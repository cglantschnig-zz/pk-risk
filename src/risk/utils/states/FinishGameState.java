package risk.utils.states;

public class FinishGameState implements IState {

    @Override
    public IState next() {
        return new GameState();
    }
}
