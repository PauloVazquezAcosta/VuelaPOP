package vuelapop;

import java.sql.*;

public class Connect {

    private String url, user, password;
    private Connection connection;

    Connect(String url, String user, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion realizada!");
            this.connection = connection;
            this.url = url;
            this.user = user;
            this.password = password;
        } catch (SQLException e) {
            System.out.println("Conexion fallida.");
            e.printStackTrace();
        }
    }

    public void mostrarTabla(String tabla) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from " + tabla);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1)
                        System.out.println();
                    String columnValue = resultSet.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println("\n");
            }
        } catch (SQLException e) {
            System.out.println("Conexion fallida.");
            e.printStackTrace();
        }
    }

    public void mostrarPasajerosVuelo(String codigo) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from pasajeros where codigo_vuelo='" + codigo + "'");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            if (resultSet.next() == false) {
                System.out.println("El codigo introducido no corresponde con ningún vuelo.");
            } else {

                while (resultSet.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1)
                            System.out.println();
                        String columnValue = resultSet.getString(i);
                        System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                    }
                    System.out.println("\n");
                }
            }
        } catch (SQLException e) {
            System.out.println("Conexion fallida.");
            e.printStackTrace();
        }
    }

    public boolean existeVuelo(String codigo) {
        Boolean existeVuelo = false;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from vuelos where codigo='" + codigo + "'");
            if (resultSet.next() == true) {
                System.out.println("El vuelo ya existe en la base de datos.");
                existeVuelo = true;
            }
        } catch (SQLException e) {
            System.out.println("Conexion fallida.");
            e.printStackTrace();
        }

        return existeVuelo;
    }

    public void insertarVuelo(String codigo, String fechaHoraSalida, String destino, String procedencia,
            int plazasFumadores, int plazasNoFumadores, int plazasTurista, int plazasPrimera) {

        System.out.println("INSERT INTO vuelos VALUES ('" + codigo + "', '" + fechaHoraSalida + "', '"
                + destino + "', '" + procedencia + "', " + plazasFumadores + ", " + plazasNoFumadores + ", "
                + plazasTurista + ", " + plazasPrimera + ")");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("INSERT INTO vuelos VALUES ('" + codigo + "', '" + fechaHoraSalida + "', '"
                            + destino + "', '" + procedencia + "', " + plazasFumadores + ", " + plazasNoFumadores + ", "
                            + plazasTurista + ", " + plazasPrimera + ")");
            ResultSetMetaData rsmd = resultSet.getMetaData();
        } catch (SQLException e) {
        }
    }

    public void borrarVuelo(String codigo) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("DELETE from vuelos where codigo='" + codigo + "'");
            ResultSetMetaData rsmd = resultSet.getMetaData();
        } catch (SQLException e) {
        }
    }

    public void modificarVuelo() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "UPDATE vuelos SET plazas_fumadores = plazas_no_fumadores;UPDATE vuelos SET plazas_no_fumadores = 0");
        } catch (SQLException e) {

        }
    }
}
