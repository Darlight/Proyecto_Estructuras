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
        System.out.print("Escoja una forma de dar los datos: \n \n 1. Por un archivo. \n 2. Por expresion aqui");
        String choice = "";
        String user_input = "";
        // Carga de un archivo. Necesito ayuda haciendo que lo lea linea por linea.
        if (choice == 1) {
            System.out.println(" Usted ha escogido por archivo. \n De la direccion de su archivo, empezando por su disco duro, \n y terminando en el nombre del archivo");
            String address = s.nextString();

            try {
                FileReader fr = new FileReader(address);
                BufferedReader br = new BufferedReader(fr);

                String str;
                while ((str = br.readLine) != null) {
                    out.println(str + "\n");
                }

                br.close();

            } catch (IOException e) {
                System.out.println("No se pudo encontrar su archivo.");
            }
        }
        // Carga mediante texto solamente
        if (choice == 2) {
            while (scanner.hasNextLine()) {
                String linea = s.nextLine();

                if (linea.equals("$$") || line.equals("$")) {
                    Parser p = new Parser();
                    Evaluador e = new Evaluador();
                    try {
                        SExpression exp = p.parse(user_input);
                        System.out.println("Notacion inicial: ");
                        exp.printExpression();
                        System.out.println("Resultado: ");
                        SExpression result = evaluador.eval(exp);
                        result.printSExpression();
                    } catch (IOException e) {
                        System.out.println("ERROR");
                    }

                    if (linea.equals("$$")) {
                        System.out.println("Done");
                        System.exit(0);
                    }

                    user_input = "";

                    System.out.println("\n \n Reinicie el programa");


                }
                else{
                    user_input = user_input + linea + "\n";
                }

            }
        }

    }
}