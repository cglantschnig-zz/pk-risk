package risk.utils.states;

public class FightState implements IState {
    @Override
    public IState next() {
        return new ReinforcementState();
    }
}
