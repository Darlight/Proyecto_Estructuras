import java.util.*;
/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */

public class SExpression {


    private boolean es_nil = false; // Null de LISP
    private SExpression c1, c2; // Valores de ningun atom

    // Hashtable que contenga funciones y valores
    // En este caso se uso un map tipo Hashtable para poder determina diferentes tipos
    //de datos que se identificaran al momento de leer el archivo .txt
    public static HashMap<String,SExpression> idValores = new HashMap<String,SExpression>();
    //Getters y Setters

    public int getNodeType(){
        return tipo_de_nodo;
    }
    public int getValue(){
        return valor;
    }
    public String getName(){
        return nombre;
    }
    public boolean getIsNil(){
        return es_nil;
    }

    public void setValue(int newvalor){
        valor = newvalor;
    }
    public void setName(String newnombre){
        nombre = newnombre;
    }
    public void setIsNil(boolean newboolean){
        es_nil = newboolean;
    }

    int tipo_de_nodo =0; // Que tipo de nodo sera creado
    int valor=0; // Valor de atomos de enteros
    boolean isNIL =false; // Null de LISP
    String nombre = ""; // Valor de atomos simbolicos
    int tipo = 0;
    // Hashtable que contenga funciones y valores
    // En este caso se uso un map tipo Hashtable para poder determina diferentes tipos
    //de datos que se identificaran al momento de leer el archivo .txt

    public static SExpression getTable(String Id){
        if(idValores.containsKey(Id)){
            return idValores.get(Id);
        }
        else{
            SExpression SExp = new SExpression(Id);
            idValores.put(Id, SExp);
            return SExp;
        }
    }
    // Crea S-Expressiones de tipo entero
    public SExpression(int numero){
        tipo_de_nodo = 1;
        valor = numero;
    }

    //  Crea S-Expressiones de tipo string
    public SExpression(String string_dependiente){
        tipo_de_nodo = 2;
        nombre = string_dependiente;
    }
    // S-Expression de ningun tipo de atom, solo reportan el valor inicial
    // de una list --> c1
    // todos los valores exceptuando el primer elemento en una list --> c2
    public SExpression(SExpression c1, SExpression c2){
        tipo_de_nodo = 3;
        c1 = c1;
        c2 = c2;
    }
    //Recibe como parametros dos expresiones S las funciones basicas de aritmetica
    public static SExpression suma(SExpression exp1, SExpression exp2){
        int valor = exp1.getValue() + exp2.getValue();
        return new SExpression(valor);
    }

    public static SExpression resta(SExpression exp1, SExpression exp2){
        int valor = exp1.getValue() - exp2.getValue();
        return new SExpression(valor);
    }

    public static SExpression multiplicar(SExpression exp1, SExpression exp2){
        int valor = exp1.getValue() * exp2.getValue();
        return new SExpression(valor);
    }

    public static SExpression dividir(SExpression exp1, SExpression exp2){
        int valor = exp1.getValue() / exp2.getValue();
        return new SExpression(valor);
    }

    public static SExpression modulo(SExpression exp1, SExpression exp2){
        int valor = exp1.getValue() % exp2.getValue();
        return new SExpression(valor);
    }
    // Comparadores verificando sus valores
    public static SExpression less(SExpression exp1, SExpression exp2) {
        if (exp1.getValue() < exp2.getValue())
            return getTable("T");
        else
            return getTable("NIL");
    }


    //  Crea S-Expressiones de tipo string


    //Recibe como parametros dos expresiones S las funciones basicas de aritmetica


    public static SExpression greater(SExpression exp1, SExpression exp2){
        if(exp1.getValue() > exp2.getValue())
            return getTable("T");
        else
            return getTable("NIL");
    }

    public static SExpression igual(SExpression exp1, SExpression exp2){
        if(exp1.getNodeType() != exp2.getNodeType())
            return getTable("NIL");
        else{
            if(exp1.getNodeType() == 1 && exp1.getValue() == exp2.getValue())
                return getTable("T");
            else if(exp1.getValue() == 2 && exp1.getName() == exp2.getName())
                return getTable("T");
            else
                return getTable("NIL");
        }

    }

    public SExpression c1(){
        return c1;
    }

    public SExpression c2(){
        return c2;
    }

    public boolean es_nil(){
        return tipo == 2 && nombre.equals("NLL");
    }

    public boolean isT(){
        return tipo == 2 && nombre.equals("T");
    }

    public boolean isAtom() {
        return tipo != 3;
    }

    public boolean isInteger(){
        return tipo == 1;
    }

    public boolean isSymbol(){
        return tipo == 2;
    }

    public static SExpression cons(SExpression c1, SExpression c2){
        return new SExpression(c1, c2);
    }
}
