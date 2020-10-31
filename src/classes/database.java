/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                if(values[start].getClass().getName().contains("Integer") || values[start].getClass().getName().contains("String")){
                    prepared.setString(start, String.valueOf(values[start - 1]));                    
                }else{
                    // OTHER DATA-TYPES
                }
            }            
            if (prepared.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "تم إدخال المعلومات بنجاح");
            } else {
                JOptionPane.showMessageDialog(null, "تعذر إدخال المعلومات ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}