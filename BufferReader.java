import java.io.*;
/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
Andrés Quan Littow       17652
Mario Andrés             18029
Josué Sagastume          18173
BufferReader.java
Proposito:
 */

/**
 * Solamente es el lector. El propostio en realida fue entender como funciona
 * este tipo de InputStream
 */
public class BufferReader {
    public static void main(String args[])throws Exception{
        FileReader fr = new FileReader("D:\\testout.txt");
        BufferedReader br = new BufferedReader(fr);

        int i;
        while((i=br.read())!=-1){
            System.out.print((char)i);
        }
        br.close();
        fr.close();
    }
}