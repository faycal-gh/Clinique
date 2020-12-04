/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClasses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 0day
 */
public class patients {

    String nom, prenom, age, phone, email, ville, typeAss;

    public patients(String nom, String prenom, String age, String phone, String email, String ville, String typeAss) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.ville = ville;
        this.typeAss = typeAss;        
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getVille() {
        return ville;
    }

    public String getTypeAss() {
        return typeAss;
    }
    
    public static ArrayList<patients> getPatientsList() {
        ArrayList<patients> patientsList = new ArrayList<>();
        Connection conn = classes.database.getConnection();                
            String query = "select * from patients";
            Statement st;
            ResultSet rs;
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                patients pat;
                while (rs.next()) {
                    pat = new patients(rs.getString("nom"),rs.getString("prenom"),rs.getString("age"),rs.getString("portable"),rs.getString("email"),rs.getString("ville"),rs.getString("assurance"));
                    patientsList.add(pat);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        
        return patientsList;
    }
    public static void show_information_in_JTable(JTable tblinfo) {
        ArrayList<patients> list = getPatientsList();
        DefaultTableModel f = (DefaultTableModel) tblinfo.getModel();
        Object obj[] = new Object[7];
        for (int x = 0; x < list.size(); x++) {
            obj[0] = list.get(x).getNom();
            obj[1] = list.get(x).getPrenom();
            obj[2] = list.get(x).getAge();
            obj[3] = list.get(x).getPhone();
            obj[4] = list.get(x).getEmail();
            obj[5] = list.get(x).getVille();
            obj[6] = list.get(x).getTypeAss();            
            f.addRow(obj);
        }
    }
}
