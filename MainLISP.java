public class MainLISP {
import java.util.Scanner;
import java.io .*;
import static java.Lang.System .*;

// TODO = SEARCH BUFFER READER - LIST - STRING
/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        // Menú Principal
        System.out.println("BIENVENIDO AL INTERPRETE DE LISP\n------------------------------------");
        System.out.println("Para comenzar, por favor ingrese la direccion del archivo de texto");
        System.out.println("Empezando por su disco duro y terminando en el nombre del archivo");
        System.out.print("Escoja una forma de dar los datos: \n \n 1. Por un archivo. \n 2. Por expresion aqui");

        String adress = s.nextString();
        try{
            FileReader fr = new FileReader(adress);
            BufferedReader br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine) != null){
                System.out.println(str + "\n");
            }
            br.close();

        } catch (IOException e){
            System.out.println("No se puedo encontrar su archivo\nAsegurese de dar bien la direccion\nY que este se encuentre donde usted cree que esta");
        }


    }
}