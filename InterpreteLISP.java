/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */

// Cambios recientes: Añadí formato de genérico
public class InterpreteLISP<E>{
    //Unico atributo de la clase
    private E resultado;

    //Override del metodo de la interfaz Calculator
    //Recibe como parametros dos valores int y un string
    //Los valores int son los numero entre los cuales se realizara
    //la operacion. Y el valor string es el operador
    public E Calculate(E num1, E num2, String op){
        //Condicion para cada caso
        if (op == "+"){
            //Si es +, entonces suma
            resultado = num1 + num2;
        } else if (op == "-"){
            //Si es -, entonces resta
            resultado = num1 - num2;
        } else if (op == "*"){
            //Si es *, entonces multiplica
            resultado = num1 * num2;
        } else if (op == "/"){
            //Si es /, entonces divide
            resultado = num1/num2;
        } else {
            //En caso de que no sea ninguno
            //Entonces se le asignara un 0
            resultado = 0;
        }
        //Devuelve el resultado
        return resultado;
    }
}