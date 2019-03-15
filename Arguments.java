/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */
import java.util.HashMap;
import java.util.ArrayList;

public class Argument {
    // HashMap como arguments
    HashMap<String, ArrayList<SExpression>> arguments = new HashMap<>();

    // Un constructor vacio que solo contenga el hashmap
    public Argument() {

    }


    public SExpression getVal(SExpression SExp) throws customException {
        // Retorna el valor del atom
        String name = SExp.name;
        if (arguments.containsKey(name)) {
            ArrayList<SExpression> list = arguments.get(name);
            return list.get(0);
        } else {
            throw new customException("Unbound atom " + name, "Evaluation");
        }
    }


    public static ArrayList<SExpression> getArgumentsAsList(SExpression args) throws customException {
        // Convierte Sexpresiones los paramatros como lista para agregar dentro de Arguments
        ArrayList<SExpression> parametros = new ArrayList<>();
        while (!args.isNil()) {
            SExpression car = args.car();
            if (!car.isNil())
                parametros.add(car);
            args = args.cdr();
        }

        return parametros;
    }
    public void addPairs(String functionName, ArrayList<String> params, ArrayList<SExpression> args) throws customException{
        // Guarda paramatros formales respecto a las ya creadas. Se guardan en arguments

        int paramsSize = params.size();
        int argsSize = args.size();

        // If sizes differ, raise appropriate error with function name
        if(paramsSize != argsSize)
            throw new customException("Function " + functionName + " expects " + paramsSize + " arguments. " + argsSize + " given. **", "Evaluation");

        // Se agregan argumentos por cada en para el hashtable
        for(int i = 0; i < paramsSize; i++){
            String parameter = params.get(i);
            SExpression argument = args.get(i);
            ArrayList <SExpression> list = new ArrayList<>();
            if(arguments.containsKey(parameter))
                list = arguments.get(parameter);
            list.add(0, argument);
            arguments.put(parameter, list);
        }
    }


    public void destroyPairs(ArrayList<String> params){
        // Cuando funciona una funciona, destruye argumentos
        int paramsSize = params.size();
        for(int i = 0; i < paramsSize; i++){
            String parameter = params.get(i);
            ArrayList <SExpression> list = aListMap.get(parameter);
            list.remove(0);
            arguments.put(parameter, list);
        }
    }
}
}