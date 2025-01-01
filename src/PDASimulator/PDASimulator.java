package PDASimulator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */

import PDASimulator.DecidableTuringMachine.DecidableTuringMachine;
import PDASimulator.DiagramGenerator.DiagramGenerator;
import PDASimulator.InputProcessingState.InputProcessingState;
import PDASimulator.PDASetup.PDASetup;
import PDASimulator.PDASetup.TransitionManagerPanel.TransitionManagerPanel;
import PDASimulator.StackOperation.StackOperation;
import PDASimulator.TransitionInfo.TransitionInfo;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Style;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.svg.SVGDocument;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author zimp
 */
public class PDASimulator extends javax.swing.JFrame {

    private static final String INVALID_PDA_ERROR = """
            The PDA format in the given file is invalid.
            
            Please ensure the all the following keys are included in the YAML/JSON file:
            
            - states ( a list of the names of the states included in this PDA )
            
            - inputAlphabet ( a list of the input characters allowed in this PDA )
            
            - stackAlphabet ( a list of the stack characters allowed in this PDA )
            
            - transitions ( Key-Value pairs describing each \
            transition in the PDA where the key is the initial state \
            and the value is a list of key-value pairs \
            where the key is the go to state and the value is another key-value pair \
            with the following keys:
            
                - consumeCharacter ( the input character to be read or epsilon (ε) )
            
                - stackOp ( the stack operation to be performed: PUSH, POP, or NOP )
            
                - stackOpArg ( the character to be pushed or popped from the stack, or null if stackOp is NOP )
            
            - startState
            
            - finalStates
            
            - languageDescription
            
            - languageSetBuilderForm
            """;
    final double MAX_ZOOM_FACTOR = 2.0;
    final double MIN_ZOOM_FACTOR = 0.1;
    final String HTML_BOILERPLATE = "<html><center>%s</center></html>";

    // String.format(INPUT_STATE_HTML, "color", "font-size", "state")
    final String INPUT_STATE_HTML = String.format(HTML_BOILERPLATE, "The current string is<br><p style=\"color: %s font-size: %dpx\">%s</p>");
    // String.format(STATE_HTML, "speed double")
    final String SPEED_X_HTML = String.format(HTML_BOILERPLATE, "Speed:<br>%.2gx");
    // String.format(ZOOM_PERCENT_HTML, "zoom double")
    final String ZOOM_PERCENT_HTML = String.format(HTML_BOILERPLATE, "Zoom:<br>%.2f%%");
    // String.format(CURRENT_CHAR_HTML, "before", "size", "char", "after")
    final String CURRENT_CHAR_HTML = String.format(HTML_BOILERPLATE, "%s<strong style=\"font-size:%dpx\">%s</strong>%s");

    DecidableTuringMachine turingMachine;
    DiagramGenerator dg;
    PDASetup setupDialog;

    int currentStep = 0; // Current step of the simulation
    String currentState;
    boolean stopSimulation = false; // Flag to stop the simulation
    Timer simulationTask = null;
    Boolean inputAccepted = null;

