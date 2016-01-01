package risk.utils.listeners;

import risk.utils.states.IState;

public interface StateChangeListener {
    void stateChanged(IState newState);
}
