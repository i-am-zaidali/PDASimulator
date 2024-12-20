/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author zimp
 */
public class TransitionManagerPanel extends PDASetupBase {

    public static final String BLANK_CHARACTER = "ε";

    public DecidableTuringMachine turingMachine;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel TablePanel;
    private javax.swing.JPanel TransitionPanel;
    private javax.swing.JButton addTransitionButton;
    private javax.swing.JComboBox<String> consumeCharacterSelect;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JComboBox<String> goToStateSelect;
    private javax.swing.JComboBox<String> initialStateSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> pushPopCharacterSelect;
    private javax.swing.JLabel pushPopLabel;
    private javax.swing.JButton removeTransitionButton;
    private javax.swing.JComboBox<String> stackOpSelect;
    private javax.swing.JTable transitionTable;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form TransitionManagerPanel
     */
    public TransitionManagerPanel(DecidableTuringMachine tm) {
        initComponents();
        this.turingMachine = tm;
        consumeCharacterSelect.removeAllItems();
        consumeCharacterSelect.addItem(BLANK_CHARACTER);
        updateTuringMachineState();
    }

    public void updateTuringMachineState() {
        // Clear all previous items
        consumeCharacterSelect.removeAllItems();
        pushPopCharacterSelect.removeAllItems();
        initialStateSelect.removeAllItems();
        goToStateSelect.removeAllItems();

        consumeCharacterSelect.addItem(BLANK_CHARACTER);
        this.turingMachine.inputAlphabet.forEach(c -> consumeCharacterSelect.addItem(c));
        this.turingMachine.stackAlphabet.forEach(c -> pushPopCharacterSelect.addItem(c));

        this.turingMachine.states.forEach(state -> {
            initialStateSelect.addItem(state);
            goToStateSelect.addItem(state);
        });
        var model = ((DefaultTableModel) transitionTable.getModel());
        model.setRowCount(0);
        this.turingMachine.transitions.forEach(
                (state, transition) -> transition.forEach(
                        (gotoState, tiArray) -> tiArray.forEach(
                                ti -> {
                                    model.addRow(new Object[]{
                                            state,
                                            gotoState,
                                            ti.consumeCharacter.isBlank()
                                                    ? BLANK_CHARACTER
                                                    : ti.consumeCharacter,
                                            ti.stackOp.name(),
                                            ti.stackOpArg != null
                                                    ? ti.stackOpArg
                                                    : BLANK_CHARACTER
                                    });
                                }
                        )
                )
        );
    }

//    public static void main(String[] args) {
//        var fauxStates = new ArrayList<Pair<String, Boolean>>();
//        for (int i = 0; i < 5; i++) {
//            fauxStates.add(new Pair<>("q" + i, i == 4));
//        }
//        var alphabet = new HashSet<>(List.of("a", "b", "c"));
//        TransitionManagerPanel tmp = new TransitionManagerPanel(fauxStates, alphabet, null);
//        javax.swing.JFrame frame = new javax.swing.JFrame();
//        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
//        frame.add(tmp);
//        frame.pack();
//        frame.setVisible(true);
//    }

