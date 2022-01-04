package vuelapop;

import java.util.Scanner;

/**
 *
 * @author Paulo
 * @author Adrian Carneiro 
 */
public class Main {

    static final String url = "jdbc:postgresql://easybyte.club:2224/VuelaPOP";
    static final String user = "acarneiro";
    static final String password = "acarneiro@Servo2021*";

    public static void main(String[] args) {
        Scanner ent = new Scanner(System.in);

        Connect connection = new Connect(url,user,password);

        int op=0;
        do {
            op=menu(ent);
            switch (op) {
                case 0:
                    System.out.println("Gracias por utilizar el programa. Que tenga un buen dia!");
                    break;
                case 1:
                    connection.mostrarTabla("vuelos");
                    break;
                case 2:
                    connection.mostrarTabla("pasajeros");
                    break;
                case 3:
                    String codigo;
                    do {
                        System.out.println("Introduce el codigo del vuelo: ");
                        codigo = ent.nextLine().toUpperCase();
                    }while(!comprobarFormato(codigo));
                    connection.mostrarPasajerosVuelo(codigo);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }
        }while(op!=0);
        ent.close();
    }

    static int menu(Scanner ent){
        int op=0;
        do{
            System.out.println("----------------------Menu----------------------\n"+ 
            "0-Salir del programa \n"+ 
            "1-Mostrar información general\n"+ 
            "2-Mostrar información de los pasajeros\n"+ 
            "3-Ver los pasajeros de un vuelo\n"+ 
            "4-Insertar nuevo vuelo\n"+
            "5-Borrar vuelo introducido previamente\n"+ 
            "6-Convertir vuelos de fumadores a no fumadores\n"+ 
            "------------------------------------------------");
            op=ent.nextInt();
            ent.nextLine();
        }while(op<0||op>6);
        return op;
    }


    static boolean comprobarFormato(String codigo){
        Boolean formato = true;
        String param[] = codigo.split("-");
        try{
            if(param[0].length()==2 && param[1].length()==2){
                for(int i = 0;i<param[0].length();i++){
                    if(!Character.isLetter(param[0].charAt(i))){
                        formato=false;
                    }
                }
                if(!Character.isLetter(param[1].charAt(0))){
                    formato=false;
                }
                for(int i = 0;i<param[2].length();i++){
                    if(Character.isLetter(param[2].charAt(i))){
                        formato=false;
                    }
                }
            }else{
                formato=false;
            }
            if(!formato) System.out.println("El formato introducido no es correcto. Formato correcto: XX-XX-1234");
        }catch (ArrayIndexOutOfBoundsException aiobe){
            System.out.println("El formato introducido no es correcto. Formato correcto: XX-XX-1234");
            formato=false;
        }
        return formato;
    }
}
