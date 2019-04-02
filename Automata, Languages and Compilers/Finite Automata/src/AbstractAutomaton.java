import java.util.Set;

public abstract class AbstractAutomaton {

    public abstract void addState(Integer state, boolean isAcceptingState);

    public int addState(boolean isAcceptingState) {
        Set<Integer> states = getStates();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!states.contains(i)) {
                addState(i, isAcceptingState);
                return i;
            }
        }
        throw new RuntimeException("Too many states");
    }

    public abstract void setStartState(Integer state);

    public abstract void addTransition(Integer fromState, Character label, Integer toState);

    public abstract Set<Integer> getStates();

    public abstract Integer getStartState();

    public abstract boolean isAcceptingState(Integer state);

    public abstract Set<Character> getDefinedInputs(Integer state);

    public abstract Set<Integer> getDestinations(Integer state, Character input);

    public abstract boolean accepts(String input);

}
