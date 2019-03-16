/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
Andrés Quan Littow       17652
Mario Andrés             18029
Josué Sagastume          18173
SExpression.java
 */

/**
 * Procesamiento principal del programa. Esta clase define, representa la expresión lisp analizada, en memoria.
 * Cada expresión S tiene un tipo: 1 entero, 2 simbólico, 3 no atómico
 * En caso de que el tipo sea un átomo entero, el valor debe ser el correspondiente
 * entero se almacena en int val. Para los átomos simbólicos, la variable del nombre contiene
 * El nombre de la variable. Si es una S-Expresión no atómica, entonces izquierda y derecha
 * Las variables apuntan a los nodos hijos izquierdo y derecho respectivamente,
 * De lo contrario son nulos.
 */
import java.util.*;
public class SExpression {


    private boolean es_nil = false; // Null de LISP
    private SExpression car, cdr; // Valores de ningun atom
    int tipo_de_nodo =0; // Que tipo de nodo sera creado
    int valor=0; // Valor de atomos de enteros
    boolean isNIL =false; // Null de LISP
    String nombre = ""; // Valor de atomos simbolicos
    int tipo = 0;
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


    // Hashtable que contenga funciones y valores
    // En este caso se uso un map tipo Hashtable para poder determina diferentes tipos
    //de datos que se identificaran al momento de leer el archivo .txt

    /**
     * @param Id Key para identificar el tipo de SExpression
     * @return
     */
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

    /**
     * @param numero Valor de un tipo Integer
     */
    // Crea S-Expressiones de tipo entero
    public SExpression(int numero){
        tipo_de_nodo = 1;
        valor = numero;
    }

    /** Despliega los valores iniciales de los SExpressions
     *
     * @return
     */
    public String displayTree(){
        String displayString = "";
        if(tipo == 1){
            displayString = displayString + Integer.toString(valor);
        } else if (tipo == 2){
            displayString = displayString + nombre;
        } else {
            displayString = displayString + "(" + car.displayTree() + " . " + cdr.displayTree() + ")";
        }
        return displayString;
    }

    /**
     * Solo imprime la expression
     */
    public void printSExpression(){
        String output = displayTree();
        System.out.println("> " + output);
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
        car = c1;
        cdr = c2;
    }


    /**
     * Recibe como parametros dos expresiones S las funciones basicas de aritmética
     * @param exp1
     * @param exp2
     * @return Valor
     */
    public static SExpression suma(SExpression exp1, SExpression exp2){
        int valor = exp1.getValue() + exp2.getValue();
        return new SExpression(valor);
    }

    /**
     * Resta aritmética
     * @param exp1
     * @param exp2
     * @return valor
     */
    public static SExpression resta(SExpression exp1, SExpression exp2){
        int valor = exp1.getValue() - exp2.getValue();
        return new SExpression(valor);
    }

    /**
     * Multiplicación artimética
     * @param exp1
     * @param exp2
     * @return valor
     */
    public static SExpression multiplicar(SExpression exp1, SExpression exp2){
        int valor = exp1.getValue() * exp2.getValue();
        return new SExpression(valor);
    }

    /**
     * División aritmética
     * @param exp1
     * @param exp2
     * @return valor
     */
    public static SExpression dividir(SExpression exp1, SExpression exp2){
        int valor = exp1.getValue() / exp2.getValue();
        return new SExpression(valor);
    }

    /**
     * Remitente o residuo
     * @param exp1
     * @param exp2
     * @return valor
     */
    public static SExpression modulo(SExpression exp1, SExpression exp2){
        int valor = exp1.getValue() % exp2.getValue();
        return new SExpression(valor);
    }
    // Comparadores verificando sus valores

    /**
     * Crequeo lógico
     * @param exp1
     * @param exp2
     * @return Table
     */
    public static SExpression less(SExpression exp1, SExpression exp2) {
        if (exp1.getValue() < exp2.getValue())
            return getTable("T");
        else
            return getTable("NIL");
    }


    //  Crea S-Expressiones de tipo string


    //Recibe como parametros dos expresiones S las funciones basicas de aritmetica


    /**
     * Chequeo Lógico
     * @param exp1
     * @param exp2
     * @return Table
     */
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

    // getter
    public SExpression car(){
        return this.car;
    }

    // Getter
    public SExpression cdr(){
        return this.cdr;
    }

    // Chequeo
    public boolean es_nil(){
        return tipo == 2 && nombre.equals("NLL");
    }

    // Chequeo
    public boolean isT(){
        return tipo == 2 && nombre.equals("T");
    }

    // Chequeo
    public boolean isAtom() {
        return tipo != 3;
    }

    // Chequeo
    public boolean isInteger(){
        return tipo == 1;
    }

    // Chequeo
    public boolean isSymbol(){
        return tipo == 2;
    }

    /**
     * Creador de expresiones
     * @param c1
     * @param c2
     * @return SExpresión con C1, C2
     */
    public static SExpression cons(SExpression c1, SExpression c2){
        return new SExpression(c1, c2);
    }


}
