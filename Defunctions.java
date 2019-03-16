/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
Andrés Quan Littow       17652
Mario Andrés             18029
Josué Sagastume          18173
Defunctions.java
Proposito:
 */
import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;

/**
 * Clase que utiliza la implementación de un Hashmap para poder participar en
 * el procesamiento de daots. Almacena la función definida por el usuario como objetos. Tiene nombre,
 * Parámetros formales como una lista y cuerpo de la función como S-Expresión.
 */
public class Defunctions{
    // Un hashmap que contenga las funciones del usuario
    //Key es el nombre y defun es una funcion con parametros

    public static HashMap <String,FuncionesLISP> defunctions = new HashMap<>();

    /**
     * Agrega una función en el Hashmpa con el key y sus funciones
     *
     * @param functionName Nombre de la funcion
     * @param function Lo que el usuario programa para tal funcion
     */
    public static void addFunction(String functionName, FuncionesLISP function){
        defunctions.put(functionName, function);
    }

    /**
     * Retorna una funcion
     *
     * @param functionName Nombre de la funcion
     * @return
     * @throws exceptionError En caso de que no se acepta valores invalidos
     */
    public static FuncionesLISP getFunction(String functionName) throws exceptionError{
        FuncionesLISP defun = null;
        // Chequea si existe alguna key
        if(defunctions.containsKey(functionName))
            defun = defunctions.get(functionName);

        return defun;
    }

}