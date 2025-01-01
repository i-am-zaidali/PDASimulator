package PDASimulator.InputProcessingState;

public enum InputProcessingState {
    EMPTY {
        public java.awt.Color getColor() {
            return new java.awt.Color(133, 133, 133);
        }
    },

    UNPROCESSED {
        public java.awt.Color getColor() {
            return new java.awt.Color(121, 86, 161);
        }
    },

    VALID {
        public java.awt.Color getColor() {
            return new java.awt.Color(0, 255, 0);
        }
    },

    INVALID {
        public java.awt.Color getColor() {
            return new java.awt.Color(255, 0, 0);
        }
    };

    public abstract java.awt.Color getColor();

    public String colorToCssString() {
        var c = getColor();
        return "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
    }
}
