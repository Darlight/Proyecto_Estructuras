import java.util.*;
/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */

public class SExpression {

    private int nodeType = 0; // Que tipo de nodo sera creado
    private int value = 0; // Valor de atomos de enteros
    private boolean isNil = false; // Null de LISP
    private String name = ""; // Valor de atomos simbolicos
    private SExpression car, cdr; // Valores de ningun atom

    // Hashtable que contenga funciones y valores
    // En este caso se uso un map tipo Hashtable para poder determina diferentes tipos
    //de datos que se identificaran al momento de leer el archivo .txt
    public static HashMap<String,SExpression> idValores = new HashMap<String,SExpression>();
    //Getters y Setters

    public int getNodeType(){
        return this.nodeType;
    }
    public int getValue(){
        return this.value;
    }
    public String getName(){
        return this.name;
    }
    public boolean getIsNil(){
        return this.isNil;
    }

    public void setValue(int newvalue){
        this.value = newvalue;
    }
    public void setName(String newname){
        this.name = newname;
    }
    public void setIsNil(boolean newboolean){
        this.isNil = newboolean;
    }

    int this.nodeType =0; // Que tipo de nodo sera creado
    int this.value=0; // Valor de atomos de enteros
    boolean this.isNIL =false; // Null de LISP
    String this.name =""; // Valor de atomos simbolicos
    // Hashtable que contenga funciones y valores
    // En este caso se uso un map tipo Hashtable para poder determina diferentes tipos
    //de datos que se identificaran al momento de leer el archivo .txt
    public static HashMap<String, SExp> idValores = new HashMap<String, SExp>();

    public static SExpression getFromTable(String Id){
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
    public SExpression(int number){
        this.nodeType = 1;
        this.value = number;
    }

    //  Crea S-Expressiones de tipo string
    public SExpression(String str){
        this.nodeType = 2;
        this.name = str;
    }
    // S-Expression de ningun tipo de atom, solo reportan el valor inicial
    // de una list --> car
    // todos los valores exceptuando el primer elemento en una list --> cdr
    public SExpression(SExpression car, SExpression cdr){
        this.nodeType = 3;
        this.car = car;
        this.cdr = cdr;
    }
    //Recibe como parametros dos expresiones S las funciones basicas de aritmetica
    public static SExpression suma(SExpression SExp1, SExpression SExp2){
        int valor = SExp1.getValue() + SExp2.getValue();
        return new SExpression(valor);
    }

    public static SExpression resta(SExpression SExp1, SExpression SExp2){
        int valor = SExp1.getValue() - SExp2.getValue();
        return new SExpression(valor);
    }

    public static SExpression multiplicar(SExpression SExp1, SExpression SExp2){
        int valor = SExp1.getValue() * SExp2.getValue();
        return new SExpression(valor);
    }

    public static SExpression dividir(SExpression SExp1, SExpression SExp2){
        int valor = SExp1.getValue() / SExp2.getValue();
        return new SExpression(valor);
    }

    public static SExpression modulo(SExpression SExp1, SExpression SExp2){
        int valor = SExp1.getValue() % SExp2.getValue();
        return new SExpression(valor);
    }
    // Comparadores verificando sus valores
    public static SExpression less(SExpression SExp1, SExpression SExp2){
        if(SExp1.getValue() < SExp2.getValue())
            return getFromTable("T");
        else
            return getFromTable("NIL");

    public SExpression(int number) {
        this.type = 1;
        this.val = number;
    }

    //  Crea S-Expressiones de tipo string
    public SExpression(String str) {
        this.type = 2;
        this.name = str;
    }

    //Recibe como parametros dos expresiones S las funciones basicas de aritmetica
    public static SExpression suma(SExpression SExp1, SExpression SExp2) {
        int addition = SExp1.val + SExp2.val;
        return new SExpression(addition);
    }

    public static SExpression resta(SExpression SExp1, SExpression SExp2) {
        int subtraction = SExp1.val - SExp2.val;
        return new SExpression(subtraction);
    }

    public static SExpression multiplicar(SExpression SExp1, SExpression SExp2) {
        int multiplication = SExp1.val * SExp2.val;
        return new SExpression(multiplication);
    }

    public static SExpression dividir(SExpression SExp1, SExpression SExp2) {
        int quotient = SExp1.val / SExp2.val;
        return new SExpression(quotient);
    }

    public static SExpression modulo(SExpression SExp1, SExpression SExp2) {
        int rem = SExp1.val % SExp2.val;
        return new SExpression(rem);
    }

    public static SExpression greater(SExpression SExp1, SExpression SExp2){
        if(SExp1.getValue() > SExp2.getValue())
            return getFromTable("T");
        else
            return getFromTable("NIL");
    }

    public static SExpression igual(SExpression SExp1, SExpression SExp2){
        if(SExp1.getNodeType() != SExp2.getNodeType())
            return getFromTable("NIL");
        else{
            if(SExp1.getNodeType() == 1 && SExp1.getValue() == SExp2.getValue())
                return getFromTable("T");
            else if(SExp1.getValue() == 2 && SExp1.getName() == SExp2.getName())
                return getFromTable("T");
            else
                return getFromTable("NIL");
        }

    }

}
