/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuelapop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Paulo
 * @author Adrian Carneiro 
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://easybyte.club:2224/VuelaPOP", "acarneiro", "acarneiro@Servo2021*")) {
            System.out.println("Conexion realizada!");
        }catch (SQLException e) {
            System.out.println("Conexion fallida.");
            e.printStackTrace();
        }
    }
    
}
