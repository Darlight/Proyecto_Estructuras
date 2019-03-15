import java.io.*;

public class Archivos {

    //Metodo encargado de leer archivos de texto
    //Recibe como parametro la direccion en la que se encuentra
    //el archivo que se desea leer
    //Y nos devuelve el texto del archivo como un String
    public String leerTxt(String direccion){

        //Variable en la que se almacenara el texto leido
        String texto = "";

        //En caso de que no exista el archivo en esa direccion
        //Entonces se utiliza un try
        try{
            //Sinceramente, este pedazo de codigo fue visto en internet, pues
            //no se sabia como leer un archivo de texto, pero FUNCIONA
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String temporal = "";
            String bfRead;

            //Hace el ciclo mientras bfRead contiene datos
            while ((bfRead = bf.readLine()) != null){
                //
                temporal = temporal + bfRead;
            }

            texto = temporal;

        }catch(Exception e){
            //Si la direccion es incorrecta, entonces mostrara este mensaje
            System.err.println("No se encontro archivo");
        }
        //Devuelve el archivo leido o el mensaje de error
        return texto;
    }
}