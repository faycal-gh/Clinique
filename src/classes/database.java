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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

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

    public static String getDataFromDataBase(String tableName, String target, String selectorColumn, String selectorValue) {
        Connection connection = getConnection();
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
}