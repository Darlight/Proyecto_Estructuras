public class MainLISP{
import java.util.Scanner

// TODO = SEARCH BUFFER READER - LIST - STRING


    public static void main(String[] args){
        MainLisp tipo_1, tipo_2 = new MainLISP();
        Scanner s = new Scanner(System.in);
        int choice = 3;
        String operation_name = "0";
        boolean status = false;
        System.out.println("Que tipo de archivo esta tratando de leer? \n \n 1. Archivo .txt \n String");
        choice = s.nextInt();
        if(!(choice = 3)){
            status = true;
            try{
                while(status = true){
                    if(choice == 1){
                        System.out.println("Ha escogido el leer un archivo. Por favor, indique (Utilizando un path) donde esta el archivo: \n");
                        String path = s.nextString();
                        try{
                            tipo_1(path);
                        }
                        catch (InvalidFileType e){
                            System.out.println("El archivo no es valido.");
                        }
                        finally{
                            System.out.println("Cerrando programa.");
                            status = false;
                            exit();
                        }
                    }
                    if(choice == 2){
                        System.out.println("Ha escogido el escribit un string. Escribalo a continuacion: \n\n");
                        String operation_name = s.nextString();
                        tipo_2(operation_name);
                    }

                }

            }
            catch(InvalidChoice e){
                System.out.println("Opcion invalida.");
            }
            finally{
                exit();
            }
        }
    }


    public void tipo_1(String path){
    // Insert buffered file here

    }

    public void tipo_2(String input){
    //Insert String reader here
    }
}