    private void pushPopCharacterSelectUpdate(boolean visible) {
        pushPopLabel.setVisible(visible);
        pushPopCharacterSelect.setVisible(visible);
        if (visible) {
            pushPopLabel.setText("P" + ((String) Objects.requireNonNull(stackOpSelect.getSelectedItem())).substring(1).toLowerCase() + " character: ");
            pushPopCharacterSelect.removeAllItems();
            this.turingMachine.stackAlphabet.forEach(c -> pushPopCharacterSelect.addItem(c));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        TransitionPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        initialStateSelect = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        goToStateSelect = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        consumeCharacterSelect = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        stackOpSelect = new javax.swing.JComboBox<>();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(5, 32767));
        addTransitionButton = new javax.swing.JButton();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        pushPopLabel = new javax.swing.JLabel();
        pushPopCharacterSelect = new javax.swing.JComboBox<>();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        removeTransitionButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(5, 32767));
        TablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transitionTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        TransitionPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("C059", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("From state: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(jLabel1, gridBagConstraints);

        initialStateSelect.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        initialStateSelect.setMaximumSize(new java.awt.Dimension(200, 24));
        initialStateSelect.setMinimumSize(new java.awt.Dimension(120, 24));
        initialStateSelect.setPreferredSize(new java.awt.Dimension(150, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(initialStateSelect, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("C059", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Go to state");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(jLabel2, gridBagConstraints);

        goToStateSelect.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        goToStateSelect.setMaximumSize(new java.awt.Dimension(200, 24));
        goToStateSelect.setMinimumSize(new java.awt.Dimension(120, 24));
        goToStateSelect.setPreferredSize(new java.awt.Dimension(150, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(goToStateSelect, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("C059", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Consume character: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(jLabel3, gridBagConstraints);

        consumeCharacterSelect.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        consumeCharacterSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"ε (empty character)"}));
        consumeCharacterSelect.setMaximumSize(new java.awt.Dimension(200, 24));
        consumeCharacterSelect.setMinimumSize(new java.awt.Dimension(120, 24));
        consumeCharacterSelect.setPreferredSize(new java.awt.Dimension(150, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(consumeCharacterSelect, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("C059", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Stack operation:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(jLabel4, gridBagConstraints);

        stackOpSelect.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        stackOpSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"PUSH", "POP", "SKIP"}));
        stackOpSelect.setMaximumSize(new java.awt.Dimension(200, 24));
        stackOpSelect.setMinimumSize(new java.awt.Dimension(120, 24));
        stackOpSelect.setPreferredSize(new java.awt.Dimension(150, 24));
        stackOpSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stackOpSelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(stackOpSelect, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        TransitionPanel.add(filler5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        TransitionPanel.add(filler6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        TransitionPanel.add(filler7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        TransitionPanel.add(filler8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(filler9, gridBagConstraints);

        addTransitionButton.setText("Add transition");
        addTransitionButton.setPreferredSize(new java.awt.Dimension(150, 30));
        addTransitionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTransitionButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        TransitionPanel.add(addTransitionButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        TransitionPanel.add(filler10, gridBagConstraints);

        pushPopLabel.setFont(new java.awt.Font("C059", 1, 18)); // NOI18N
        pushPopLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pushPopLabel.setText("Push character: ");
        pushPopLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(pushPopLabel, gridBagConstraints);

        pushPopCharacterSelect.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        pushPopCharacterSelect.setPreferredSize(new java.awt.Dimension(150, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(pushPopCharacterSelect, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        TransitionPanel.add(filler1, gridBagConstraints);

        removeTransitionButton.setText("Remove transition");
        removeTransitionButton.setEnabled(false);
        removeTransitionButton.setPreferredSize(new java.awt.Dimension(150, 30));
        removeTransitionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTransitionButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 15);
        TransitionPanel.add(removeTransitionButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        TransitionPanel.add(filler2, gridBagConstraints);

        add(TransitionPanel, java.awt.BorderLayout.LINE_END);

        TablePanel.setPreferredSize(new java.awt.Dimension(500, 400));
        TablePanel.setLayout(new java.awt.BorderLayout());

        transitionTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "<html><center>Initial<br/>state</center></html>", "<html><center>Transition<br>state</center></html>", "<html><center>Consume<br>character</center></html>", "<html><center>Stack<br>Operation</center></html>", "<html><center>Stack<br>Character</center></html>"
                }
        ) {
            final Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            final boolean[] canEdit = new boolean[]{
                    false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        transitionTable.setFocusable(false);
        transitionTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        transitionTable.getTableHeader().setResizingAllowed(false);
        transitionTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(transitionTable);
        transitionTable.getSelectionModel().addListSelectionListener(this::transitionTableSelectionHandler);
        if (transitionTable.getColumnModel().getColumnCount() > 0) {
            transitionTable.getColumnModel().getColumn(0).setResizable(false);
            transitionTable.getColumnModel().getColumn(0).setPreferredWidth(11);
            transitionTable.getColumnModel().getColumn(1).setResizable(false);
            transitionTable.getColumnModel().getColumn(1).setPreferredWidth(14);
            transitionTable.getColumnModel().getColumn(2).setResizable(false);
            transitionTable.getColumnModel().getColumn(2).setPreferredWidth(11);
            transitionTable.getColumnModel().getColumn(3).setResizable(false);
            transitionTable.getColumnModel().getColumn(3).setPreferredWidth(13);
            transitionTable.getColumnModel().getColumn(4).setResizable(false);
            transitionTable.getColumnModel().getColumn(4).setPreferredWidth(11);
        }

        TablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(TablePanel, java.awt.BorderLayout.CENTER);

        jLabel5.setFont(new java.awt.Font("Monaspace Argon", 1, 20)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Transition Manager");
        add(jLabel5, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void transitionTableSelectionHandler(ListSelectionEvent listSelectionEvent) {
        removeTransitionButton.setEnabled(transitionTable.getSelectedRow() != -1);
    }

    private void stackOpSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stackOpSelectActionPerformed
        pushPopCharacterSelectUpdate(!((String) Objects.requireNonNull(stackOpSelect.getSelectedItem())).equalsIgnoreCase("skip"));
    }//GEN-LAST:event_stackOpSelectActionPerformed

    private void addTransitionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTransitionButtonActionPerformed
        String initialState = (String) Objects.requireNonNull(initialStateSelect.getSelectedItem());
        String goToState = (String) Objects.requireNonNull(goToStateSelect.getSelectedItem());
        String consumeCharacter = (String) Objects.requireNonNull(consumeCharacterSelect.getSelectedItem());
        var stackOp = StackOperation.fromString((String) Objects.requireNonNull(stackOpSelect.getSelectedItem()));
        String stackOpArg = stackOp != StackOperation.NOP ?
                ((String) Objects.requireNonNull(pushPopCharacterSelect.getSelectedItem()))
                : null;
        TransitionInfo ti = new TransitionInfo(consumeCharacter, stackOp, stackOpArg);
        var subMap = this.turingMachine.transitions.computeIfAbsent(initialState, _ -> new HashMap<>());
        var sublist = subMap.computeIfAbsent(goToState, _ -> new ArrayList<>());
        if (sublist.stream().anyMatch(ti::equals)) {
            JOptionPane.showMessageDialog(this, "Transition already exists", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel model = (DefaultTableModel) transitionTable.getModel();
        model.addRow(new Object[]{initialState, goToState, consumeCharacter, stackOp.name(), stackOpArg != null ? stackOpArg : BLANK_CHARACTER});
        sublist.add(ti);
    }//GEN-LAST:event_addTransitionButtonActionPerformed

    private void removeTransitionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeTransitionButtonActionPerformed
        var row = transitionTable.getSelectedRow();
        var model = (DefaultTableModel) transitionTable.getModel();
        var initialState = (String) model.getValueAt(row, 0);
        var goToState = (String) model.getValueAt(row, 1);
        var consumeCharacter = (String) model.getValueAt(row, 2);
        var stackOp = StackOperation.fromString((String) model.getValueAt(row, 3));
        var stackOpArg = (String) model.getValueAt(row, 4);
        var ti = new TransitionInfo(consumeCharacter, stackOp, stackOpArg.equals(BLANK_CHARACTER) ? null : stackOpArg);
        if (ti.equals(DecidableTuringMachine.defaultTransition)) {
            JOptionPane.showMessageDialog(this, "Cannot remove default transition", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var subMap = this.turingMachine.transitions.get(initialState);
        var sublist = subMap.get(goToState);
        sublist.removeIf(ti::equals);
        model.removeRow(row);
    }//GEN-LAST:event_removeTransitionButtonActionPerformed

    @Override
    public Boolean allowNext(Boolean showErrorDialog) {
        return true;
    }

}
