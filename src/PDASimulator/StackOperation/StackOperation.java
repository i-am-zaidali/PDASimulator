package PDASimulator.StackOperation;

public enum StackOperation {
    PUSH,
    POP,
    NOP;

    public static StackOperation fromString(String s) throws IllegalArgumentException {
        if (s.equalsIgnoreCase("push")) {
            return PUSH;
        } else if (s.equalsIgnoreCase("pop")) {
            return POP;
        } else if (s.equalsIgnoreCase("skip") || s.equalsIgnoreCase("nop")) {
            return NOP;
        } else {
            throw new IllegalArgumentException("Invalid stack operation: " + s);
        }
    }
}
