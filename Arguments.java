/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */
import java.util.HashMap;
import java.util.ArrayList;

public class Arguments {
    // HashMap como arguments
    HashMap<String, ArrayList<SExpression>> arguments = new HashMap<>();

    // Un constructor vacio que solo contenga el hashmap
    public Arguments() {

    }


    public SExpression getVal(SExpression SExp) throws exceptionError {
        // Retorna el valor del atom
        String nombre = SExp.nombre;
        if (arguments.containsKey(nombre)) {
            ArrayList<SExpression> lista = arguments.get(nombre);
            return lista.get(0);
        } else {
            throw new exceptionError("Unbound atom " + nombre, "Evaluacion");
        }
    }


    public ArrayList<SExpression> getArgumentsAsList(SExpression argumentos) throws exceptionError {
        // Convierte Sexpresiones los paramatros como lista para agregar dentro de Arguments
        ArrayList<SExpression> parametros = new ArrayList<>();

        while (!argumentos.es_nil()) {
            SExpression car = argumentos.car();
            if (!car.es_nil())
                parametros.add(car);
            argumentos = argumentos.cdr();
        }
        return parametros;
    }


    public void addPairs(String nombre_Funcion, ArrayList<String> parametros_1, ArrayList<SExpression> argumentos) throws exceptionError{
        // Guarda paramatros formales respecto a las ya creadas. Se guardan en arguments

        int tamano_Parametro = parametros_1.size();
        int tamano_Argumentos = argumentos.size();

        // If sizes differ, raise appropriate error with function nombre
        if(tamano_Parametro != tamano_Argumentos)
            throw new exceptionError("Function " + nombre_Funcion + " expects " + tamano_Parametro + " arguments. " + tamano_Argumentos + " given. **", "Evaluacion");

        // Se agregan argumentos por cada en para el hashtable
        for(int i = 0; i < tamano_Parametro; i++){
            String parametros = parametros_1.get(i);
            SExpression argument = argumentos.get(i);
            ArrayList <SExpression> lista = new ArrayList<>();
            if(arguments.containsKey(parametros))
                lista = arguments.get(parametros);
            lista.add(0, argument);
            arguments.put(parametros, lista);
        }
    }


    public void destroyPairs(ArrayList<String> parametros_1){
        // Cuando funciona una funciona, destruye argumentos
        int tamano_Parametro = parametros_1.size();
        for(int i = 0; i < tamano_Parametro; i++){
            String parametros = parametros_1.get(i);
            ArrayList <SExpression> lista = arguments.get(parametros);
            lista.remove(0);
            arguments.put(parametros, lista);
        }
    }
}