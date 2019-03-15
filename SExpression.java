import java.util.*;
/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */

public class SExpression {

    private int tipo_de_nodo = 0; // Que tipo de nodo sera creado
    private int valor = 0; // Valor de atomos de enteros
    private boolean es_nil = false; // Null de LISP
    private String nombre = ""; // Valor de atomos simbolicos
    private SExpression c1, c2; // Valores de ningun atom

    // Hashtable que contenga funciones y valores
    // En este caso se uso un map tipo Hashtable para poder determina diferentes tipos
    //de datos que se identificaran al momento de leer el archivo .txt
    public static HashMap<String,SExpression> idValores = new HashMap<String,SExpression>();
    //Getters y Setters

    public int getNodeType(){
        return this.tipo_de_nodo;
    }
    public int getValue(){
        return this.valor;
    }
    public String getName(){
        return this.nombre;
    }
    public boolean getIsNil(){
        return this.es_nil;
    }

    public void setValue(int newvalor){
        this.valor = newvalor;
    }
    public void setName(String newnombre){
        this.nombre = newnombre;
    }
    public void setIsNil(boolean newboolean){
        this.es_nil = newboolean;
    }

    int this.tipo_de_nodo =0; // Que tipo de nodo sera creado
    int this.valor=0; // Valor de atomos de enteros
    boolean this.isNIL =false; // Null de LISP
    String this.nombre = ""; // Valor de atomos simbolicos
    // Hashtable que contenga funciones y valores
    // En este caso se uso un map tipo Hashtable para poder determina diferentes tipos
    //de datos que se identificaran al momento de leer el archivo .txt
    public static HashMap<String, SExp> idValores = new HashMap<String, SExp>();

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
        this.tipo_de_nodo = 1;
        this.valor = numero;
    }

    //  Crea S-Expressiones de tipo string
    public SExpression(String string_dependiente){
        this.tipo_de_nodo = 2;
        this.nombre = string_dependiente;
    }
    // S-Expression de ningun tipo de atom, solo reportan el valor inicial
    // de una list --> c1
    // todos los valores exceptuando el primer elemento en una list --> c2
    public SExpression(SExpression c1, SExpression c2){
        this.tipo_de_nodo = 3;
        this.c1 = c1;
        this.c2 = c2;
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
    public static SExpression less(SExpression exp1, SExpression exp2){
        if(exp1.getValue() < exp2.getValue())
            return getTable("T");
        else
            return getTable("NIL");

    public SExpression(int numero) {
        this.tipo = 1;
        this.val = numero;
    }

    //  Crea S-Expressiones de tipo string
    public SExpression(String string_dependiente) {
        this.tipo = 2;
        this.nombre = string_dependiente;
    }

    //Recibe como parametros dos expresiones S las funciones basicas de aritmetica
    public static SExpression suma(SExpression exp1, SExpression exp2) {
        int adicion = exp1.val + exp2.val;
        return new SExpression(adicion);
    }

    public static SExpression resta(SExpression exp1, SExpression exp2) {
        int resta_res = exp1.val - exp2.val;
        return new SExpression(resta_res);
    }

    public static SExpression multiplicar(SExpression exp1, SExpression exp2) {
        int multi_res = exp1.val * exp2.val;
        return new SExpression(multi_res);
    }

    public static SExpression dividir(SExpression exp1, SExpression exp2) {
        int cos_res = exp1.val / exp2.val;
        return new SExpression(cos_res);
    }

    public static SExpression modulo(SExpression exp1, SExpression exp2) {
        int rem_res = exp1.val % exp2.val;
        return new SExpression(rem_res);
    }

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
        return this.c1;
    }

    public SExpression c2(){
        return this.c2;
    }

    public boolean es_nil(){
        return tipo == 2 && nombre.equals("NLL");
    }

    public boolena isT(){
        return tipo == 2 && nombre.equals("T");
    }

    public boolean isAtom() {
        return tipo != 3;
    }

    public boolena isInteger(){
        return tipo == 1;
    }

    public boolean isSymbol(){
        return tipo == 2;
    }

    public static SExpression cons(SExpression c1, SExpression c2){
        return new SExpression(c1, c2);
    }
}
