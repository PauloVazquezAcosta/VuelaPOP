package vuelapop;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @autor Paulo Vázquez Acosta
 * @autor Adrián Carneiro Diz
 * @autor Javier Ceballos Mateo
 * @date 21/12/2021
 * @version 1.0
 */
public class PostgreSQLJDBC {

    public static void main(String args[]) {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://easybyte.club:2224/VuelaPOP",
                            "javaconnect", "conndb@Servo2021*");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

}