    double zoomFactor = 1;
    int simulationSpeed = 100;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CheckStringButton;
    private javax.swing.JPanel StackPanel;
    private javax.swing.JPanel bottomPanelWithButtonAndStatus;
    private javax.swing.JPanel currentCharPanel;
    private javax.swing.JPanel customSpeedPanel;
    private javax.swing.JPanel customZoomPanel;
    private org.apache.batik.swing.JSVGCanvas diagramPanel;
    private javax.swing.JButton editPDAButton;
    private javax.swing.JButton exportPDAButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JLabel highlightAndStatusLabel;
    private javax.swing.JTable historyTable;
    private javax.swing.JButton importPDAButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel languageDescriptionLabel;
    private javax.swing.JLabel languageSetBuilderFormLabel;
    private javax.swing.JButton minusSpeedButton;
    private javax.swing.JButton minusZoomButton;
    private javax.swing.JTable operationsTable;
    private javax.swing.JButton plusSpeedButton;
    private javax.swing.JButton plusZoomButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JTable stackTable;
    private javax.swing.JScrollPane statesTableScrollPane;
    private javax.swing.JButton stopButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titleLabelPanel;
    private javax.swing.JTextField userStringInput;
    private javax.swing.JLabel zoomLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form PDASimulator
     */
    public PDASimulator() {
        initComponents();
        this.turingMachine = new DecidableTuringMachine();
        this.dg = new DiagramGenerator(turingMachine);
        this.setupDialog = new PDASetup(this, true, turingMachine);
        userStringInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateString(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateString(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateString(e);
            }

            private void updateString(DocumentEvent e) {
                String text = userStringInput.getText();
                stopButton.setEnabled(false);
                boolean buttonState;
                InputProcessingState ips;
                if (text.isEmpty()) {
                    buttonState = false;
                    ips = InputProcessingState.EMPTY;
                } else {
                    buttonState = true;
                    ips = InputProcessingState.UNPROCESSED;
                    if (e.getType() == DocumentEvent.EventType.INSERT || e.getType() == DocumentEvent.EventType.CHANGE) {
                        var addedChars = text.substring(e.getOffset(), e.getOffset() + e.getLength());
                        var hasInvalidChars = !turingMachine.inputAlphabet.containsAll(addedChars.chars().mapToObj(Character::toString).toList());
                        if (hasInvalidChars) {
                            var invalidChars = addedChars.chars().mapToObj(Character::toString).toArray();
                            JOptionPane.showMessageDialog(
                                    PDASimulator.this,
                                    "Only characters " +
                                            humanize_list(turingMachine.inputAlphabet.toArray()) +
                                            " are allowed",
                                    "Invalid Characters; \"" +
                                            humanize_list(invalidChars) + "\"",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            SwingUtilities.invokeLater(() -> userStringInput.setText(text.substring(0, e.getOffset()) + text.substring(e.getOffset() + e.getLength())));
                            return;
                        }
                    }
                }
                CheckStringButton.setEnabled(buttonState);
                resetButton.setEnabled(buttonState);
                highlightAndStatusLabel.setText(getFormattedSimulatorState(ips));
            }
        });
    }

