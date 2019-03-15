import java.util.;

/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */

public class SExpression {

    int this.nodeType =0; // Que tipo de nodo sera creado
    int this.value=0; // Valor de atomos de enteros
    boolean this.isNIL =false; // Null de LISP
    String this.name =""; // Valor de atomos simbolicos
    // Hashtable que contenga funciones y valores
    // En este caso se uso un map tipo Hashtable para poder determina diferentes tipos
    //de datos que se identificaran al momento de leer el archivo .txt
    public static HashMap<String, SExp> idValores = new HashMap<String, SExp>();

    // Crea S-Expressiones de tipo entero
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

}

}