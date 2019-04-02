public class Main {
    public static void main(String[] args) {
        /*
        Short example of FiniteAutomation class
         */

        FiniteAutomaton fa = new FiniteAutomaton();

        fa.addState(0, false);
        fa.addState(1, true);
        fa.addState(2, false);

        fa.addTransition(0, 'b', 0);
        fa.addTransition(0, 'c', 2);
        fa.addTransition(2, 'a', 1);
        fa.addTransition(1, 'd', 0);
        fa.addTransition(0, null, 1);

        fa.setStartState(0);

        boolean result1 = fa.accepts("cadbbbca");
        boolean result2 = fa.accepts("abc");

        System.out.println("Automata accepts cadbbbca?: " + result1);
        System.out.println("Automata accepts abc?: " + result2);
    }
}
