package risk.utils.states;

public class NewState implements IState {

    @Override
    public IState next() {
        return new SelectionState();
    }
}
