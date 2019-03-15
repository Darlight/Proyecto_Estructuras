import java.util.HashMap;
import java.util.ArrayList;

public class Evaluador{
    AList aL;

    // Contructor to invoke new AList for each
    public Evaluator(){
        aL = new AList();
    }

    public SExpression eval(SExpression exp) throws customException{
        // This function implements eval function.

        // If S-Expression is an atom,
        // it gets the value from either symbol table or AList
        if(exp.isAtom()){
            if(exp.isT() || exp.isNil() || exp.isInteger()){
                return exp;
            }
            else
                return aL.getVal(exp);
        }

        // If not atom, take car and check what kind of function it is.
        else if(exp.car().isSymbol()){
            SExpression c1 = exp.car();
            SExpression c2 = exp.cdr();
            String nombre_exp = c1.name;

            if(nombre_exp.equals("QUOTE")){
                // If valid arguments are given, just return c2 of the S-Expression
                String error_en_consola = checkIfValidArgs("QUOTE", c2);
                if(error_en_consola != null)
                    throw new customException(error_en_consola, "Evaluacion Erronea");
                else
                    return c2.car();
            }
            else if(nombre_exp.equals("COND")){
                // For COND, call eval_con
                if(c2.isNil())
                    throw new customException("Argumentos no encontrados. **", "Evaluacion");
                return eval_con(c2);
            }
            else if(nombre_exp.equals("DEFUN")){
                // If Defun, check if it defined properly with function nombre_exp, parametros_funcion and body

                String error = checkIfValidDefun(c2);
                if(error != null)
                    throw new customException(error, "Evaluation");

                // If all arguments are valid, create a Defun object with nombre_exp, params and body
                SExpression nombre_de_funcion = c2.car().car();
                SExpression parametros_funcion = c2.car().cdr().car();
                SExpression cuerpo_de_funcion = c2.cdr().car();
                Defun code_de_defun = new Defun(nombre_de_funcion.name, parametros_funcion, cuerpo_de_funcion);
                // Add the function to DList
                DList.addFunction(nombre_de_funcion.name, code_de_defun);

                return SExpression.getTable(nombre_de_funcion.name);
            }
            // else if(c2.isSymbol())
            // 	throw new customException("Could not evaluate" + exp.displayTree() + " .**", "Evaluation");

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
        // This function implemets eval_con function.

        if(be.isNil()){
            throw new customException("Error blooleano.** ", "Evaluacion");
        }

        String error_codigo = checkIfValidArgs("EVAL_CON", be.car());
        if(error_codigo != null)
            throw new customException(error_codigo, "Evaluation");

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
        String error_codigo = checkIfValidArgs(nombre_funcion, parametros_arg);
        if(error_codigo != null)
            throw new customException(error_codigo, "Evaluation");

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
            Defun defun = DList.getFunction(nombre_funcion);

            // If not in the DList, undefined funcion_primaria
            if(defun == null)
                throw new customException("Undefined funcion_primaria " + nombre_funcion + " . **", "Evaluation");

            // Add arguments to AList
            aL.addPairs(nombre_funcion, defun.parameters, aL.getArgumentsAsList(parametros_arg));
            // Execute the funcion_primaria
            SExpression exp = eval(defun.funBody);
            // Remove the pairs from AList
            aL.destroyPairs(defun.parameters);
            return exp;
        }
    }

    // Helper function to check for valid arguments

