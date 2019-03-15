public class MainLISP {
import java.util.Scanner;
import java.io.*;
import static java.Lang.System.*;

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
        System.out.print("Escoja una forma de dar los datos: \n \n 1. Por un archivo. \n 2. Por expresion aqui");
        String choice = "";
        // Carga de un archivo. Necesito ayuda haciendo que lo lea linea por linea.
        if(choice == 1){
            System.out.println(" Usted ha escogido por archivo. \n De la direccion de su archivo, empezando por su disco duro, \n y terminando en el nombre del archivo");
            String address = s.nextString();

            try{
                FileReader fr = new FileReader(address);
                BufferedReader br = new BufferedReader(fr);

                String str;
                while((str = br.readLine) != null){
                    out.println(str + "\n");
                }

                br.close();

            }
            catch(IOException e){
                System.out.println("No se pudo encontrar su archivo.");
            }
        }
        // Carga mediante texto solamente
        if(choice == 2){
            while(scanner.hasNextLine()){
                String linea = s.nextLine();

                if(linea.equals("$$") || line.equals("$")){
                    Parser p = new Parser();
                    Evaluador e = new Evaluador();

                    
                }
            }
        }

        }
    }