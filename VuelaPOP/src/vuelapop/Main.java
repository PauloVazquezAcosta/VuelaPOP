package vuelapop;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Paulo
 * @author Adrian Carneiro
 * @author Javier Ceballos
 */
public class Main {

    static final String url = "jdbc:postgresql://easybyte.club:2224/VuelaPOP";
    static final String user = "javaconnect";
    static final String password = "conndb@Servo2021*";

    public static void main(String[] args) {
        Scanner ent = new Scanner(System.in);

        Connect connection = new Connect(url, user, password);

        int op = 0;
        do {
            op = menu(ent);
            switch (op) {
                // Método correspondiente al punto 0 del menú 0, para salir del programa
                case 0:
                    System.out.println("Gracias por utilizar el programa. Que tenga un buen dia!");
                    break;
                case 1:
                    // Método que muestra los datos de los vuelos de la aerolínea
                    connection.mostrarTabla("vuelos");
                    break;
                case 2:
                    // Método que muestra los datos de los pasajeros de los vuelos de la aerolínea
                    connection.mostrarTabla("pasajeros");
                    break;
                case 3:
                    // Método que muestra los pasajeros del vuelo que introduzcamos por código
                    String codigo;
                    do {
                        System.out.print("Introduce el codigo del vuelo: ");
                        codigo = ent.nextLine().toUpperCase();
                        // Mientras el formato del código no sea el correcto, repite la petición
                    } while (!comprobarFormatoCodigo(codigo));
                    System.out.println();
                    connection.mostrarPasajerosVuelo(codigo);
                    break;
                case 4:
                    // Método que inserta un vuelo solicitando todos los datos que se necesitan para
                    // los vuelos
                    do {
                        System.out.print("Introduce el codigo del vuelo: ");
                        codigo = ent.nextLine().toUpperCase();
                        // Mientras el formato no sea correcto o el vuelo exista ya en la base de datos,
                        // se repite la petición
                    } while (!comprobarFormatoCodigo(codigo) || connection.existeVuelo(codigo));

                    String fechaSalida;
                    do {
                        System.out.print("Introduce la fecha de salida (formato dd-MM-aaaa): ");
                        fechaSalida = ent.nextLine();
                        // Mientras la fecha no tenga formato correcto, se repite la petición
                    } while (!comprobarFecha(fechaSalida));
                    String horaSalida;
                    do {
                        System.out.print("Introduce la hora de salida (formato HH:mm): ");
                        horaSalida = ent.nextLine();
                        // Mientras la hora no tenga formato correcto, se repite la petición
                    } while (!comprobarHora(horaSalida));

                    // Creamos un String con la fecha y la hora para que tenga el formato que acepta
                    // la base de datos
                    String fechaHoraSalida = fechaSalida + " " + horaSalida;

                    System.out.print("Introduzca el destino del vuelo: ");
                    String destino = ent.nextLine().toUpperCase();

                    System.out.print("Introduzca la procedencia del vuelo: ");
                    String procedencia = ent.nextLine().toUpperCase();

                    // Creamos las variables para las plazas del avión que solicitaremos
                    // posteriormente
                    int plazasFumadores, plazasNoFumadores, plazasTurista, plazasPrimera;

                    do {
                        System.out.print("Introduzca el número de plazas de fumadores: ");
                        try {
                            plazasFumadores = ent.nextInt();
                        } catch (InputMismatchException e) {
                            plazasFumadores = Integer.MIN_VALUE;
                        }
                        // Repetimos la petición mientras introduzcan un valor menor a 0
                    } while (plazasFumadores < 0);

                    do {
                        System.out.print("Introduzca el número de plazas de no fumadores: ");
                        try {
                            plazasNoFumadores = ent.nextInt();
                        } catch (InputMismatchException e) {
                            plazasNoFumadores = Integer.MIN_VALUE;
                        }
                        // Repetimos la petición mientras introduzcan un valor menor a 0
                    } while (plazasNoFumadores < 0);

                    do {
                        System.out.print("Introduzca el número de plazas de clase Turista: ");
                        try {
                            plazasTurista = ent.nextInt();
                        } catch (InputMismatchException e) {
                            plazasTurista = Integer.MIN_VALUE;
                        }
                        // Repetimos la petición mientras introduzcan un valor menor a 0
                    } while (plazasTurista < 0);

                    do {
                        System.out.print("Introduzca el número de plazas de clase Primera: ");
                        try {
                            plazasPrimera = ent.nextInt();
                        } catch (InputMismatchException e) {
                            plazasPrimera = Integer.MIN_VALUE;
                        }
                        // Repetimos la petición mientras introduzcan un valor menor a 0
                    } while (plazasPrimera < 0);

                    System.out.println();

                    // Llamamos al método para insertar el vuelo y le enviamos los datos solicitados
                    // por teclado al usuario
                    connection.insertarVuelo(codigo, fechaHoraSalida, destino, procedencia, plazasFumadores,
                            plazasNoFumadores, plazasTurista, plazasPrimera);
                    break;
                case 5:
                    // Solicitamos el código del vuelo a eliminar y repetimos mientras introduzca un
                    // formato no válido
                    do {
                        System.out.println("Introduce el codigo del vuelo: ");
                        codigo = ent.nextLine().toUpperCase();
                    } while (!comprobarFormatoCodigo(codigo));
                    connection.borrarVuelo(codigo);
                    break;
                case 6:
                    // Llamamos al método que modificará los vuelos de no fumadores a no fumadores
                    connection.modificarVuelo();
                    break;
            }
        } while (op != 0);
        ent.close();
    }

