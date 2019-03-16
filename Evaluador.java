import java.util.ArrayList;
/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
Andrés Quan Littow       17652
Mario Andrés             18029
Josué Sagastume          18173
Evaluador.java
Proposito:
 */
/**
 *Esta clase contiene funciones para la evaluación del árbol de expresión-s
 */
public class Evaluador {
    Arguments aL;//ArgumentList

    /**
     * Constructor del evaluador creando una clase con argumentos
     */
    // Contructor
    public Evaluador() {
        aL = new Arguments();
    }

    /**
     * Identifica, o "Evalúa" una expresión
     *
     * @param exp Una expresion
     * @return Devuelve una SExpresiion
     * @throws exceptionError Exceptuando cuando ingrese valores diferentes de ATOM,T, NIL o Integer.
     */
    public SExpression eval(SExpression exp) throws exceptionError {


        // Si la expression es un atom
        // Agarra los valores con signo
        if (exp.isAtom()) {
            if (exp.isT() || exp.es_nil() || exp.isInteger()) {
                return exp;
            } else
                return aL.getVal(exp);
        }

        // Chequea que tipo de funcion es
        else if (exp.car().isSymbol()) {
            SExpression car = exp.car();
            SExpression cdr = exp.cdr();
            String nombre_exp = car.nombre;

            if (nombre_exp.equals("QUOTE")) {
                // Retorna pos si tiene valores correctos
                String error_en_consola = checkIfValidArgs("QUOTE", cdr);
                if (error_en_consola != null)
                    throw new exceptionError(error_en_consola, "Evaluacion Erronea");
                else
                    return cdr.car();
            } else if (nombre_exp.equals("COND")) {
                // Llama a COND
                if (cdr.es_nil())
                    throw new exceptionError("Argumentos no encontrados. **", "Evaluacion");
                return eval_con(cdr);
            } else if (nombre_exp.equals("FuncionesLISP")) {
                // Chequea si algo es una funcion o no

                String error_en_consola = checkIfValidDefun(cdr);
                if (error_en_consola != null)
                    throw new exceptionError(error_en_consola, "Evaluacion");

                // Chequea la validez de los argumentos
                SExpression nombre_de_funcion = cdr.car().car();
                SExpression parametros_funcion = cdr.car().cdr().car();
                SExpression cuerpo_de_funcion = cdr.cdr().car();
                FuncionesLISP code_de_defun = new FuncionesLISP(nombre_de_funcion.nombre, parametros_funcion, cuerpo_de_funcion);
                // Add the function to Defunctions
                Defunctions.addFunction(nombre_de_funcion.nombre, code_de_defun);

                return SExpression.getTable(nombre_de_funcion.nombre);
            } else {
                SExpression resultado_1;
                try {
                    resultado_1 = evaluacion_list(cdr);
                } catch (NullPointerException e) {
                    throw new exceptionError("Evaluacion erronea ", "evaluacion");
                }
                return apply(car, resultado_1);
            }
        } else {
            throw new exceptionError("Este codigo no es Lisp **", "Evaluacion");
        }
    }


    /**
     * Evalúa a base de booleanos
     *
     * @param be Una SExpression de forma Booleana
     * @return SExpression
     * @throws exceptionError Difernte de un Booleano
     */
    public SExpression eval_con(SExpression be) throws exceptionError {
        // Implementacion de eval_con

        if (be.es_nil()) {
            throw new exceptionError("Error blooleano.** ", "Evaluacion");
        }

        String error_en_consola = checkIfValidArgs("EVAL_CON", be.car());
        if (error_en_consola != null)
            throw new exceptionError(error_en_consola, "Evaluacion");

        SExpression booleano = be.car().car();
        SExpression exp = be.car().cdr().car();
        SExpression resultado_booleano = eval(booleano);

        if (!resultado_booleano.es_nil()) {
            return eval(exp);
        } else {
            return eval_con(be.cdr());
        }
    }

