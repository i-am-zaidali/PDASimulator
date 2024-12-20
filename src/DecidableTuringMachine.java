import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@JsonInclude()
public class DecidableTuringMachine {
    static final String initialStackSymbol = "$";
    static final TransitionInfo defaultTransition = new TransitionInfo(
            TransitionManagerPanel.BLANK_CHARACTER,
            StackOperation.PUSH,
            initialStackSymbol
    );
    public ArrayList<String> states;
    public ArrayList<String> finalStates;
    public HashSet<String> inputAlphabet;
    public HashSet<String> stackAlphabet;
    public HashMap<String, HashMap<String, ArrayList<TransitionInfo>>> transitions;
    public String startState;

    public String languageSetBuilderForm;
    public String languageDescription;

    public DecidableTuringMachine() {
        states = new ArrayList<>();
        finalStates = new ArrayList<>();
        inputAlphabet = new HashSet<>();
        stackAlphabet = new HashSet<>();
        transitions = new HashMap<>();
        languageDescription = "";
        languageSetBuilderForm = "";
        setupDefaults();
    }

    @JsonCreator
    public static DecidableTuringMachine fromJSON(
            @JsonProperty("states") ArrayList<String> states,
            @JsonProperty("finalStates") ArrayList<String> finalStates,
            @JsonProperty("inputAlphabet") HashSet<String> inputAlphabet,
            @JsonProperty("stackAlphabet") HashSet<String> stackAlphabet,
            @JsonProperty("transitions") HashMap<String, HashMap<String, ArrayList<TransitionInfo>>> transitions,
            @JsonProperty("startState") String startState,
            @JsonProperty("languageSetBuilderForm") String languageSetBuilderForm,
            @JsonProperty("languageDescription") String languageDescription
    ) {
        var tm = new DecidableTuringMachine();
        tm.states = states;
        tm.finalStates = finalStates;
        tm.inputAlphabet = inputAlphabet;
        tm.stackAlphabet = stackAlphabet;
        tm.startState = startState;
        tm.transitions = transitions;
        tm.languageSetBuilderForm = languageSetBuilderForm;
        tm.languageDescription = languageDescription;
        return tm;
    }

    private void setupDefaults() {
        states.addAll(List.of("start", "initializeStack"));
        startState = "start";
        stackAlphabet.add("$");
        var subMap = new HashMap<String, ArrayList<TransitionInfo>>();
        var subList = new ArrayList<TransitionInfo>();
        transitions.put("start", subMap);
        subMap.put("initializeStack", subList);
        subList.add(defaultTransition);
    }

    public String toString() {
        var sb = new StringBuilder();
        sb.append("Q (States): ");
        listToSetString(sb, states);

        sb.append("Σ (Input Alphabet): ");
        listToSetString(sb, inputAlphabet);

        sb.append("Γ (Stack Alphabet): ");
        listToSetString(sb, stackAlphabet);

        sb.append("q0 (Start State): ").append(startState).append("\n");

        sb.append("F (Final States): ");
        listToSetString(sb, finalStates);

        sb.append("δ (Transitions):\n");
        for (var state : transitions.keySet()) {
            for (var gotoState : transitions.get(state).keySet()) {
                for (var transition : transitions.get(state).get(gotoState)) {
                    sb.append("\t").append(state).append(" : ").append(transition).append(" : ").append(gotoState).append("\n");
                }
            }
        }

        return sb.toString();
    }

    private void listToSetString(StringBuilder sb, Iterable<String> array) {
        sb.append("{ ");
        for (var item : array) {
            sb.append(item).append(", ");
        }
        if (sb.charAt(sb.length() - 2) == ',') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(" }\n");
    }

    public Boolean isFinalState(String stateName) {
        return this.finalStates.contains(stateName);
    }

    public void updateFrom(DecidableTuringMachine other) {
        this.states.clear();
        this.states.addAll(other.states);
        this.finalStates.clear();
        this.finalStates.addAll(other.finalStates);
        this.inputAlphabet.clear();
        this.inputAlphabet.addAll(other.inputAlphabet);
        this.stackAlphabet.clear();
        this.stackAlphabet.addAll(other.stackAlphabet);
        this.transitions.clear();
        this.transitions.putAll(other.transitions);
        this.startState = other.startState;
        this.languageDescription = other.languageDescription;
        this.languageSetBuilderForm = other.languageSetBuilderForm;
    }

}
