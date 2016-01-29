package risk.utils.states;

public class ReinforcementState implements IState{
    @Override
    public IState next() {
        return new FightState();
    }
}
