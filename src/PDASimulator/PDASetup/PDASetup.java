package PDASimulator.PDASetup;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */

import PDASimulator.DecidableTuringMachine.DecidableTuringMachine;
import PDASimulator.PDASetup.AlphabetPanel.AlphabetPanel;
import PDASimulator.PDASetup.PDASetupBase.PDASetupBase;
import PDASimulator.PDASetup.StateManagerPanel.StateManagerPanel;
import PDASimulator.PDASetup.TransitionManagerPanel.TransitionManagerPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author zimp
 */
public class PDASetup extends javax.swing.JDialog {

    public StateManagerPanel stateManager;
    public AlphabetPanel alphabetManager;
    public TransitionManagerPanel transitionManager;
    DecidableTuringMachine turingMachine;
    String[] panels;
    int panelIndex = 0;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton nextButton;

    /**
     * Creates new form PDASetup
     */
    public PDASetup(java.awt.Frame parent, boolean modal, DecidableTuringMachine turingMachine) {
        super(parent, modal);
        this.turingMachine = turingMachine;
        this.stateManager = new StateManagerPanel(turingMachine);
        this.alphabetManager = new AlphabetPanel(turingMachine);
        this.transitionManager = new TransitionManagerPanel(turingMachine);

        this.panels = new String[]{"stateManager", "alphabetManager", "transitionManager"};

        initComponents();
        this.setTitle("PDA Setup");

    }
    // End of variables declaration//GEN-END:variables

    public void setTuringMachine(DecidableTuringMachine turingMachine) {
        this.turingMachine = turingMachine;
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PDASetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(PDASetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(PDASetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(PDASetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                PDASetup dialog = new PDASetup(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        cardPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        cardPanel.setLayout(new java.awt.CardLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(cardPanel, gridBagConstraints);
        this.cardPanel.add(stateManager, "stateManager");
        this.cardPanel.add(alphabetManager, "alphabetManager");
        this.cardPanel.add(transitionManager, "transitionManager");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "stateManager");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        backButton.setText("Back");
        backButton.setEnabled(false);
        backButton.setPreferredSize(new java.awt.Dimension(80, 30));
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next_backButtonHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(backButton, gridBagConstraints);

        nextButton.setText("Next");
        nextButton.setPreferredSize(new java.awt.Dimension(80, 30));
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next_backButtonHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(nextButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void askForLanguage() {
        var languageSetBuilder = JOptionPane.showInputDialog(this,
                "Enter the language set builder form (E.G. { w | w^rev == w } ):",
                "Language Set Builder Form",
                JOptionPane.QUESTION_MESSAGE
        );
        var languageDescription = JOptionPane.showInputDialog(this,
                "Enter the language description (E.G. The set of all palindromes):",
                "Language Description",
                JOptionPane.QUESTION_MESSAGE
        );

        this.turingMachine.languageSetBuilderForm = languageSetBuilder;
        this.turingMachine.languageDescription = languageDescription;
    }

    private void confirmToProceed() {
        var opt = JOptionPane.showConfirmDialog(this,
                "The following is the machine's configuration:\n\n" +
                        this.turingMachine.toString() +
                        "\n\nAre you sure you want to proceed?",
                "Confirm to proceed", JOptionPane.YES_NO_OPTION
        );
        if (opt == JOptionPane.YES_OPTION) {
            if (this.turingMachine.languageDescription.isBlank() || this.turingMachine.languageSetBuilderForm.isBlank()) {
                this.askForLanguage();
            }
            this.dispose();

        }

    }

    private void next_backButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_next_backButtonHandler
        var curPanel = (PDASetupBase) cardPanel.getComponent(this.panelIndex);
        if (evt.getSource() == this.nextButton) {
            if (!curPanel.allowNext(true)) {
                return;
            }
            this.panelIndex += 1;
        } else {
            this.panelIndex -= 1;
        }
        if (this.panelIndex == 0) {
            this.backButton.setEnabled(false);
        } else if (this.panelIndex >= panels.length) {
            this.panelIndex = panels.length - 1;
            this.confirmToProceed();
            return;
        } else {
            this.backButton.setEnabled(true);
        }
        var nextPanel = (PDASetupBase) cardPanel.getComponent(this.panelIndex);
        nextPanel.turingMachine = turingMachine;
        nextPanel.updateTuringMachineState();
        ((CardLayout) this.cardPanel.getLayout()).show(this.cardPanel, this.panels[this.panelIndex]);

    }//GEN-LAST:event_next_backButtonHandler

    public void refreshState() {
        this.stateManager.turingMachine = this.turingMachine;
        this.stateManager.updateTuringMachineState();
        this.alphabetManager.turingMachine = this.turingMachine;
        this.alphabetManager.updateTuringMachineState();
        this.transitionManager.turingMachine = this.turingMachine;
        this.transitionManager.updateTuringMachineState();
    }
}
