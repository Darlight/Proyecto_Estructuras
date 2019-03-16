// TODO = SEARCH BUFFER READER - LIST - STRING
/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */
import java.util.Scanner;

/**
 * Clase main, introducción al programa.
 *
 * Andrés Quan Littow       17652
 * Mario Andrés             18029
 * Josué Sagastume          18173
 */

public class MainLISP{
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Archivos archivo = new Archivos();

        // Menú Principal
        System.out.println("BIENVENIDO AL INTERPRETE DE LISP\n------------------------------------");
        System.out.println("Para comenzar, por favor ingrese la direccion del archivo de texto");
        System.out.println("Empezando por su disco duro y terminando en el nombre del archivo");

        //C:\Users\josue\Desktop\U\archivo.txt
        String adress = s.nextLine();

        //Variable en la que se almacenara el texto leido
        String texto = "";

        //En caso de que no exista el archivo en esa direccion
        //Entonces se utiliza un try
        while (s.hasNextLine()) {
            // Read input until a '$' occurs
            String line = s.nextLine();
            // Parse the current input SExp
            Parser parser = new Parser();
            Evaluador evaluador = new Evaluador();
            try {
                SExpression SExp = parser.parse(texto);
                System.out.println("Notacion de puntos: ");
                SExp.printSExpression();
                System.out.println("Resultado de la evaluacion: ");
                SExpression resultSExp = evaluador.eval(SExp);
                resultSExp.printSExpression();
            } catch (exceptionError c) {
                c.printErrorMessage();
            }
        }
    }
}