    /**
     * Evalúa a base de una lista
     * @param list Una list de expresiones
     * @return SExpression
     * @throws exceptionError
     */
    public SExpression evaluacion_list(SExpression list) throws exceptionError {
        // Implementacion de evaluacion_list
        if (list.es_nil())
            return SExpression.getTable("NIL");

        SExpression car = eval(list.car());
        SExpression cdr = evaluacion_list(list.cdr());
        return SExpression.cons(car, cdr);
    }

    /**
     * Aplica a una expresión
     * @param funcion_primaria Nombre de la funcion
     * @param parametros_arg Sus parametros
     * @return SExpression Devuelve el valor despues de evaluar la funcion
     * @throws exceptionError Exceptuando valores diferntes de aritmeticas, mayor, menor, cdr y car.
     */
    public SExpression apply(SExpression funcion_primaria, SExpression parametros_arg) throws exceptionError {


        // Chequea si funcion_primaria tiene argumentos válidos
        String nombre_funcion = funcion_primaria.nombre;
        String error_en_consola = checkIfValidArgs(nombre_funcion, parametros_arg);
        if (error_en_consola != null)
            throw new exceptionError(error_en_consola, "Evaluacion");

        // Chequea donde esta el parametro principal
        if (nombre_funcion.equals("CAR"))
            return parametros_arg.car().car();

        else if (nombre_funcion.equals("CDR"))
            return parametros_arg.car().cdr();

        else if (nombre_funcion.equals("CONS"))
            return SExpression.cons(parametros_arg.car(), parametros_arg.cdr().car());

        else if (nombre_funcion.equals("PLUS"))
            return SExpression.suma(parametros_arg.car(), parametros_arg.cdr().car());

        else if (nombre_funcion.equals("MINUS"))
            return SExpression.resta(parametros_arg.car(), parametros_arg.cdr().car());

        else if (nombre_funcion.equals("TIMES"))
            return SExpression.multiplicar(parametros_arg.car(), parametros_arg.cdr().car());

        else if (nombre_funcion.equals("QUOTIENT"))
            return SExpression.dividir(parametros_arg.car(), parametros_arg.cdr().car());

        else if (nombre_funcion.equals("REMAINDER"))
            return SExpression.modulo(parametros_arg.car(), parametros_arg.cdr().car());

        else if (nombre_funcion.equals("LESS"))
            return SExpression.less(parametros_arg.car(), parametros_arg.cdr().car());

        else if (nombre_funcion.equals("GREATER"))
            return SExpression.greater(parametros_arg.car(), parametros_arg.cdr().car());

        else if (nombre_funcion.equals("ATOM")) {
            if (parametros_arg.car().isAtom())
                return SExpression.getTable("T");
            else
                return SExpression.getTable("NIL");
        } else if (nombre_funcion.equals("EQ"))
            return SExpression.igual(parametros_arg.car(), parametros_arg.cdr().car());

        else if (nombre_funcion.equals("NULL")) {
            if (parametros_arg.car().es_nil())
                return SExpression.getTable("T");
            else
                return SExpression.getTable("NIL");
        } else if (nombre_funcion.equals("INT")) {
            if (parametros_arg.car().isInteger())
                return SExpression.getTable("T");
            else
                return SExpression.getTable("NIL");
        }

        // Chequea validacion de AList vs Defunctions
        else {
            FuncionesLISP defun_arg = Defunctions.getFunction(nombre_funcion);

            // If not in the Defunctions, undefined funcion_primaria
            if (defun_arg == null)
                throw new exceptionError("Undefined funcion_primaria " + nombre_funcion + " . **", "Evaluacion");

            // Agrega argumentos a AList
            aL.addPairs(nombre_funcion, defun_arg.parametros, aL.getArgumentsAsList(parametros_arg));
            // ejecuta funcion_primaria
            SExpression exp = eval(defun_arg.cuerpoFuncion);
            // Quita de AList
            aL.destroyPairs(defun_arg.parametros);
            return exp;
        }
    }

    // Chequea por argumentos ovalidos

