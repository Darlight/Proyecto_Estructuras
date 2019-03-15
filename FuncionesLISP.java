/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
FuncionesLISP.java
Proposito:
 */

import java.util.HashMap;
import java.util.ArrayList;

public class Funciones{

    String nombreFuncion;
    ArrayList<String> parametros;
    SExpression cuerpoFuncion;

    //Constructor
    public Funciones(String nombre, SExpression parametro, SExpression cuerpo) throws customException{
        nombreFuncion = nombre;
        parametros = parametro;
        cuerpoFuncion = cuerpo;
    }

    //Este metodo coniverte una lista SExpression a una lista de parametros
    public static ArrayList<String> getListaParametros(SExpression parametro) throws customException {
        //Crea una variable ArrayList para ingresar los parametros
        ArrayList<String> parametros = new ArrayList<String>();

        //Mientras los parametros no sean null
        while(!parametros.isNil()){

            SExpressions car = parametros.car();

            //Los parametros formales de la lista no pueden ser enteros
            if (car.isInteger()){
                //Si es entero, manda una excepcion
                throw new customException("Los parametros para la funcion deben ser atomos simbolicos **", "Evaluacion");
            }
            //Se agrega a los parametros el nombre de car
            parametros.add(car.nombre);
            parametro = parametro.cdr();
        }
        //Retorna el ArrayList de los parametros
        return parametros;

    }

}