    /**
     * Menú que muestra las opciones con las que el usuario puede interactuar
     * 
     * @param ent Enviamos el Scanner que servirá para recibir los datos del teclado
     * @return el valor de la opción
     */
    static int menu(Scanner ent) {
        int op = 0;
        do {
            System.out.println("----------------------Menu----------------------\n" +
                    "0-Salir del programa \n" +
                    "1-Mostrar información general\n" +
                    "2-Mostrar información de los pasajeros\n" +
                    "3-Ver los pasajeros de un vuelo\n" +
                    "4-Insertar nuevo vuelo\n" +
                    "5-Borrar vuelo introducido previamente\n" +
                    "6-Convertir vuelos de fumadores a no fumadores\n" +
                    "------------------------------------------------");
            System.out.println();
            System.out.print("Introduzca la opción: ");
            op = ent.nextInt();
            ent.nextLine();
        } while (op < 0 || op > 6);
        return op;
    }

    /**
     * Método que valida el formato del código de vuelo y devuelve si es válido o no
     * 
     * @param codigo Código de vuelo a validar
     * @return Devolverá un boolean que indicará si el formato es válido o no
     */
    static boolean comprobarFormatoCodigo(String codigo) {
        Boolean formato = true;
        String param[] = codigo.split("-");
        try {
            if (param[0].length() == 2 && param[1].length() == 2) {
                for (int i = 0; i < param[0].length(); i++) {
                    if (!Character.isLetter(param[0].charAt(i))) {
                        formato = false;
                    }
                }
                if (!Character.isLetter(param[1].charAt(0))) {
                    formato = false;
                }
                for (int i = 0; i < param[2].length(); i++) {
                    if (Character.isLetter(param[2].charAt(i))) {
                        formato = false;
                    }
                }
            } else {
                formato = false;
            }
            if (!formato)
                System.out.println("El formato introducido no es correcto. Formato correcto: XX-XX-1234");
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            System.out.println("El formato introducido no es correcto. Formato correcto: XX-XX-1234");
            formato = false;
        }
        return formato;
    }

    /**
     * Método que valida el formato de la fecha del vuelo y devuelve si es válido o
     * no
     * 
     * @param fecha Fecha a validar
     * @return Devolverá un boolean que indicará si el formato es válido o no
     */
    static boolean comprobarFecha(String fecha) {
        Boolean formato = true;

        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.setLenient(false);
            formatter.parse(fecha);
            formato = true;
        } catch (ParseException e) {
            System.out.println("El formato introducido no es correcto. Formato correcto: dd-MM-yyyy");
            formato = false;
        }
        return formato;
    }

    /**
     * Método que valida el formato de la hora del vuelo y devuelve si es válido o
     * no
     * 
     * @param horaString Hora a validar
     * @return Devolverá un boolean que indicará si el formato es válido o no
     */
    static boolean comprobarHora(String horaString) {
        Boolean formato = true;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        try {
            LocalTime hora = LocalTime.parse(horaString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("El formato introducido no es correcto. Formato correcto: H:mm");
            formato = false;
        }

        return formato;
    }
}