    /**
     * @param defun_arg Una funcion con sus argumentos
     * @return Devuelve un error o un mensaje dependiedo su condicion
     */
    public String checkIfValidDefun(SExpression defun_arg) {
        String error_en_consola = null;

        // Nombre de Funcion
        if (defun_arg.es_nil())
            error_en_consola = "Function nombre and body cannot be null. **";
        else if (defun_arg.car().es_nil())
            error_en_consola = "Empty function nombre and parametros_1. **";
        else if (!defun_arg.car().car().isSymbol())
            error_en_consola = "Function nombre should be a symbolic atom. **";

            // Function parametros_1
        else if (defun_arg.car().cdr().es_nil() || defun_arg.car().cdr().car().es_nil())
            error_en_consola = "Parameters of the function cannot be empty. **";

        else if (!defun_arg.car().cdr().cdr().es_nil())
            error_en_consola = "Parameters should be defined as a separate list from function nombre. **";

        else if (defun_arg.car().cdr().isAtom())
            error_en_consola = "Parameters to the function cannot be an atom. **";

            // Cuerpo de la función
        else if (defun_arg.cdr().es_nil() || defun_arg.cdr().car().es_nil())
            error_en_consola = "Function body cannot be empty. **";
        else if (defun_arg.cdr().isAtom())
            error_en_consola = "Function body cannot be an atom. **";

            // No se puede evaluar si "NIL" no existe
        else if (!defun_arg.cdr().cdr().es_nil())
            error_en_consola = "Unexpected expression " + " found after function body. **";

        return error_en_consola;
    }

    public String checkIfValidArgs(String funcion_necesaria, SExpression argumentos) {
        // Chequea si tiene argumentos válidos

        String error_en_consola = null;
        int argumentos_prim = countArgs(argumentos);
        String nombre_funcion = funcion_necesaria;

        // Source: www.github.com/basic-LISP-functions
        if (funcion_necesaria.equals("PLUS") || funcion_necesaria.equals("MINUS") ||
                funcion_necesaria.equals("TIMES") || funcion_necesaria.equals("QUOTIENT") ||
                funcion_necesaria.equals("GREATER") || funcion_necesaria.equals("LESS") || funcion_necesaria.equals("REMAINDER"))
            nombre_funcion = "BINARY";

        switch (nombre_funcion) {
            case "QUOTE":
                if (argumentos_prim != 1)
                    error_en_consola = "QUOTE expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "EVCON":
                if (argumentos_prim != 2)
                    error_en_consola = "EVCON expects two arguments. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "CAR":
                if (argumentos_prim != 1)
                    error_en_consola = "CAR expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                else if (argumentos.car().isAtom())
                    error_en_consola = "Invalid argument to CAR. " + argumentos.car().getName() + " is an atom. **";
                return error_en_consola;

            case "CDR":
                if (argumentos_prim != 1)
                    error_en_consola = "CDR expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                else if (argumentos.car().isAtom())
                    error_en_consola = "Invalid argument to CDR. " + argumentos.car().getName() + " is an atom. **";
                return error_en_consola;

            case "CONS":
                if (argumentos_prim != 2)
                    error_en_consola = "CONS expects two arguments. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "ATOM":
                if (argumentos_prim != 1)
                    error_en_consola = "ATOM expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "EQ":
                if (argumentos_prim != 2)
                    error_en_consola = "EQ expects two arguments. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "NULL":
                if (argumentos_prim != 1)
                    error_en_consola = "NULL expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "INT":
                if (argumentos_prim != 1)
                    error_en_consola = "INT expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "BINARY":
                if (argumentos_prim != 2)
                    error_en_consola = funcion_necesaria + " expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                else if (!argumentos.car().isInteger() || !argumentos.cdr().car().isInteger())
                    error_en_consola = "Arguments to " + funcion_necesaria + " must be integer atoms. **";
                else if ((funcion_necesaria.equals("QUOTIENT") || funcion_necesaria.equals("REMAINDER")) && argumentos.cdr().car().valor == 0)
                    error_en_consola = "Cannot perform division with divisor value as 0";
                return error_en_consola;

            default:
                return error_en_consola;
        }
    }

    /**
     * Cuenta el número de argumentos
     * @param exp la expresion
     * @return contador
     */
    public int countArgs(SExpression exp) {
        // CAR
        int contador = 0;
        while (!exp.es_nil()) {
            exp = exp.cdr();
            contador += 1;
        }
        return contador;
    }

}