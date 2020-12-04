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
import java.io.File;
import java.util.regex.PatternSyntaxException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.w3c.dom.events.DocumentEvent;
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

    public static boolean checkPanelElements(JPanel... panels) {
        boolean isEmpty = true;
        ButtonGroup btnG = new ButtonGroup();
        for (JPanel panel : panels) {
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
                } else if (compo.getClass().getName().contains("JRadioButton")) {
                    btnG.add((JRadioButton) compo);
                }
            }
        }
        if (btnG.getSelection() == null) {
            isEmpty &= false;
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

    private static int getTypeNumberInJPanel(JPanel pnl, String... types) {
        int i = 0;
        for (int compo = 0; compo < pnl.getComponentCount(); compo++) {
            for (String type : types) {
                if (pnl.getComponent(compo).getClass().getName().contains(type)) {
                    i++;
                }
            }
        }
        return i;
    }

    public static String[] getDataFromPanel(JPanel... compos) {
        int i = 0;
        String object[] = new String[100];
        for (JPanel compo : compos) {
            for (Component com : compo.getComponents()) {
                if (com.getClass().getName().contains("JTextField")) {
                    object[i] = ((JTextField) com).getText().trim();
                } else if (com.getClass().getName().contains("RSMTextFull")) {
                    object[i] = ((RSMTextFull) compo.getComponent(i)).getText().trim();
                } else if (com.getClass().getName().contains("RSMPassView")) {
                    object[i] = ((RSMPassView) com).getText().trim();
                } else if (com.getClass().getName().contains("JRadioButton")) {
                    if (((JRadioButton) com).isSelected()) {
                        object[i] = ((JRadioButton) com).getText().trim();
                    }
                } else if (com.getClass().getName().contains("JComboBox")) {
                    object[i] = new JComboBox(com.toString().split(" ")).getSelectedItem().toString().substring(com.toString().indexOf("selectedItemReminder=") + "selectedItemReminder=".length(), com.toString().length() - 1).trim();
                } else if (com.getClass().getName().contains("FileInputStream")) {

                }
                i++;
            }

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

    public static void addToRadioGroup(ButtonGroup btnG, JRadioButton... btnsR) {
        for (JRadioButton btn : btnsR) {
            btnG.add(btn);
        }
    }

    public static String choseImage(JLabel lbl_path) {
        String path = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images", "jpg", "gif", "png");
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File select = chooser.getSelectedFile();
            path = select.getAbsolutePath();
            lbl_path.setText(path);
        } else {
            JOptionPane.showMessageDialog(null, "Aucune image n'a été sélectionnée");
        }
        return path;
    }

    public static Object[] insertIntoArray(Object arr[], Object x, int pos) {
        int i;
        int n = arr.length;
        Object newarr[] = new Object[n + 1];
        for (i = 0; i < n + 1; i++) {
            if (i < pos - 1) {
                newarr[i] = arr[i];
            } else if (i == pos - 1) {
                newarr[i] = x;
            } else {
                newarr[i] = arr[i - 1];
            }
        }
        return newarr;
    }

    public static void addArrayDataToJTable(JTable tbl, String data[]) {
        DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
        dtm.addRow(data);
    }

    public static void filterTableBy(JTable tbl, String query) {
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<DefaultTableModel>();
        tbl.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(query));
    }

    public static void AddFilter(JTable tbl, JTextField txtSearch, Integer SearchColumnIndex) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tbl.setRowSorter(sorter);
        String txt = txtSearch.getText().toLowerCase();
        if (txt.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            try {
                sorter.setRowFilter(RowFilter.regexFilter("^(?i)" + txt, SearchColumnIndex));
            } catch (PatternSyntaxException pse) {
                System.out.println("Bad regex pattern");
            }
        }

    }
}
