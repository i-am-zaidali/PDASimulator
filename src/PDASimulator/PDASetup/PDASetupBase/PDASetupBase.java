package PDASimulator.PDASetup.PDASetupBase;

import PDASimulator.DecidableTuringMachine.DecidableTuringMachine;
import javax.swing.*;

public abstract class PDASetupBase extends JPanel {
    public DecidableTuringMachine turingMachine;


    public abstract Boolean allowNext(Boolean showErrorDialog);

    abstract public void updateTuringMachineState();
}
