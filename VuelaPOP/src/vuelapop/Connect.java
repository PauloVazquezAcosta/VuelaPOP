package vuelapop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private String url,user,password;
    Connect(String url,String user,String password){
        this.url=url;
        this.user=user;
        this.password=password;
    }

    public void connect(){
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexion realizada!");
        }catch (SQLException e) {
            System.out.println("Conexion fallida.");
            e.printStackTrace();
        }
    }
}
