import java.util.HashMap;
import java.util.ArrayList;

public class Evaluador{
    AList aL;

    // Contructor 
    public Evaluator(){
        aL = new AList();
    }

    public SExpression eval(SExpression exp) throws customException{
      

        // Si la expression es un atom
        // Agarra los valores con signo
        if(exp.isAtom()){
            if(exp.isT() || exp.isNil() || exp.isInteger()){
                return exp;
            }
            else
                return aL.getVal(exp);
        }

        // Chequea que tipo de funcion es
        else if(exp.car().isSymbol()){
            SExpression c1 = exp.car();
            SExpression c2 = exp.cdr();
            String nombre_exp = c1.name;

            if(nombre_exp.equals("QUOTE")){
                // Retorna pos si tiene valores correctos
                String error_en_consola = checkIfValidArgs("QUOTE", c2);
                if(error_en_consola != null)
                    throw new customException(error_en_consola, "Evaluacion Erronea");
                else
                    return c2.car();
            }
            else if(nombre_exp.equals("COND")){
                // Llama a COND
                if(c2.isNil())
                    throw new customException("Argumentos no encontrados. **", "Evaluacion");
                return eval_con(c2);
            }
            else if(nombre_exp.equals("DEFUN")){
                // Chequea si algo es una funcion o no

                String error_en_consola = checkIfValidDefun(c2);
                if(error_en_consola != null)
                    throw new customException(error_en_consola, "Evaluacion");

                // If all arguments are valid, create a Defun object with nombre_exp, params and body
                SExpression nombre_de_funcion = c2.car().car();
                SExpression parametros_funcion = c2.car().cdr().car();
                SExpression cuerpo_de_funcion = c2.cdr().car();
                Defun code_de_defun = new Defun(nombre_de_funcion.name, parametros_funcion, cuerpo_de_funcion);
                // Add the function to DList
                DList.addFunction(nombre_de_funcion.name, code_de_defun);

                return SExpression.getTable(nombre_de_funcion.name);
            }

            else{
                SExpression resultado_1;
                try{
                    resultado_1 = evaluacion_list(c2);
                }
                catch(NullPointerException e){
                    throw new customException("Evaluacion erronea " + exp.displayTree() + " **", "Evaluacion");
                }
                return apply(c1, resultado_1);
            }
        }
        else{
            throw new customException("Este codigo no es Lisp **", "Evaluacion");
        }
    }


    public SExpression eval_con(SExpression be) throws customException{
        // Implementacion de eval_con

        if(be.isNil()){
            throw new customException("Error blooleano.** ", "Evaluacion");
        }

        String error_en_consola = checkIfValidArgs("EVAL_CON", be.car());
        if(error_en_consola != null)
            throw new customException(error_en_consola, "Evaluacion");

        SExpression booleano = be.car().car();
        SExpression exp = be.car().cdr().car();
        SExpression resultado_booleano = eval(booleano);

        if(!resultado_booleano.isNil()){
            return eval(exp);
        }
        else{
            return eval_con(be.cdr());
        }
    }

    public SExpression evaluacion_list(SExpression list) throws customException{
        // This function implements evaluacion_list
        if(list.isNil())
            return SExpression.getTable("NIL");

        SExpression c1 = eval(list.car());
        SExpression c2 = evaluacion_list(list.cdr());
        return SExpression.cons(c1, c2);
    }

    public SExpression apply(SExpression funcion_primaria, SExpression parametros_arg) throws customException{
        // This funcion_primaria applies appropriate funcion_primaria from arguments

        // Check if funcion_primaria has valid arguments
        String nombre_funcion = funcion_primaria.name;
        String error_en_consola = checkIfValidArgs(nombre_funcion, parametros_arg);
        if(error_en_consola != null)
            throw new customException(error_en_consola, "Evaluacion");

        // Check if funcion_primaria is in a list of built-in functions
        if(nombre_funcion.equals("CAR"))
            return parametros_arg.car().car();

        else if(nombre_funcion.equals("CDR"))
            return parametros_arg.car().cdr();

        else if(nombre_funcion.equals("CONS"))
            return SExpression.cons(parametros_arg.car(), parametros_arg.cdr().car());

        else if(nombre_funcion.equals("PLUS"))
            return SExpression.plus(parametros_arg.car(), parametros_arg.cdr().car());

        else if(nombre_funcion.equals("MINUS"))
            return SExpression.minus(parametros_arg.car(), parametros_arg.cdr().car());

        else if(nombre_funcion.equals("TIMES"))
            return SExpression.times(parametros_arg.car(), parametros_arg.cdr().car());

        else if(nombre_funcion.equals("QUOTIENT"))
            return SExpression.quotient(parametros_arg.car(), parametros_arg.cdr().car());

        else if(nombre_funcion.equals("REMAINDER"))
            return SExpression.remainder(parametros_arg.car(), parametros_arg.cdr().car());

        else if(nombre_funcion.equals("LESS"))
            return SExpression.less(parametros_arg.car(), parametros_arg.cdr().car());

        else if(nombre_funcion.equals("GREATER"))
            return SExpression.greater(parametros_arg.car(), parametros_arg.cdr().car());

        else if(nombre_funcion.equals("ATOM")){
            if(parametros_arg.car().isAtom())
                return SExpression.getTable("T");
            else
                return SExpression.getTable("NIL");
        }

        else if(nombre_funcion.equals("EQ"))
            return SExpression.eq(parametros_arg.car(), parametros_arg.cdr().car());

        else if(nombre_funcion.equals("NULL")){
            if(parametros_arg.car().isNil())
                return SExpression.getTable("T");
            else
                return SExpression.getTable("NIL");
        }

        else if(nombre_funcion.equals("INT")){
            if(parametros_arg.car().isInteger())
                return SExpression.getTable("T");
            else
                return SExpression.getTable("NIL");
        }

        // If not built-in, check if in the DList
        else{
            Defun defun_arg = DList.getFunction(nombre_funcion);

            // If not in the DList, undefined funcion_primaria
            if(defun_arg == null)
                throw new customException("Undefined funcion_primaria " + nombre_funcion + " . **", "Evaluacion");

            // Add arguments to AList
            aL.addPairs(nombre_funcion, defun_arg.parametros_1, aL.getArgumentsAsList(parametros_arg));
            // Execute the funcion_primaria
            SExpression exp = eval(defun_arg.cuerpo_de_funcion);
            // Remove the pairs from AList
            aL.destroyPairs(defun_arg.parametros_1);
            return exp;
        }
    }

