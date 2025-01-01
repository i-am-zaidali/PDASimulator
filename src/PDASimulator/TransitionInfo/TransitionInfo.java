package PDASimulator.TransitionInfo;

import PDASimulator.StackOperation.StackOperation;
import PDASimulator.PDASetup.TransitionManagerPanel.TransitionManagerPanel;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransitionInfo implements Comparable<TransitionInfo> {
    public String consumeCharacter;
    public StackOperation stackOp;
    public String stackOpArg;

    public TransitionInfo(String consumeCharacter, StackOperation stackOperation, String stackOpArg) {
        this.consumeCharacter = consumeCharacter;
        this.stackOp = stackOperation;
        this.stackOpArg = stackOpArg;
    }

    @JsonCreator
    public static TransitionInfo fromJSON_YAML(
            @JsonProperty("consumeCharacter") String consumeCharacter,
            @JsonProperty("stackOp") String stackOp,
            @JsonProperty("stackOpArg") String stackOpArg) {
        return new TransitionInfo(
                consumeCharacter,
                StackOperation.fromString(stackOp),
                stackOpArg
        );
    }

    @Override
    public int compareTo(TransitionInfo o) {
        return (consumeCharacter + stackOp + stackOpArg).toLowerCase().compareTo((o.consumeCharacter + o.stackOp + o.stackOpArg).toLowerCase());
    }

    public boolean equals(TransitionInfo o) {
        return this.consumeCharacter.equalsIgnoreCase(o.consumeCharacter) && this.stackOp == o.stackOp && this.stackOpArg.equalsIgnoreCase(o.stackOpArg);
    }

    public String toString() {
        return consumeCharacter + ", " + switch (stackOp) {
            case NOP ->
                    TransitionManagerPanel.BLANK_CHARACTER + " -> " + TransitionManagerPanel.BLANK_CHARACTER;
            case POP -> stackOpArg + " -> " + TransitionManagerPanel.BLANK_CHARACTER;
            case PUSH -> TransitionManagerPanel.BLANK_CHARACTER + " -> " + stackOpArg;
        };
    }
}