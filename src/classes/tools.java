/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import rojeru_san.RSMPassView;
import rojeru_san.RSMTextFull;

/**
 *
 * @author Administrateur
 */
public class tools {

    public static boolean checkTextFields(JTextField... txtField) {
        boolean isEmpty = true;
        for (JTextField txt : txtField) {
            isEmpty &= !txt.getText().trim().isEmpty();
        }
        return !isEmpty;
    }

    public static boolean checkPanelElements(JPanel panel) {
        boolean isEmpty = true;
        Component components[] = panel.getComponents();
        for (Component compo : components) {
            if (compo.getClass().getName().contains("JComboBox") && new JComboBox(compo.toString().split(" ")).getSelectedItem().toString().substring(compo.toString().indexOf("selectedItemReminder=") + "selectedItemReminder=".length(), compo.toString().length() - 1).isEmpty()) {
                isEmpty &= false;
            } else if (compo.getClass().getName().contains("JTextField") && ((JTextField) compo).getText().trim().isEmpty()) {
                isEmpty &= false;
            } else if (compo.getClass().getName().contains("RSMTextFull") && ((RSMTextFull) compo).getText().trim().isEmpty()) {
                isEmpty &= false;
            } else if (compo.getClass().getName().contains("RSMPassView") && ((RSMPassView) compo).getText().trim().isEmpty()) {
                isEmpty &= false;
            }
        }
        return isEmpty;
    }

    public static String getText(Object obj) {
        String object = "";
        if (obj.getClass().getName().contains("JTextField")) {
            object = ((JTextField) obj).getText().trim();
        } else if (obj.getClass().getName().contains("RSMTextFull")) {
            object = ((RSMTextFull) obj).getText().trim();
        } else if (obj.getClass().getName().contains("RSMPassView")) {
            object = ((RSMPassView) obj).getText().trim();
        } else if (obj.getClass().getName().contains("JLabel")) {
            object = ((JLabel) obj).getText().trim();
        } else if (obj.getClass().getName().contains("JComboBox")) {
            object = new JComboBox(obj.toString().split(" ")).getSelectedItem().toString().substring(obj.toString().indexOf("selectedItemReminder=") + "selectedItemReminder=".length(), obj.toString().length() - 1).trim();
        }
        return object;
    }

    public static void openJFrame(JFrame dispose, JFrame form) {
        dispose.dispose();
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }

    // JTabbedPane
    public static void addJTabbedPane(JTabbedPane panelName, String tabbedTitle) {
        JPanel panel = new JPanel();
        panelName.add(tabbedTitle, panel);
    }

    public static void gotoJTabbedPane(JTabbedPane pane, int index) {
        pane.setSelectedIndex(index);
    }

    public static void checkAndCreatePanels(JTabbedPane tbdOptions, String tabTitle, JPanel panel) {
        if (tbdOptions.indexOfTab(tabTitle) == -1) {
            tbdOptions.add(tabTitle, panel);
            tbdOptions.setSelectedIndex(tbdOptions.indexOfTab(tabTitle));
        } else {
            tbdOptions.setSelectedIndex(tbdOptions.indexOfTab(tabTitle));
        }
    }

    public static JPanel closeTabs(final JTabbedPane tabbedPane, final JPanel panel, String title) {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        JLabel titleLbl = new JLabel(title);
        titleLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        titlePanel.add(titleLbl);
        JLabel closeButton = new JLabel(" x");
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tabbedPane.remove(panel);
            }
        });
        titlePanel.add(closeButton);
        return titlePanel;
    }
    
}
