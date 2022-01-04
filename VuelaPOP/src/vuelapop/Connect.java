package vuelapop;

import java.sql.*;

public class Connect {

    private String url,user,password;
    private Connection connection;

    Connect(String url,String user,String password){
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion realizada!");
            this.connection=connection;
            this.url=url;
            this.user=user;
            this.password=password;
        }catch (SQLException e) {
            System.out.println("Conexion fallida.");
            e.printStackTrace();
        }
    }

    public void mostrarTabla(String tabla){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from " + tabla);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(", ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println("");
            }
        }catch (SQLException e) {
            System.out.println("Conexion fallida.");
            e.printStackTrace();
        }
    }
}
