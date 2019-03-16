/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Clase que utiliza la implementación de un Hashmap para poder participar en
 * el procesamiento de daots
 */
public class Defunctions{
    // Un hashmap que contenga las funciones del usuario
    //Key es el nombre y defun es una funcion con parametros

    public static HashMap <String,FuncionesLISP> defunctions = new HashMap<>();

    /**
     * Agrega una función en el Hashmpa con el key y sus funciones
     *
     * @param functionName
     * @param function
     */
    public static void addFunction(String functionName, FuncionesLISP function){
        defunctions.put(functionName, function);
    }

    /**
     * Retorna una funcion
     *
     * @param functionName
     * @return
     * @throws exceptionError
     */
    public static FuncionesLISP getFunction(String functionName) throws exceptionError{
        FuncionesLISP defun = null;
        // Chequea si existe alguna key
        if(defunctions.containsKey(functionName))
            defun = defunctions.get(functionName);

        return defun;
    }

}