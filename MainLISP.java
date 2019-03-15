// TODO = SEARCH BUFFER READER - LIST - STRING
/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */
import java.util.Scanner;
import java.io .*;
import static java.Lang.System .*;
public class MainLISP{
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
        //traductor.Tokenizer(nuevoArchivo);

        //Variable en la que se almacenara el texto leido
        String texto = "";

        //En caso de que no exista el archivo en esa direccion
        //Entonces se utiliza un try
        while (scanner.hasNextLine()) {
            // Read input until a '$' occurs
            String line = scanner.nextLine();
            // Parse the current input SExp
            Parser parser = new Parser();
            Evaluador evaluador = new Evaluador();
            try {
                SExpression SExp = parser.parse(input);
                System.out.println("Notacion de puntos: ");
                SExp.printSExpression();
                System.out.println("Resultado de la evaluacion: ");
                SExpression resultSExp = evaluator.eval(SExp);
                resultSExp.printSExpression();
            } catch (customException c) {
                c.printErrorMessage();
            }
        }
    }
}