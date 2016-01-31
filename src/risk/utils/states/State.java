package risk.utils.states;

import risk.utils.listeners.StateChangeListener;

import java.util.ArrayList;

public class State {
    private IState state = null;
    private ArrayList<StateChangeListener> listeners = new ArrayList<>();
    public State(IState initialState) {
        this.state = initialState;
    }

    public void addStateChangeListener(StateChangeListener listener) {
        this.listeners.add(listener);
    }

    public IState getState() {
        return this.state;
    }

    public void next() {
        if (state != null) {
            this.state = state.next();

            for (StateChangeListener listener : this.listeners) {
                listener.stateChanged(this.state);
            }
        }
    }

    public void finish() {
        this.state = new FinishGameState();

        for (StateChangeListener listener : this.listeners) {
            listener.stateChanged(this.state);
        }
    }

    @Override
    public String toString() {
        return this.state.toString();
    }
}
