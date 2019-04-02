/*
    This program creates a Finite Automaton and can be used to check if it accepts a string or not.
 */

import java.util.*;

public class FiniteAutomaton extends AbstractAutomaton{

    private Map<Integer,HashMap<Character, List<Integer>>> states;
    private int startState;
    private List<Integer> endStates;

    public FiniteAutomaton() {
        this.states = new HashMap<>();
        this.endStates = new ArrayList<>();
    }

    /**
     * Adds a state to automata
     * @param stateId - State number
     * @param isAcceptingState - If the state is accepting.
     */
    public void addState(Integer stateId, boolean isAcceptingState) {
        states.put(stateId,new HashMap<>());
        if (isAcceptingState){
            endStates.add(stateId);
        }

    }

    /**
     * Sets starting state
     * @param id - State id to set as a starting state.
     */
    public void setStartState(Integer id) {
        startState = id;
    }

    /**
     * Adds transition from one state to antoher.
     * @param fromState - Beginning state of the transition
     * @param label - Transition character or null in case of epsilon
     * @param toState - End state of the transition
     */
    public void addTransition(Integer fromState, Character label, Integer toState){

        if (states.containsKey(fromState)){

            if (states.get(fromState).containsKey(label)){
                states.get(fromState).get(label).add(toState);
            }
            else {
                List<Integer> toStates = new ArrayList<>();
                toStates.add(toState);
                states.get(fromState).put(label,toStates);
            }
        }
        else {
            System.out.println("State doesn't exist!");
        }
    }

    @Override
    public Set<Integer> getStates() {
        return states.keySet();
    }

    @Override
    public Integer getStartState() {
        return startState;
    }

    /**
     * Returns lisst of state ID's with and epsilon transition from given state
     * @param state - State to check
     * @return - List of states with epsilon transition from given state
     */
    public List<Integer> getEpsilonsOfState(int state){
        if (states.get(state).containsKey(null)){
            return states.get(state).get(null);
        }
        return new ArrayList<>();
    }

    /**
     * Function for getting a list of state ID's, with an epsilon transition from the given state
     * Recursively checks the recieved states for their epsilon-transitions aswell
     * @param state - State to check
     * @param checkedStates - Already checked states
     * @return - List of states with epsilon transition from given state
     */
    public List<Integer> allEpsilonsFromState(int state, List<Integer> checkedStates){

        List<Integer> checkedStatesCopy = new ArrayList<>(checkedStates);

        List<Integer> stateEpsilons = getEpsilonsOfState(state);

        checkedStatesCopy.addAll(stateEpsilons);

        for (int o : stateEpsilons){
            if (!checkedStates.contains(o)){
                checkedStatesCopy.addAll(allEpsilonsFromState(o,checkedStatesCopy));
            }

        }

        List<Integer> cleanedList = new ArrayList<>();

        for(int i : checkedStatesCopy){
            if (!cleanedList.contains(i)){
                cleanedList.add(i);
            }
        }

        return cleanedList;

    }

    @Override
    public boolean isAcceptingState(Integer state) {
        return endStates.contains(state);
    }

    @Override
    public Set<Character> getDefinedInputs(Integer state) {
        return states.get(state).keySet();
    }

    @Override
    public Set<Integer> getDestinations(Integer state, Character input) {
        return new HashSet<>(states.get(state).get(input));
    }

    /**
     * Returns true, if the automata accepts given input
     * @param input - String to check
     * @return - True, if automata accepts the string
     */
    public boolean accepts(String input) {

        // Creates two lists of ending states for the string
        List<Integer> states = new ArrayList<>();
        List<Integer> newStates = new ArrayList<>();

        // Adds the starting string
        states.add(startState);

        // Adds every other possible start
        states = allEpsilonsFromState(startState, states);

        // Iterates through the string
        char[] chars = input.toCharArray();

        for (Character c : chars){

            // Adds new possible states for every current possible state
            for (int state : states){

                if (this.states.get(state).containsKey(c)){
                    newStates.addAll(this.states.get(state).get(c));
                }

                for (int newState : newStates){
                    newStates = allEpsilonsFromState(newState,newStates);
                }

            }

            // Adds unique new states to states
            states = new ArrayList<>();
            for (int s : newStates){
                if (!states.contains(s)){
                    states.add(s);
                }
            }
            newStates = new ArrayList<>();
        }

        // If any of the end states is a final state, then the automata accepts the string
        for (int state : endStates){
            if (states.contains(state)){
                return true;
            }
        }

        return false;
    }

    public void setEndStates(List<Integer> endStates) {
        this.endStates = endStates;
    }

    public int getMaxState(){
        return Collections.max(states.keySet());
    }

    public void changeEpsilons(){
        for (Map.Entry<Integer,HashMap<Character, List<Integer>>> olek : states.entrySet()){
            if (olek.getValue().containsKey('ε')){
                List<Integer> epsilonList = olek.getValue().remove('ε');
                olek.getValue().put(null, epsilonList);
            }
        }
    }

    @Override
    public String toString() {
        return states.toString();
    }
}