    public static <T> String humanize_list(T[] arr) {
        switch (arr.length) {
            case 0 -> {
                return "";
            }
            case 1 -> {
                return arr[0].toString();
            }
            case 2 -> {
                return arr[0].toString() + " and " + arr[1].toString();
            }
            default -> {
                var sb = new StringBuilder();
                for (int i = 0; i < arr.length - 1; i++) {
                    sb.append(arr[i].toString()).append(", ");
                }
                sb.append(" and ").append(arr[arr.length - 1]);
                return sb.toString();
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacLightLaf.setup();

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PDASimulator frame = new PDASimulator();
                frame.setVisible(true);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        if (frame.simulationTask != null) {
                            frame.simulationTask.stop();
                        }
                    }
                });
//                var dialog = frame.setupDialog;
//                dialog.setVisible(true);
//                dialog.setLocationRelativeTo(frame);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosed(java.awt.event.WindowEvent e) {
//                        if (dialog.stateManager.allowNext(false) && dialog.alphabetManager.allowNext(false) && dialog.transitionManager.allowNext(false)) {
//                            frame.currentState = frame.turingMachine.startState;
//                            frame.editPDAButton.setEnabled(true);
//                            return;
//                        }
//                        frame.dispose();
//                    }
//                });
            }
        });
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf + 1);
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

        bottomPanelWithButtonAndStatus = new javax.swing.JPanel();
        importPDAButton = new javax.swing.JButton();
        exportPDAButton = new javax.swing.JButton();
        titleLabelPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        editPDAButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        StackPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        stackTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        operationsTable = new javax.swing.JTable();
        userStringInput = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        languageDescriptionLabel = new javax.swing.JLabel();
        languageSetBuilderFormLabel = new javax.swing.JLabel();
        diagramPanel = new SuppressedErrorsJSVGCanvas();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        customSpeedPanel = new javax.swing.JPanel();
        minusSpeedButton = new javax.swing.JButton();
        speedLabel = new javax.swing.JLabel();
        plusSpeedButton = new javax.swing.JButton();
        currentCharPanel = new javax.swing.JPanel();
        highlightAndStatusLabel = new javax.swing.JLabel();
        customZoomPanel = new javax.swing.JPanel();
        minusZoomButton = new javax.swing.JButton();
        plusZoomButton = new javax.swing.JButton();
        zoomLabel = new javax.swing.JLabel();
        stopButton = new javax.swing.JButton();
        statesTableScrollPane = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        CheckStringButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 800));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        bottomPanelWithButtonAndStatus.setLayout(new java.awt.GridBagLayout());

        importPDAButton.setBackground(new java.awt.Color(0, 0, 0));
        importPDAButton.setForeground(new java.awt.Color(255, 255, 255));
        importPDAButton.setText("Import PDA");
        importPDAButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        importPDAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importPDAButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        bottomPanelWithButtonAndStatus.add(importPDAButton, gridBagConstraints);

        exportPDAButton.setBackground(new java.awt.Color(0, 0, 0));
        exportPDAButton.setForeground(new java.awt.Color(255, 255, 255));
        exportPDAButton.setText("Export PDA");
        exportPDAButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exportPDAButton.setEnabled(false);
        exportPDAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportPDAButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        bottomPanelWithButtonAndStatus.add(exportPDAButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(bottomPanelWithButtonAndStatus, gridBagConstraints);

        titleLabelPanel.setLayout(new java.awt.GridBagLayout());

        titleLabel.setFont(new java.awt.Font("Noto Sans", 3, 30)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("PDA SIMULATOR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        titleLabelPanel.add(titleLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        titleLabelPanel.add(jSeparator2, gridBagConstraints);

        editPDAButton.setBackground(new java.awt.Color(0, 0, 0));
        editPDAButton.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        editPDAButton.setForeground(new java.awt.Color(255, 255, 255));
        editPDAButton.setText("Setup PDA");
        editPDAButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editPDAButton.setPreferredSize(new java.awt.Dimension(141, 24));
        editPDAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPDAButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        titleLabelPanel.add(editPDAButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(titleLabelPanel, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        StackPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(70, 80));

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
        stackTable.setEnabled(false);
        stackTable.setFocusable(false);
        stackTable.setRowSelectionAllowed(false);
        stackTable.getTableHeader().setReorderingAllowed(false);
        stackTable.setUpdateSelectionOnSort(false);
        stackTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(stackTable);
        if (stackTable.getColumnModel().getColumnCount() > 0) {
            stackTable.getColumnModel().getColumn(0).setResizable(false);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        StackPanel.add(jScrollPane2, gridBagConstraints);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(110, 100));

        operationsTable.setFont(new java.awt.Font("DejaVu Sans", 1, 20)); // NOI18N
        operationsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String[]{
                        "<html><center>Input<br>Char</center></html>", "<html><center>Stack<br/>Operation</center></html>"
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
        operationsTable.setEnabled(false);
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
            operationsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            operationsTable.getColumnModel().getColumn(1).setResizable(false);
            operationsTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        StackPanel.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel1.add(StackPanel, gridBagConstraints);

        userStringInput.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        userStringInput.setEnabled(false);
        userStringInput.setPreferredSize(new java.awt.Dimension(350, 28));
        userStringInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userStringInputActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        jPanel1.add(userStringInput, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        languageDescriptionLabel.setFont(new java.awt.Font("DejaVu Sans", 2, 14)); // NOI18N
        languageDescriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        languageDescriptionLabel.setText("gay");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        jPanel2.add(languageDescriptionLabel, gridBagConstraints);

        languageSetBuilderFormLabel.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        languageSetBuilderFormLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        languageSetBuilderFormLabel.setText("gay 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        jPanel2.add(languageSetBuilderFormLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.2;
        jPanel1.add(jPanel2, gridBagConstraints);

        diagramPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                    double delta = -MIN_ZOOM_FACTOR * e.getWheelRotation();
                    zoomFactor += delta;
                    if (zoomFactor < MIN_ZOOM_FACTOR) {
                        zoomFactor = MIN_ZOOM_FACTOR;
                    } else if (zoomFactor > MAX_ZOOM_FACTOR) {
                        zoomFactor = MAX_ZOOM_FACTOR;
                    }
                    AffineTransform at = new AffineTransform();
                    at.translate(e.getX(), e.getY());
                    at.scale(zoomFactor, zoomFactor);
                    at.translate(-e.getX(), -e.getY());
                    diagramPanel.setRenderingTransform(at);
                    zoomLabel.setText(String.format(ZOOM_PERCENT_HTML, zoomFactor * 100));
                }
            }
        });
        diagramPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        diagramPanel.add(filler1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        diagramPanel.add(filler2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        diagramPanel.add(filler3, gridBagConstraints);

        customSpeedPanel.setBackground(new java.awt.Color(255, 255, 255));
        customSpeedPanel.setLayout(new java.awt.GridBagLayout());

        minusSpeedButton.setText("-");
        minusSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speedButtonsHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        customSpeedPanel.add(minusSpeedButton, gridBagConstraints);

        speedLabel.setText("<html><center>Speed:<br>1.0x<center></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        customSpeedPanel.add(speedLabel, gridBagConstraints);

        plusSpeedButton.setText("+");
        plusSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speedButtonsHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        customSpeedPanel.add(plusSpeedButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        diagramPanel.add(customSpeedPanel, gridBagConstraints);

        currentCharPanel.setBackground(new java.awt.Color(255, 255, 255));
        currentCharPanel.setLayout(new java.awt.GridBagLayout());

        highlightAndStatusLabel.setBackground(new java.awt.Color(0, 0, 0, 0));
        highlightAndStatusLabel.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 20)); // NOI18N
        highlightAndStatusLabel.setText(getFormattedSimulatorState());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        currentCharPanel.add(highlightAndStatusLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        diagramPanel.add(currentCharPanel, gridBagConstraints);

        customZoomPanel.setBackground(new java.awt.Color(255, 255, 255));
        customZoomPanel.setLayout(new java.awt.GridBagLayout());

        minusZoomButton.setText("-");
        minusZoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomButtonsHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        customZoomPanel.add(minusZoomButton, gridBagConstraints);

        plusZoomButton.setText("+");
        plusZoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomButtonsHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        customZoomPanel.add(plusZoomButton, gridBagConstraints);

        zoomLabel.setText("<html><center>Zoom:<br>100%</center></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        customZoomPanel.add(zoomLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        diagramPanel.add(customZoomPanel, gridBagConstraints);

        stopButton.setBackground(new java.awt.Color(0, 0, 0));
        stopButton.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        stopButton.setForeground(new java.awt.Color(255, 255, 255));
        stopButton.setText("Stop Simulation");
        stopButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stopButton.setEnabled(false);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        diagramPanel.add(stopButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 1, 0);
        jPanel1.add(diagramPanel, gridBagConstraints);

        statesTableScrollPane.setPreferredSize(new java.awt.Dimension(30, 100));

        historyTable.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "History", "Accepted?"
                }
        ) {
            final Class[] types = new Class[]{
                    java.lang.String.class, java.lang.Boolean.class
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
        historyTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        statesTableScrollPane.setViewportView(historyTable);
        if (historyTable.getColumnModel().getColumnCount() > 0) {
            historyTable.getColumnModel().getColumn(0).setResizable(false);
            historyTable.getColumnModel().getColumn(1).setResizable(false);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        jPanel1.add(statesTableScrollPane, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        CheckStringButton.setBackground(new java.awt.Color(0, 0, 0));
        CheckStringButton.setFont(new java.awt.Font("Noto Sans", 0, 15)); // NOI18N
        CheckStringButton.setForeground(new java.awt.Color(255, 255, 255));
        CheckStringButton.setText("Check String");
        CheckStringButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckStringButton.setEnabled(false);
        CheckStringButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckStringButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(CheckStringButton, gridBagConstraints);

        resetButton.setBackground(new java.awt.Color(0, 0, 0));
        resetButton.setForeground(new java.awt.Color(255, 255, 255));
        resetButton.setText("Reset Simulation");
        resetButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetButton.setEnabled(false);
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(resetButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void userStringInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userStringInputActionPerformed
        CheckStringButtonActionPerformed(evt);
    }//GEN-LAST:event_userStringInputActionPerformed

    private void CheckStringButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckStringButtonActionPerformed
        userStringInput.setEnabled(false);
        CheckStringButton.setEnabled(false);
        startSimulation();
    }//GEN-LAST:event_CheckStringButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        ((DefaultTableModel) historyTable.getModel()).addRow(new Object[]{String.format(HTML_BOILERPLATE, userStringInput.getText()), inputAccepted});
        userStringInput.setEnabled(true);
        userStringInput.setText("");
        resetCharacterCounter();
        var stackModel = (DefaultTableModel) stackTable.getModel();
        var opModel = (DefaultTableModel) operationsTable.getModel();
        stackModel.setRowCount(0);
        opModel.setRowCount(0);
        inputAccepted = null;
        simulationTask = null;
        paintStaticImage();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        if (simulationTask != null) {
            simulationTask.stop();
            stopSimulation = false;
            currentStep = 0;
            simulationTask = null;
            inputAccepted = false;
        }
        stopButton.setEnabled(false);
        resetButton.setEnabled(true);
    }//GEN-LAST:event_stopButtonActionPerformed

    private void editPDAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPDAButtonActionPerformed

        setupDialog.setTuringMachine(turingMachine);
        this.dg.setTuringMachine(turingMachine);
        setupDialog.refreshState();
        setupDialog.setVisible(true);
        setupDialog.setLocationRelativeTo(this);
        if (setupDialog.stateManager.allowNext(false) && setupDialog.alphabetManager.allowNext(false) && setupDialog.transitionManager.allowNext(false)) {
            enableSimulator();
        } else {
            editPDAButton.setEnabled(true);
            resetButton.setEnabled(false);
            importPDAButton.setEnabled(true);
            exportPDAButton.setEnabled(false);
            CheckStringButton.setEnabled(false);
            userStringInput.setEnabled(false);
            stopButton.setEnabled(false);
        }
    }//GEN-LAST:event_editPDAButtonActionPerformed

    private void importPDAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importPDAButtonActionPerformed
        var fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Select a PDA file (.JSON, .YAML, .YML)");
        var restrict = new FileNameExtensionFilter("Only .json and .yaml files", "json", "yaml", "yml");
        fileChooser.addChoosableFileFilter(restrict);
        var r = fileChooser.showOpenDialog(this);
        if (r == JFileChooser.APPROVE_OPTION) {
            var ext = getFileExtension(fileChooser.getSelectedFile());
            ObjectMapper om;
            switch (ext) {
                case "json":
                    om = new ObjectMapper();
                    break;
                case "yaml", "yml":
                    om = new ObjectMapper(new YAMLFactory());
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid file format", "Invalid file", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            try {
                var tm = om.readValue(fileChooser.getSelectedFile(), DecidableTuringMachine.class);
                this.turingMachine.updateFrom(tm);
                enableSimulator();
            } catch (DatabindException e) {
                JOptionPane.showMessageDialog(this,
                        INVALID_PDA_ERROR,
                        "Invalid PDA Format",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_importPDAButtonActionPerformed

    private void exportPDAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportPDAButtonActionPerformed
        var fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        var r = fileChooser.showSaveDialog(this);
        if (r == JFileChooser.APPROVE_OPTION) {
            var filename = JOptionPane.showInputDialog(this, "Enter the file name (include .json or .yaml as the extension)", "Input file name", JOptionPane.QUESTION_MESSAGE);
            if (!filename.contains(".")) {
                JOptionPane.showMessageDialog(this, "Invalid file format (missing extension?)", "Invalid file", JOptionPane.ERROR_MESSAGE);
                return;
            }
            var ext = filename.substring(filename.lastIndexOf(".") + 1);
            ObjectMapper om;
            switch (ext.toLowerCase()) {
                case "json":
                    om = new ObjectMapper();
                    break;
                case "yaml":
                case "yml":
                    om = new ObjectMapper(new YAMLFactory());
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid file format", "Invalid file", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            try {
                om.writeValue(new File(fileChooser.getSelectedFile().getAbsolutePath() + File.separator + filename), turingMachine);
                JOptionPane.showMessageDialog(this, "File saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error writing file", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_exportPDAButtonActionPerformed

    private void speedButtonsHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speedButtonsHandler
        if (evt.getSource() == minusSpeedButton) {
            simulationSpeed = Math.max(15, simulationSpeed - 10);
        } else {
            simulationSpeed = Math.min(200, simulationSpeed + 10);
        }
        if (simulationTask != null) {
            simulationTask.setDelay((int) (1300 / (simulationSpeed / 100f)));
        }
        speedLabel.setText(String.format(SPEED_X_HTML, simulationSpeed / 100f));
    }//GEN-LAST:event_speedButtonsHandler

    private void zoomButtonsHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomButtonsHandler
        if (evt.getSource() == plusZoomButton) {
            zoomFactor = Math.min(MAX_ZOOM_FACTOR, zoomFactor + 0.1);
        } else {
            zoomFactor = Math.max(MIN_ZOOM_FACTOR, zoomFactor - 0.1);
        }
        AffineTransform at = new AffineTransform();
        at.translate((double) diagramPanel.getWidth() / 2, (double) diagramPanel.getHeight() / 2);
        at.scale(zoomFactor, zoomFactor);
        at.translate((double) -diagramPanel.getWidth() / 2, (double) -diagramPanel.getHeight() / 2);
        diagramPanel.setRenderingTransform(at);
        zoomLabel.setText(String.format(ZOOM_PERCENT_HTML, zoomFactor * 100));
    }//GEN-LAST:event_zoomButtonsHandler

    public void enableSimulator() {
        userStringInput.setEnabled(true);
        exportPDAButton.setEnabled(true);
        editPDAButton.setText("Edit PDA");
        languageDescriptionLabel.setMaximumSize(new Dimension(userStringInput.getWidth(), languageDescriptionLabel.getHeight()));
        languageDescriptionLabel.setText(String.format(HTML_BOILERPLATE, turingMachine.languageDescription));
        languageSetBuilderFormLabel.setMaximumSize(new Dimension(userStringInput.getWidth(), languageSetBuilderFormLabel.getHeight()));
        languageSetBuilderFormLabel.setText(String.format(HTML_BOILERPLATE, turingMachine.languageSetBuilderForm));
        var stackModel = (DefaultTableModel) stackTable.getModel();
        var opModel = (DefaultTableModel) operationsTable.getModel();
        stackModel.setRowCount(0);
        opModel.setRowCount(0);
        userStringInput.setText("");
        resetCharacterCounter();
        paintStaticImage();
    }

    private void startSimulation() {
        editPDAButton.setEnabled(false);
        stopSimulation = false;
        currentState = turingMachine.startState;
//        paintHighlightedStateImage();
        var characters = userStringInput.getText().split("");
        var stackModel = (DefaultTableModel) stackTable.getModel();
        var opModel = (DefaultTableModel) operationsTable.getModel();
        opModel.setRowCount(0);
        stackModel.setRowCount(0);
        setupCharacterCounter(characters);
        simulationTask = new Timer(1300, null);
        simulationTask.setRepeats(true);
        simulationTask.addActionListener(e -> {
            resetButton.setEnabled(false);
            stopButton.setEnabled(true);
            paintHighlightedStateImage();
            simulate(characters);
            simulationTask.setDelay((int) (1300 / (simulationSpeed / 100f)));
            if (stopSimulation) {
                if (turingMachine.isFinalState(currentState) && currentStep >= characters.length) {
                    inputAccepted = true;
                    paintHighlightedStateImage();
                    JOptionPane.showMessageDialog(this, "The given input string was accepted by the specified PDA", "Valid String", JOptionPane.INFORMATION_MESSAGE);
                    var ips = InputProcessingState.VALID;
                    highlightAndStatusLabel.setText(getFormattedSimulatorState(ips));
                } else {
                    inputAccepted = false;
                    paintHighlightedStateImage();
                    JOptionPane.showMessageDialog(this, "The given input string was not accepted by the specified PDA", "Invalid String", JOptionPane.ERROR_MESSAGE);
                    var ips = InputProcessingState.INVALID;
                    highlightAndStatusLabel.setText(getFormattedSimulatorState(ips));
                }
                currentStep = 0;
                currentState = turingMachine.startState;
                stopSimulation = false;
                stopButton.setEnabled(false);
                resetButton.setEnabled(true);
                editPDAButton.setEnabled(true);
                simulationTask.stop();
                return;
            }
            resetButton.setEnabled(false);
            stopButton.setEnabled(true);
        });
        simulationTask.setInitialDelay(0);
        simulationTask.start();
    }

    private void setupCharacterCounter(String[] characters) {
        highlightAndStatusLabel.setText(String.format(CURRENT_CHAR_HTML, String.join("", characters), highlightAndStatusLabel.getFont().getSize(), "", ""));
    }

    private void resetCharacterCounter() {
        highlightAndStatusLabel.setText(String.format(CURRENT_CHAR_HTML, "", highlightAndStatusLabel.getFont().getSize(), "", ""));
    }

    private void simulate(String[] input) {
        var possibleTransitions = new HashMap<String, ArrayList<TransitionInfo>>();
        var blankTransitions = new HashMap<String, ArrayList<TransitionInfo>>();
        String topOfStack = stackTable.getRowCount() > 0 ? (String) stackTable.getValueAt(0, 0) : "";
        var allTransitions = turingMachine.transitions.get(currentState);
        if ((allTransitions == null || allTransitions.isEmpty())) {
//            if (currentStep < input.length && !turingMachine.isFinalState(currentState)) {
//                JOptionPane.showMessageDialog(PDASimulator.this, "The given input string was not accepted by the specified PDA`", "Invalid String", JOptionPane.ERROR_MESSAGE);
//            }
            stopSimulation = true;
            return;
        }
        var currentChar = currentStep < input.length ? input[currentStep] : TransitionManagerPanel.BLANK_CHARACTER;
        allTransitions.forEach(
                (gotoState, possibilities) -> {
                    var toAdd = possibilities.stream().filter(
                            ti -> {
                                if (ti.stackOp == StackOperation.POP && !topOfStack.equals(ti.stackOpArg)) {
                                    return false;
                                }
                                if (ti.consumeCharacter.equals(TransitionManagerPanel.BLANK_CHARACTER)) {
                                    var arr = blankTransitions.computeIfAbsent(gotoState, _ -> new ArrayList<>());
                                    arr.add(ti);
                                    return false;
                                } else
                                    return ti.consumeCharacter.equals(currentChar);
                            }
                    ).collect(Collectors.toCollection(ArrayList::new));
                    if (!toAdd.isEmpty()) {
                        possibleTransitions.put(gotoState, toAdd);
                    }

                }
        );


        if (possibleTransitions.isEmpty() && blankTransitions.isEmpty()) {
            stopSimulation = true;
            return;
        }
        var transitionHash = possibleTransitions.isEmpty() ? blankTransitions : possibleTransitions;
        var gotoState = transitionHash.keySet().iterator().next();
        var ti = transitionHash.get(gotoState).getFirst();

        this.handleTransition(currentState, gotoState, ti);

    }

    private void handleTransition(String initialState, String gotoState, TransitionInfo ti) {
        var text = userStringInput.getText();
        var beforeChar = text.substring(0, Math.min(currentStep, text.length()));
        var curChar = currentStep < text.length() ? text.substring(currentStep, currentStep + 1) : "";
        var afterChar = currentStep + 1 < text.length() ? text.substring(currentStep + 1) : "";
        highlightAndStatusLabel.setText(String.format(CURRENT_CHAR_HTML,
                        beforeChar,
                        highlightAndStatusLabel.getFont().getSize(),
                        curChar,
                        afterChar
                )
        );

        paintHighlightedTransitionImage(gotoState, ti);

        try {
            Thread.sleep((int) (1000 / (simulationSpeed / 100f)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!ti.consumeCharacter.equals(TransitionManagerPanel.BLANK_CHARACTER)) {
            currentStep += 1;
        }
        if (ti.stackOp != StackOperation.NOP) {
            ((DefaultTableModel) operationsTable.getModel()).addRow(new Object[]{ti.consumeCharacter, ti.stackOp.name() + " " + ti.stackOpArg});
            if (ti.stackOp == StackOperation.POP) {
                ((DefaultTableModel) stackTable.getModel()).removeRow(0);
            } else {
                ((DefaultTableModel) stackTable.getModel()).insertRow(0, new Object[]{ti.stackOpArg});
            }
        }
        if (!initialState.equals(gotoState)) {
            currentState = gotoState;
        }
//        paintHighlightedStateImage();
    }

    public void paintStaticImage() {
        var svg = dg.generate();
        setSVGToPanel(svg);
    }

    public void paintHighlightedStateImage() {
        var color = inputAccepted == null ? Color.YELLOW : inputAccepted ? Color.GREEN : Color.RED;
        var svg = dg.generateWithHighlightedState(currentState, color, Style.lineWidth(3), Style.FILLED);
        setSVGToPanel(svg);
    }

    public void paintHighlightedTransitionImage(String finalState, TransitionInfo ti) {
        var color = Color.YELLOW;
        var svg = dg.generateWithHighlightedTransition(currentState, finalState, ti, color, Style.lineWidth(3), Style.BOLD, Style.DASHED);
//        ((SuppressedErrorsJSVGCanvas) diagramPanel).setShowError(true);
        setSVGToPanel(svg);
    }

    private void setSVGToPanel(String svg) {
        try {
            var svgReplaced = svg.replaceAll("stroke=\"transparent\"", "stroke-opacity=\"0\"");
            var at = diagramPanel.getRenderingTransform();
            var doc = new SAXSVGDocumentFactory(org.apache.batik.util.XMLResourceDescriptor.getXMLParserClassName()).createDocument(null, new java.io.StringReader(svgReplaced));
            diagramPanel.setSVGDocument((SVGDocument) doc);
            diagramPanel.setRenderingTransform(at);
            var gnode = diagramPanel.getCanvasGraphicsNode();
            if (gnode != null) {
                gnode.fireGraphicsNodeChangeCompleted();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getFormattedSimulatorState() {
        var ips = InputProcessingState.EMPTY;
        return String.format(
                INPUT_STATE_HTML, ips.colorToCssString(), highlightAndStatusLabel.getFont().getSize() + 3, ips.name()
        );
    }

    private String getFormattedSimulatorState(InputProcessingState ips) {
        return String.format(
                INPUT_STATE_HTML, ips.colorToCssString(), highlightAndStatusLabel.getFont().getSize() + 3, ips.name()
        );
    }

    private static class SuppressedErrorsJSVGCanvas extends JSVGCanvas {

        private boolean showError = false;

        public SuppressedErrorsJSVGCanvas() {
            super();
            setDocumentState(ALWAYS_DYNAMIC);
        }

        public boolean getShowError() {
            return showError;
        }

        public void setShowError(boolean showError) {
            this.showError = showError;
        }

        @Override
        protected SuppressedErrorsCanvasAgent createUserAgent() {
            return new SuppressedErrorsCanvasAgent();
        }

        private class SuppressedErrorsCanvasAgent extends CanvasUserAgent {
            @Override
            public void displayError(String message) {
                if (showError) {
                    setShowError(false);
                    System.out.println(message);
                }
            }

            @Override
            public void displayError(Exception ex) {
                if (showError) {
                    setShowError(false);
                    ex.printStackTrace();
                }
            }


        }
    }
}
