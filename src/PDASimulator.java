/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

/**
 * @author zimp
 */
public class PDASimulator extends javax.swing.JDialog {

    int currentStep = 0; // Current step of the simulation
    boolean stopSimulation = false; // Flag to stop the simulation
    Timer simulationTask = null;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CheckStringButton;
    private javax.swing.JPanel LabelPanel;
    private javax.swing.JSlider characterSlider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable operationsTable;
    private javax.swing.JButton resetButton;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JTable stackTable;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JButton stopButton;
    private javax.swing.JTextField userStringInput;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form PDASimulator
     */
    public PDASimulator(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        userStringInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateString();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateString();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateString();
            }

            private void updateString() {
                String text = userStringInput.getText();
                if (text.isEmpty()) {
                    statusLabel.setText("The current string is **EMPTY**");
                    speedSlider.setEnabled(false);
                    CheckStringButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    resetButton.setEnabled(false);
                } else {
                    statusLabel.setText("The current string is **UNPROCESSED**");
                    var lastChar = text.substring(text.length() - 1);
                    var allChars = new HashSet<>(text.chars().mapToObj(c -> (char) c).toList());
                    var allowedChars = List.of('a', 'b', 'c');
                    if (!allowedChars.contains(lastChar.toCharArray()[0])) {
                        JOptionPane.showMessageDialog(PDASimulator.this, "Only characters a, b, and c are allowed", "Invalid Character; \"" + lastChar + "\"", JOptionPane.ERROR_MESSAGE);
                        SwingUtilities.invokeLater(() -> userStringInput.setText(text.substring(0, text.length() - 1)));
                        return;
                    }
                    if (allChars.containsAll(allowedChars) && !text.matches("a+b+c+")) {
                        JOptionPane.showMessageDialog(PDASimulator.this, "The character 'b' must be followed by 'c'", "Invalid Character; \"" + lastChar + "\"", JOptionPane.ERROR_MESSAGE);
                        SwingUtilities.invokeLater(() -> userStringInput.setText(""));
                        return;
                    }
                    speedSlider.setEnabled(true);
                    CheckStringButton.setEnabled(true);
                    resetButton.setEnabled(true);
                    stopButton.setEnabled(false);
                }
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException |
                 InstantiationException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PDASimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PDASimulator dialog = new PDASimulator(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
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
        jPanel6 = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        resetButton = new javax.swing.JButton();
        CheckStringButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        stackTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        operationsTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        speedSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        LabelPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        userStringInput = new javax.swing.JTextField();
        characterSlider = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        statusLabel.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText("The current string is **EMPTY**");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(statusLabel)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        java.awt.GridBagLayout jPanel8Layout = new java.awt.GridBagLayout();
        jPanel8Layout.columnWidths = new int[]{0, 50, 0, 50, 0, 50, 0, 50, 0};
        jPanel8Layout.rowHeights = new int[]{0};
        jPanel8.setLayout(jPanel8Layout);

        resetButton.setText("Reset Simulation");
        resetButton.setEnabled(false);
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        jPanel8.add(resetButton, gridBagConstraints);

        CheckStringButton.setFont(new java.awt.Font("Noto Sans", 1, 16)); // NOI18N
        CheckStringButton.setText("Check String");
        CheckStringButton.setEnabled(false);
        CheckStringButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckStringButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        jPanel8.add(CheckStringButton, gridBagConstraints);

        stopButton.setText("Stop Simulation");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel8.add(stopButton, gridBagConstraints);

        jPanel1.add(jPanel8, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        stackTable.setFont(new java.awt.Font("DejaVu Sans", 1, 20)); // NOI18N
        stackTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null},
                        {null},
                        {null},
                        {null}
                },
                new String[]{
                        "Stack"
                }
        ) {
            final Class[] types = new Class[]{
                    java.lang.String.class
            };
            final boolean[] canEdit = new boolean[]{
                    false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        stackTable.setFocusable(false);
        stackTable.setRowSelectionAllowed(false);
        stackTable.getTableHeader().setReorderingAllowed(false);
        stackTable.setUpdateSelectionOnSort(false);
        stackTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(stackTable);
        if (stackTable.getColumnModel().getColumnCount() > 0) {
            stackTable.getColumnModel().getColumn(0).setResizable(false);
        }

        operationsTable.setFont(new java.awt.Font("DejaVu Sans", 1, 20)); // NOI18N
        operationsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String[]{
                        "Input Char", "Stack Operation"
                }
        ) {
            final Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class
            };
            final boolean[] canEdit = new boolean[]{
                    false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        operationsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        operationsTable.setFocusable(false);
        operationsTable.setRequestFocusEnabled(false);
        operationsTable.setRowSelectionAllowed(false);
        operationsTable.getTableHeader().setResizingAllowed(false);
        operationsTable.getTableHeader().setReorderingAllowed(false);
        operationsTable.setUpdateSelectionOnSort(false);
        operationsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(operationsTable);
        if (operationsTable.getColumnModel().getColumnCount() > 0) {
            operationsTable.getColumnModel().getColumn(0).setResizable(false);
            operationsTable.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane2)
                                        .addComponent(jScrollPane1))
                                .addContainerGap())
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        speedSlider.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
        speedSlider.setMajorTickSpacing(25);
        speedSlider.setMaximum(200);
        speedSlider.setMinimum(25);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setValue(100);
        speedSlider.setEnabled(false);
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSliderStateChanged(evt);
            }
        });
        jPanel4.add(speedSlider, java.awt.BorderLayout.CENTER);
        java.util.Hashtable labelTable = new java.util.Hashtable();
        labelTable.put(Integer.valueOf(200), new javax.swing.JLabel("2x"));
        labelTable.put(Integer.valueOf(175), new javax.swing.JLabel("1.75x"));
        labelTable.put(Integer.valueOf(150), new javax.swing.JLabel("1.50x"));
        labelTable.put(Integer.valueOf(125), new javax.swing.JLabel("1.25x"));
        labelTable.put(Integer.valueOf(100), new javax.swing.JLabel("1x"));
        labelTable.put(Integer.valueOf(75), new javax.swing.JLabel("0.75"));
        labelTable.put(Integer.valueOf(50), new javax.swing.JLabel("0.50x"));
        labelTable.put(Integer.valueOf(25), new javax.swing.JLabel("0.25x"));
        speedSlider.setLabelTable(labelTable);

        jLabel1.setFont(new java.awt.Font("Noto Sans", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Speed Selector ( 1.0x )");
        jPanel4.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.setLayout(new java.awt.BorderLayout());

        LabelPanel.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Languge = {a^i.b^j.c^k | i,j,k > 0 and k = i + j}");
        LabelPanel.add(jLabel5, java.awt.BorderLayout.CENTER);

        jLabel6.setFont(new java.awt.Font("Noto Sans", 3, 30)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("PDA SIMULATOR");
        LabelPanel.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jSeparator1.setPreferredSize(new java.awt.Dimension(0, 15));
        LabelPanel.add(jSeparator1, java.awt.BorderLayout.PAGE_END);

        jPanel5.add(LabelPanel, java.awt.BorderLayout.PAGE_START);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Monospaced", 2, 18)); // NOI18N
        jLabel3.setText("User input string to verify:");
        jPanel7.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        userStringInput.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 19)); // NOI18N
        userStringInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userStringInputActionPerformed(evt);
            }
        });
        jPanel7.add(userStringInput, java.awt.BorderLayout.CENTER);

        characterSlider.setPaintTrack(false);
        characterSlider.setEnabled(false);
        characterSlider.setFocusable(false);
        characterSlider.setRequestFocusEnabled(false);
        jPanel7.add(characterSlider, java.awt.BorderLayout.PAGE_END);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
        int speed = speedSlider.getValue();
        jLabel1.setText(String.format("Speed Selector ( %.2gx )", speed / 100.0));
        simulationTask.setDelay((int) (1000 / (speed / 100f)));
    }//GEN-LAST:event_speedSliderStateChanged

    private void CheckStringButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckStringButtonActionPerformed
        if (!userStringInput.getText().matches("a+b+c+")) {
            JOptionPane.showMessageDialog(this, "The string to check must be in the form `{a^i b^j c^k | i,j,k > 0}`", "Invalid Character", JOptionPane.ERROR_MESSAGE);
            return;
        }
        userStringInput.setEnabled(false);
        CheckStringButton.setEnabled(false);
        startSimulation();
    }//GEN-LAST:event_CheckStringButtonActionPerformed

    private void userStringInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userStringInputActionPerformed
        CheckStringButtonActionPerformed(evt);
    }//GEN-LAST:event_userStringInputActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        userStringInput.setEnabled(true);
        userStringInput.setText("");
        statusLabel.setText("The current string is **EMPTY**");
        resetCharacterSlider();
        CheckStringButton.setEnabled(false);
        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
        var stackModel = (DefaultTableModel) stackTable.getModel();
        var opModel = (DefaultTableModel) operationsTable.getModel();
        stackModel.setRowCount(0);
        opModel.setRowCount(0);
    }//GEN-LAST:event_resetButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        if (simulationTask != null) {
            simulationTask.stop();
            stopSimulation = false;
            currentStep = 0;
            simulationTask = null;
        }
        stopButton.setEnabled(false);
        resetButton.setEnabled(true);
    }//GEN-LAST:event_stopButtonActionPerformed

    private void startSimulation() {
        char[] characters = userStringInput.getText().toCharArray();
        var stackModel = (DefaultTableModel) stackTable.getModel();
        var opModel = (DefaultTableModel) operationsTable.getModel();
        opModel.setRowCount(0);
        stackModel.setRowCount(0);
        setupCharacterSlider(characters);
        stackModel.addRow(new Object[]{"$"});
        opModel.addRow(new Object[]{"EMPTY", "PUSH $"});
        simulationTask = new Timer(1000, null);
        simulationTask.setRepeats(true);
        simulationTask.addActionListener(e -> {
            resetButton.setEnabled(false);
            stopButton.setEnabled(true);
            int step = currentStep;
            characterSlider.setValue(step);
            var c = characters[step];
            switch (Character.toLowerCase(c)) {
                case 'a':
                case 'b': {
                    var stackRow = new Object[]{'a'};
                    var opRow = new Object[]{c, "PUSH a"};
                    opModel.addRow(opRow);
                    stackModel.insertRow(0, stackRow);
                    break;
                }

                case 'c': {
                    var firstRow = stackModel.getValueAt(0, 0);
                    if (!firstRow.equals('a')) {
                        JOptionPane.showMessageDialog(PDASimulator.this, "The given string is not in the form `{a^i b^j c^k | i,j,k > 0}`\nThe amount of 'c's in the string is less than the sum of 'a's and 'b's.", "Invalid String", JOptionPane.ERROR_MESSAGE);
                        resetCharacterSlider();
                        stopSimulation = true;
                        break;
                    }
                    stackModel.removeRow(0);
                    var opRow = new Object[]{c, "POP a"};
                    opModel.addRow(opRow);
                }
            }
            simulationTask.setDelay((int) (1000 / (speedSlider.getValue() / 100f)));
            if (++currentStep >= characters.length || stopSimulation) {
                if (stackModel.getRowCount() != 0 && stackModel.getValueAt(0, 0).equals('a')) {
                    JOptionPane.showMessageDialog(PDASimulator.this, "The given string is not in the form `{a^i b^j c^k | i,j,k > 0}`\nThe amount of 'c's in the string is greater than the sum of 'a's and 'b's.", "Invalid String", JOptionPane.ERROR_MESSAGE);
                    statusLabel.setText("The current string is **INVALID**");
                } else {
                    JOptionPane.showMessageDialog(PDASimulator.this, "The given string is in the form `{a^i b^j c^k | i,j,k > 0}`", "Valid String", JOptionPane.INFORMATION_MESSAGE);
                    statusLabel.setText("The current string is **VALID**");
                }
                currentStep = 0;
                stopSimulation = false;
                stopButton.setEnabled(false);
                resetButton.setEnabled(true);
                simulationTask.stop();
                return;
            }
            resetButton.setEnabled(false);
            stopButton.setEnabled(true);
        });
        simulationTask.start();
    }

    private void setupCharacterSlider(char[] characters) {
        characterSlider.setMaximum(characters.length - 1);
        characterSlider.setValue(0);
        characterSlider.setPaintLabels(true);
        characterSlider.setPaintTicks(true);
        characterSlider.setPaintTrack(true);
        characterSlider.setSnapToTicks(true);
        characterSlider.setMajorTickSpacing(1);
        var hash = new Hashtable<Integer, javax.swing.JLabel>();
        var i = 0;
        for (char c : characters) {
            hash.put(i++, new javax.swing.JLabel(String.valueOf(c)));
        }
        characterSlider.setLabelTable(hash);
    }

    private void resetCharacterSlider() {
        characterSlider.setMaximum(100);
        characterSlider.setValue(50);
        characterSlider.setPaintLabels(false);
        characterSlider.setPaintTicks(false);
        characterSlider.setSnapToTicks(false);
        characterSlider.setMajorTickSpacing(0);
        characterSlider.setLabelTable(new Hashtable<>());
    }
}
