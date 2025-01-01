package PDASimulator.MultiSelectComboBox;

// credits to Dj-Raven on github: https://github.com/DJ-Raven/raven-java-swing-tutorial-project/blob/main/combobox-multiple-selection

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatCheckBoxIcon;
import com.formdev.flatlaf.ui.FlatComboBoxUI;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <E>
 * @author RAVEN
 */
public class MultiSelectComboBox<E> extends JComboBox<E> {

    private final List<E> selectedItems = new ArrayList<>();
    private final ComboBoxMultiCellEditor comboBoxMultiCellEditor;
    private Component comboList;

    public MultiSelectComboBox() {
        setUI(new ComboBoxMultiUI());
        comboBoxMultiCellEditor = new ComboBoxMultiCellEditor();
        setRenderer(new ComboBoxMultiCellRenderer());
        setEditor(comboBoxMultiCellEditor);
        setEditable(true);
        addActionListener((e) -> {
            if (e.getModifiers() == ActionEvent.MOUSE_EVENT_MASK) {
                @SuppressWarnings("unchecked")
                var combo = (JComboBox<E>) e.getSource();
                Object obj = combo.getSelectedItem();
                if (obj == null) {
                    return;
                }
                if (selectedItems.contains(obj)) {
                    removeItemObject(obj);
                } else {
                    addItemObject(obj);
                }
            }
        });
    }

    public List<E> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Object> selectedItems) {
        List<Object> comboItem = new ArrayList<>();
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            comboItem.add(getItemAt(i));
        }
        for (Object obj : selectedItems) {
            if (comboItem.contains(obj)) {
                addItemObject(obj);
            }
        }
        comboItem.clear();
    }

    public void clearSelectedItems() {
        selectedItems.clear();
        Component editorCom = getEditor().getEditorComponent();
        if (editorCom instanceof JScrollPane scroll) {
            JPanel panel = (JPanel) scroll.getViewport().getComponent(0);
            panel.removeAll();
            revalidate();
            repaint();
            if (comboList != null) {
                comboList.repaint();
            }
            comboList.repaint();
        }
    }

    private void removeItemObject(Object obj) {
        selectedItems.remove(obj);
        comboBoxMultiCellEditor.removeItem(obj);
        if (comboList != null) {
            comboList.repaint();
        }
    }

    private void addItemObject(Object obj) throws ClassCastException {
        selectedItems.add((E) obj);
        comboBoxMultiCellEditor.addItem(obj);
        if (comboList != null) {
            comboList.repaint();
        }
    }

    @Override
    public void setPopupVisible(boolean v) {
        super.setPopupVisible(v);
    }

    private static class ComboBoxMultiUI extends FlatComboBoxUI {

        @Override
        protected ComboPopup createPopup() {
            return new MultiComboPopup(comboBox);
        }

        @Override
        protected Dimension getDisplaySize() {
            Dimension size = super.getDefaultSize();
            return new Dimension(0, size.height);
        }

        private class MultiComboPopup extends FlatComboPopup {

            public MultiComboPopup(JComboBox combo) {
                super(combo);
            }
        }
    }


    private class ComboBoxMultiCellRenderer extends BasicComboBoxRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (comboList != list) {
                comboList = list;
            }
            if (getBackground() == null) {
                setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            }
            setIcon(new FlatCheckBoxIcon() {

                @Override
                public boolean isSelected(Component c) {
                    return selectedItems.contains(value);
                }


            });
            return this;
        }
    }

    private class ComboBoxMultiCellEditor extends BasicComboBoxEditor {

        protected final JScrollPane scroll;
        protected final JPanel panel;

        public ComboBoxMultiCellEditor() {
            this.panel = new JPanel(new MigLayout("insets 0,filly,gapx 2", "", "fill"));
            this.scroll = new JScrollPane(panel);
            scroll.putClientProperty(FlatClientProperties.STYLE, "border:2,2,2,2;"
                    + "background:$ComboBox.editableBackground");
            panel.putClientProperty(FlatClientProperties.STYLE, "background:$ComboBox.editableBackground");
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            JScrollBar scrollBar = scroll.getHorizontalScrollBar();
            scrollBar.putClientProperty(FlatClientProperties.STYLE, "width:3;"
                    + "thumbInsets:0,0,0,1;"
                    + "hoverTrackColor:null");
            scrollBar.setUnitIncrement(10);

        }

        protected void addItem(Object obj) {
            Item item = new Item(obj);
            panel.add(item);
            panel.repaint();
            panel.revalidate();
        }

        protected void removeItem(Object obj) {
            int count = panel.getComponentCount();
            for (int i = 0; i < count; i++) {
                Item item = (Item) panel.getComponent(i);
                if (item.getItem() == obj) {
                    panel.remove(i);
                    panel.revalidate();
                    panel.repaint();
                    break;
                }
            }
        }

        @Override
        public Component getEditorComponent() {
            return scroll;
        }

    }

    private class Item extends JLabel {

        private final Object item;

        public Item(Object item) {
            super(item.toString());
            this.item = item;
            init();
        }

        public Object getItem() {
            return item;
        }

        private void init() {
            putClientProperty(FlatClientProperties.STYLE, "border:0,5,0,20;"
                    + "background:darken($ComboBox.background,10%)");
            JButton cmd = new JButton(new FlatSVGIcon("raven/combobox/close.svg", 0.6f));
            cmd.putClientProperty(FlatClientProperties.STYLE, "arc:999;"
                    + "margin:1,1,1,1;"
                    + "background:null;"
                    + "focusWidth:0");
            cmd.addActionListener((e) -> {
                removeItemObject(item);
            });
            cmd.setFocusable(false);
            setLayout(new MigLayout("fill"));
            add(cmd, "pos 1al 0.5al 10 10");
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            FlatUIUtils.setRenderingHints(g2);
            int arc = UIScale.scale(10);
            g2.setColor(getBackground());
            FlatUIUtils.paintComponentBackground(g2, 0, 0, getWidth(), getHeight(), 0, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}