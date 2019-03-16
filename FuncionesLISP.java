/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
FuncionesLISP.java
Proposito:
 */

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Clase básica para la implementación de las funciones LISP
 */
public class FuncionesLISP{

    String nombreFuncion; // Nombre de una función
    ArrayList<String> parametros; // Parámetros en una ArrayList
    SExpression cuerpoFuncion; // Cuerpo de una función

    //Constructor
    public FuncionesLISP(String nombre, SExpression parametro, SExpression cuerpo) throws exceptionError{
        nombreFuncion = nombre;
        parametros = getListaParametros(parametro);
        cuerpoFuncion = cuerpo;
    }



    /**
     * Este metodo coniverte una lista SExpression a una lista de parametros
     * @param parametro
     * @return
     * @throws exceptionError
     */
    public static ArrayList<String> getListaParametros(SExpression parametro) throws exceptionError {
        //Crea una variable ArrayList para ingresar los parametros
        ArrayList<String> parametros = new ArrayList<String>();

        //Mientras los parametros no sean null
        while(!parametro.getIsNil()){

            SExpression car = parametro.car();

            //Los parametros formales de la lista no pueden ser enteros
            if (car.isInteger()){
                //Si es entero, manda una excepcion
                throw new exceptionError("Los parametros para la funcion deben ser atomos simbolicos **", "Evaluacion");
            }
            //Se agrega a los parametros el nombre de car
            parametros.add(car.nombre);
            parametro = parametro.cdr();
        }
        //Retorna el ArrayList de los parametros
        return parametros;

    }

}