    public String checkIfValidDefun(SExpression defun){
        String error_codigo = null;

        // Function name
        if(defun.isNil())
            error_codigo = "Function name and body cannot be null. **";
        else if(defun.car().isNil())
            error_codigo = "Empty function name and parameters. **";
        else if(!defun.car().car().isSymbol())
            error_codigo = "Function name should be a symbolic atom. **";

            // Function parameters
        else if(defun.car().cdr().isNil() || defun.car().cdr().car().isNil())
            error_codigo = "Parameters of the function cannot be empty. **";

        else if(!defun.car().cdr().cdr().isNil())
            error_codigo = "Parameters should be defined as a separate list from function name. **";

        else if(defun.car().cdr().isAtom())
            error_codigo = "Parameters to the function cannot be an atom. **";

            // Function body
        else if(defun.cdr().isNil() || defun.cdr().car().isNil())
            error_codigo = "Function body cannot be empty. **";
        else if(defun.cdr().isAtom())
            error_codigo = "Function body cannot be an atom. **";

            // If the part after function body is not NIL, it cannot be evaluated.
        else if(!defun.cdr().cdr().isNil())
            error_codigo = "Unexpected expression " + defun.cdr().cdr().displayTree() + " found after function body. **";

        return error_codigo;
    }

    public String checkIfValidArgs(String funcion_necesaria, SExpression argumentos){
        // Function to check if given functions had valid arguments

        String error_codigo = null;
        int argumentos_prim = countArgs(argumentos);
        String nombre_funcion = funcion_necesaria;

        if(funcion_necesaria.equals("PLUS") || funcion_necesaria.equals("MINUS") ||
                funcion_necesaria.equals("TIMES") || funcion_necesaria.equals("QUOTIENT") ||
                funcion_necesaria.equals("GREATER") || funcion_necesaria.equals("LESS") || funcion_necesaria.equals("REMAINDER"))
            nombre_funcion = "BINARY";

        switch(nombre_funcion){
            case "QUOTE":
                if(argumentos_prim != 1)
                    error_codigo = "QUOTE expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_codigo;

            case "EVCON":
                if(argumentos_prim != 2)
                    error_codigo = "EVCON expects two arguments. " + Integer.toString(argumentos_prim) + " given. **";
                return error_codigo;

            case "CAR":
                if(argumentos_prim != 1)
                    error_codigo = "CAR expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                else if(argumentos.car().isAtom())
                    error_codigo =  "Invalid argument to CAR. " + argumentos.car().getName() + " is an atom. **";
                return error_codigo;

            case "CDR":
                if(argumentos_prim != 1)
                    error_codigo = "CDR expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                else if(argumentos.car().isAtom())
                    error_codigo =  "Invalid argument to CDR. " + argumentos.car().getName() + " is an atom. **";
                return error_codigo;

            case "CONS":
                if(argumentos_prim != 2)
                    error_codigo = "CONS expects two arguments. " + Integer.toString(argumentos_prim) + " given. **";
                return error_codigo;

            case "ATOM":
                if(argumentos_prim != 1)
                    error_codigo = "ATOM expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_codigo;

            case "EQ":
                if(argumentos_prim != 2)
                    error_codigo = "EQ expects two arguments. " + Integer.toString(argumentos_prim) + " given. **";
                return error_codigo;

            case "NULL":
                if(argumentos_prim != 1)
                    error_codigo = "NULL expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_codigo;

            case "INT":
                if(argumentos_prim != 1)
                    error_codigo = "INT expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                return error_codigo;

            case "BINARY":
                if(argumentos_prim != 2)
                    error_codigo = funcion_necesaria + " expects exactly one argument. " + Integer.toString(argumentos_prim) + " given. **";
                else if(!argumentos.car().isInteger() || !argumentos.cdr().car().isInteger())
                    error_codigo = "Arguments to " + funcion_necesaria + " must be integer atoms. **";
                else if((funcion_necesaria.equals("QUOTIENT") || funcion_necesaria.equals("REMAINDER")) && argumentos.cdr().car().val == 0)
                    error_codigo = "Cannot perform division with divisor value as 0";
                return error_codigo;

            default:
                return error_codigo;
        }
    }

    public int countArgs(SExpression exp){
        // This function just counts number of arguments using car till NIL occurs
        int counter = 0;
        while(!exp.isNil()){
            exp = exp.cdr();
            counter += 1;
        }
        return counter;
    }

}