    // Helper function to check for valid arguments

    public String checkIfValidDefun(SExpression defun_arg){
        String error_en_consola = null;

        // Function name
        if(defun_arg.isNil())
            error_en_consola = "Function name and body cannot be null. **";
        else if(defun_arg.car().isNil())
            error_en_consola = "Empty function name and parametros_1. **";
        else if(!defun_arg.car().car().isSymbol())
            error_en_consola = "Function name should be a symbolic atom. **";

            // Function parametros_1
        else if(defun_arg.car().cdr().isNil() || defun_arg.car().cdr().car().isNil())
            error_en_consola = "Parameters of the function cannot be empty. **";

        else if(!defun_arg.car().cdr().cdr().isNil())
            error_en_consola = "Parameters should be defined as a separate list from function name. **";

        else if(defun_arg.car().cdr().isAtom())
            error_en_consola = "Parameters to the function cannot be an atom. **";

            // Function body
        else if(defun_arg.cdr().isNil() || defun_arg.cdr().car().isNil())
            error_en_consola = "Function body cannot be empty. **";
        else if(defun_arg.cdr().isAtom())
            error_en_consola = "Function body cannot be an atom. **";

            // If the part after function body is not NIL, it cannot be evaluated.
        else if(!defun_arg.cdr().cdr().isNil())
            error_en_consola = "Unexpected expression " + defun_arg.cdr().cdr().displayTree() + " found after function body. **";

        return error_en_consola;
    }

    public String checkIfValidArgs(String funcion_necesaria, SExpression argumentos){
        // Function to check if given functions had valid arguments

        String error_en_consola = null;
        int argumentos_prim = countArgs(argumentos);
        String nombre_funcion = funcion_necesaria;

        if(funcion_necesaria.equals("PLUS") || funcion_necesaria.equals("MINUS") ||
                funcion_necesaria.equals("TIMES") || funcion_necesaria.equals("QUOTIENT") ||
                funcion_necesaria.equals("GREATER") || funcion_necesaria.equals("LESS") || funcion_necesaria.equals("REMAINDER"))
            nombre_funcion = "BINARY";

        switch(nombre_funcion){
            case "QUOTE":
                if(argumentos_prim != 1)
                    error_en_consola = "QUOTE expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "EVCON":
                if(argumentos_prim != 2)
                    error_en_consola = "EVCON expects two arguments. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "CAR":
                if(argumentos_prim != 1)
                    error_en_consola = "CAR expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                else if(argumentos.car().isAtom())
                    error_en_consola =  "Invalid argument to CAR. " + argumentos.car().getName() + " is an atom. **";
                return error_en_consola;

            case "CDR":
                if(argumentos_prim != 1)
                    error_en_consola = "CDR expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                else if(argumentos.car().isAtom())
                    error_en_consola =  "Invalid argument to CDR. " + argumentos.car().getName() + " is an atom. **";
                return error_en_consola;

            case "CONS":
                if(argumentos_prim != 2)
                    error_en_consola = "CONS expects two arguments. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "ATOM":
                if(argumentos_prim != 1)
                    error_en_consola = "ATOM expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "EQ":
                if(argumentos_prim != 2)
                    error_en_consola = "EQ expects two arguments. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "NULL":
                if(argumentos_prim != 1)
                    error_en_consola = "NULL expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "INT":
                if(argumentos_prim != 1)
                    error_en_consola = "INT expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_en_consola;

            case "BINARY":
                if(argumentos_prim != 2)
                    error_en_consola = funcion_necesaria + " expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                else if(!argumentos.car().isInteger() || !argumentos.cdr().car().isInteger())
                    error_en_consola = "Arguments to " + funcion_necesaria + " must be integer atoms. **";
                else if((funcion_necesaria.equals("QUOTIENT") || funcion_necesaria.equals("REMAINDER")) && argumentos.cdr().car().val == 0)
                    error_en_consola = "Cannot perform division with divisor value as 0";
                return error_en_consola;

            default:
                return error_en_consola;
        }
    }

    public int countArgs(SExpression exp){
        // This function just counts number of arguments using car till NIL occurs
        int contador = 0;
        while(!exp.isNil()){
            exp = exp.cdr();
            contador += 1;
        }
        return contador;
    }

}