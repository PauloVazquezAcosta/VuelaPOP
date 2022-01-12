package vuelapop;

import java.sql.*;

public class Connect {

    private String url, user, password;
    private Connection connection;

    /**
     * Método constructor que crea la conexión a la base de datos con los datos de
     * autenticación
     * 
     * @param url      URL a la que se conecte el usuario, la del servidor de la
     *                 base de datos
     * @param user     Usuario de conexión a la base de datos
     * @param password Contraseña del usuario
     */
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

    /**
     * Método qaue mostrará la tabla de la base de datos que se le envíe por
     * parámetro
     * 
     * @param tabla Nombre de la tabla que se quiere visualizar
     */
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

    /**
     * Método que consulta los pasajeros de un vuelo determinado e introducido por
     * código
     * 
     * @param codigo Código del vuelo a consultar en la base de datos
     */
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

    /**
     * Método que comprueba si el vuelo existe en la base de datos para evitar
     * introducir uno existente y que no haya conflictos con las claves primarias
     * 
     * @param codigo Código del vuelo a consultar en la base de datos
     * @return Devuelve si el vuelo existe o no en la base de datos
     */
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

    /**
     * Método que insertará el vuelo en la base de datos con la información que
     * reciba del usuario por teclado
     * 
     * @param codigo            Código de vuelo
     * @param fechaHoraSalida   String de la concatenación de fecha y hora de salida
     *                          del vuelo
     * @param destino           Ciudad de destino del vuelo
     * @param procedencia       Ciudad de procedencia del vuelo
     * @param plazasFumadores   Número de plazas del avión de fumadores
     * @param plazasNoFumadores Número de plazas del avión no fumadores
     * @param plazasTurista     Número de plazas del avión de clase turista
     * @param plazasPrimera     Número de plazas del avión de clase primera
     */
    public void insertarVuelo(String codigo, String fechaHoraSalida, String destino, String procedencia,
            int plazasFumadores, int plazasNoFumadores, int plazasTurista, int plazasPrimera) {

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

    /**
     * Método que borra de la base de datosa el vuelo cuyo código se introduce por
     * teclado
     * 
     * @param codigo Código del vuelo a borrar
     */
    public void borrarVuelo(String codigo) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("DELETE from vuelos where codigo='" + codigo + "'");
            ResultSetMetaData rsmd = resultSet.getMetaData();
        } catch (SQLException e) {
        }
    }

    /**
     * Método que modifica las plazas de pasajeros a fumadores y deja las de no
     * fumadores a 0
     */
    public void modificarVuelo() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "UPDATE vuelos SET plazas_fumadores = plazas_no_fumadores;UPDATE vuelos SET plazas_no_fumadores = 0");
        } catch (SQLException e) {

        }
    }
}
