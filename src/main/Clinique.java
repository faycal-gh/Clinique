/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import classes.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Administrateur
 */
public class Clinique {

    /**
     * @param args the command line arguments
     */
    public static int[] insertX(int arr[], int x, int pos) {
        int i;
        int n = arr.length;
        int newarr[] = new int[n + 1];
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
    
    public static void main(String[] args) {
        // TODO code application logic here  
//        String imgpath = "C:\\Users\\HALIM\\Desktop\\70462147_1286646698184323_5184646853420908544_n.jpg";
//        String columnNames[] = {"name", "img"};
//        try {
//            database.insertToDataBase("imagetest", columnNames, "Ghali Faiçal", new FileInputStream(new File(imgpath)));
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Clinique.class.getName()).log(Level.SEVERE, null, ex);
//        }

        int arr[] = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(insertX(arr, 30, 3)));
    }
}
