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
        Archivos archivo = new Archivos();
        TraductorLISP traductor = new TraductorLISP();

        // Menú Principal
        System.out.println("BIENVENIDO AL INTERPRETE DE LISP\n------------------------------------");
        System.out.println("Para comenzar, por favor ingrese la direccion del archivo de texto");
        System.out.println("Empezando por su disco duro y terminando en el nombre del archivo");

        //C:\Users\josue\Desktop\U\archivo.txt
        String adress = s.nextString();

        nuevoArchivo = archivo.leerTxt(adress);
        traductor.Tokenizer(nuevoArchivo);

        

    }
}