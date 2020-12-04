/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrateur
 */
public class database {
    
    private static String url = "";
    private static Connection con;
    private static PreparedStatement prepared = null;
    private static ResultSet rs = null;
    
    private static void SetURL() {
        url = "jdbc:mysql://localhost:3306/clinique"
                + "?useUnicode=true&characterEncoding=UTF-8";
    }
    
    private static void SetConnection() {
        try {
            SetURL();
            con = DriverManager.getConnection(url, "root", "");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public static Connection getConnection() {
        try {
            SetURL();
            con = DriverManager.getConnection(url, "root", "");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return con;
    }
    
    public static boolean CheckUserAndPass(String UserName, String Password) {
        try {
            SetConnection();
            Statement stm = con.createStatement();
            String StrCheck;
            StrCheck = "select * from userinfo where username='" + UserName + "' and password='" + Password + "'";
            stm.executeQuery(StrCheck);
            if (stm.getResultSet().next()) {
                con.close();
                return true;
            }
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }
    
    public static boolean checkUser(String UserName) {
        try {
            SetConnection();
            Statement stm = con.createStatement();
            String StrCheck;
            StrCheck = "select * from userinfo where username='" + UserName + "'";
            stm.executeQuery(StrCheck);
            if (stm.getResultSet().next()) {
                con.close();
                return true;
            }
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }
    
    public static String splitWithComma(String a[]) {
        String returnedValue = "";
        for (String aa : a) {
            returnedValue += aa + ",";
        }
        return returnedValue.substring(0, returnedValue.length() - 1);
    }
    
    public static String questionMarkNumber(int number) {
        String mark = "";
        for (int start = 1; start <= number; start++) {
            mark += "?, ";
        }
        return mark.substring(0, mark.length() - 2);
    }
    
    public static void insertToDataBase(String tableName, String[] columnName, Object... values) {
        con = getConnection();
        String insert = "INSERT INTO `" + tableName + "`(" + splitWithComma(columnName) + ") VALUES(" + questionMarkNumber(columnName.length) + ")";
        try {
            prepared = con.prepareStatement(insert);
            System.out.println(values.length);
            for (int start = 1; start <= values.length; start++) {
                if (values[start - 1].getClass().getName().contains("Integer") || values[start - 1].getClass().getName().contains("String")) {
                    prepared.setString(start, String.valueOf(values[start - 1]));
                } else if (values[start - 1].getClass().getName().contains("FileInputStream")) {
                    prepared.setBlob(start, ((InputStream) values[start - 1]));
                } else if (values[start - 1].getClass().isArray()) {
                    System.out.println("rana f array");
                    Object[] obj = ((Object[]) values[start - 1]);
                    for (int x = 0; x < obj.length; x++) {
                        if (obj[x - 1].getClass().getName().contains("Integer") || obj[x - 1].getClass().getName().contains("String")) {
                            prepared.setString(start, String.valueOf(obj[x - 1]));
                        } else if (obj[x - 1].getClass().getName().contains("FileInputStream")) {
                            prepared.setBlob(x, ((InputStream) obj[x - 1]));
                        }
                    }
                } else {
                    // OTHER DATA-TYPES                    
                }
            }
            if (prepared.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "Les informations ont été saisies avec succès");
            } else {
                JOptionPane.showMessageDialog(null, "Les informations n'ont pas pu être saisies");
            }
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void showUsersInJComboBox(JComboBox combo, String tableName, String column) {
        Connection connection = getConnection();
        String query = "select * from " + tableName;
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                combo.addItem(rs.getString(column));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public static String getDataFromDataBase(String tableName, String target, String selectorColumn, String selectorValue) {
        Connection connection = getConnection();
        if (target.equals("")) {
            target = "*";
        }
        String query = "select " + target + " from " + tableName + " where " + selectorColumn + "='" + selectorValue + "'";
        String result = "Error";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                result = rs.getString(target);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return result;
    }
    
    public static void searchPatientsBy(JTable table, String data[], String statement) {
        try {
            SetConnection();
            Statement st = con.createStatement();
            ResultSet rs;
            String strSelect;
            strSelect = "select " + splitWithComma(data) + " from " + statement;            
            rs = st.executeQuery(strSelect);
            ResultSetMetaData rsm = rs.getMetaData();
            int c = rsm.getColumnCount();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Vector row = new Vector();
            model.setRowCount(0);
            while (rs.next()) {
                row = new Vector(c);
                for (int i = 1; i <= c; i++) {
                    switch (i) {
                        case 1:
                            row.add(rs.getString("nom"));
                            break;
                        case 2:
                            row.add(rs.getString("prenom"));
                            break;
                        case 3:
                            row.add(rs.getString("age"));
                            break;
                        case 4:
                            row.add(rs.getString("portable"));
                            break;
                        case 5:
                            row.add(rs.getString("email"));
                            break;
                        case 6:
                            row.add(rs.getString("ville"));
                            break;                                                
                        case 7:
                            row.add(rs.getString("assurance"));
                            break;                                                
                    }
                }
                model.addRow(row);
            }
            
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
}
