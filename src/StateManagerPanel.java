/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * @author zimp
 */
public class StateManagerPanel extends PDASetupBase {
    public DecidableTuringMachine turingMachine;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addStateButton;
    private javax.swing.JButton clearAllStatesButton;
    private javax.swing.JButton deleteStateButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel stateAmountTextField;
    private javax.swing.JTable stateTable;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form StateManagerFrame
     */
    public StateManagerPanel(DecidableTuringMachine turingMachine) {
        this.turingMachine = turingMachine;
        initComponents();
        this.stateTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        updateTuringMachineState();
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

        jPanel1 = new javax.swing.JPanel();
        stateAmountTextField = new javax.swing.JLabel();
        clearAllStatesButton = new javax.swing.JButton();
        deleteStateButton = new javax.swing.JButton();
        addStateButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        stateTable = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(710, 320));
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        stateAmountTextField.setText("Amount of states: 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 20);
        jPanel1.add(stateAmountTextField, gridBagConstraints);

        clearAllStatesButton.setText("Clear All States");
        clearAllStatesButton.setEnabled(false);
        clearAllStatesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllStatesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 20);
        jPanel1.add(clearAllStatesButton, gridBagConstraints);

        deleteStateButton.setText("Delete State");
        deleteStateButton.setEnabled(false);
        deleteStateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteStateButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 20);
        jPanel1.add(deleteStateButton, gridBagConstraints);

        addStateButton.setText("Add State");
        addStateButton.setPreferredSize(new java.awt.Dimension(130, 24));
        addStateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStateButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 20);
        jPanel1.add(addStateButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        add(jPanel1, gridBagConstraints);

        stateTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "State name", "Final?"
                }
        ) {
            final Class[] types = new Class[]{
                    java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        stateTable.getModel().addTableModelListener(this::stateTableUpdate);
        stateTable.getSelectionModel().addListSelectionListener(this::stateTableSelectionHandler);
        jScrollPane1.setViewportView(stateTable);
        if (stateTable.getColumnModel().getColumnCount() > 0) {
            stateTable.getColumnModel().getColumn(0).setResizable(false);
            stateTable.getColumnModel().getColumn(1).setResizable(false);
            stateTable.getColumnModel().getColumn(1).setPreferredWidth(10);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        add(jPanel2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void deleteStateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteStateButtonActionPerformed
        var row = stateTable.getSelectedRow();
        var model = (javax.swing.table.DefaultTableModel) stateTable.getModel();
        var stateName = (String) model.getValueAt(row, 0);
        if (stateName.equals("start") || stateName.equals("initializeStack")) {
            JOptionPane.showMessageDialog(this, "Cannot delete the start or initializeStack state", "Invalid state", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model.removeRow(row);
        this.turingMachine.states.remove(row);
        this.stateAmountTextField.setText("Amount of states: " + this.turingMachine.states.size());

        deleteStateButton.setEnabled(false);
    }//GEN-LAST:event_deleteStateButtonActionPerformed

    private void clearAllStatesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllStatesButtonActionPerformed
        var model = (javax.swing.table.DefaultTableModel) stateTable.getModel();
        model.setRowCount(2);
        this.turingMachine.states.subList(2, this.turingMachine.states.size()).clear();
        this.turingMachine.finalStates.clear();
        this.stateAmountTextField.setText("Amount of states: 2");
    }//GEN-LAST:event_clearAllStatesButtonActionPerformed

    private void addStateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStateButtonActionPerformed
        ((DefaultTableModel) this.stateTable.getModel()).addRow(new Object[]{"q" + this.turingMachine.states.size(), false});
        this.turingMachine.states.add("q" + this.turingMachine.states.size());
        this.stateAmountTextField.setText("Amount of states: " + this.turingMachine.states.size());
    }//GEN-LAST:event_addStateButtonActionPerformed

    private void stateTableUpdate(TableModelEvent e) {
        if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.DELETE) {
            clearAllStatesButton.setEnabled(stateTable.getRowCount() != 2);
            return;
        } else if (e.getType() != TableModelEvent.UPDATE) {
            return;
        }
        var row = e.getFirstRow();
        var stateName = (String) stateTable.getValueAt(row, 0);
        var isFinal = (Boolean) stateTable.getValueAt(row, 1);
        if (row <= 1 && (!List.of("start", "initializeStack").contains(stateName))) {
            JOptionPane.showMessageDialog(this, "Cannot change the final state of the start or initializeStack state", "Invalid state", JOptionPane.ERROR_MESSAGE);
            stateTable.setValueAt(row == 0 ? "start" : "initializeStack", row, 0);
            return;
        }
        this.turingMachine.states.set(row, stateName);
        if (isFinal && !this.turingMachine.finalStates.contains(stateName)) {
            this.turingMachine.finalStates.add(stateName);
        } else {
            this.turingMachine.finalStates.remove(stateName);
        }
    }

    private void stateTableSelectionHandler(ListSelectionEvent event) {
        deleteStateButton.setEnabled(stateTable.getSelectedRow() != -1);
    }

    @Override
    public Boolean allowNext(Boolean showErrorDialog) {
        return true;
    }

    @Override
    public void updateTuringMachineState() {
        var model = (javax.swing.table.DefaultTableModel) stateTable.getModel();
        model.setRowCount(0);
        this.turingMachine.states.forEach(
                state -> model.addRow(new Object[]{state, this.turingMachine.finalStates.contains(state)})
        );
    }
}