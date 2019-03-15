import java.util.HashMap;
import java.util.ArrayList;

public class ALista{

    HashMap<String, ArrayList<SExpression>> mapaDeLista = new HashMap<>();

    public ALista(){}

    public SExpression getValor(SExpression SExp) throws exceptionError{
        String nombre = SExp.nombre;
        if(mapaDeLista.containsKey(nombre)){
            ArrayList <SExpression> lista = mapaDeLista.get(nombre);
            return lista.get(0);
        } else {
            throw new exceptionError("Unbound atom " + nombre, "Evaluacion");
        }
    }

    //Este metodo convierte SExpression de argumentos a una lista para agreganrla a
    //la estructura de ALista
    public static ArrayList<SExpression> getArgumentosLista(SExpression argumentos) throws exceptionError{
        ArrayList <SExpression> argumento = new ArrayList<>();
        while(!argumentos.isNil()){
            SExpression car = argumentos.car();
            if(!car.isNil()){
                argumento.add(car);
            }
            argumentos = argumentos.cdr();
        }

        return argumento;
    }

    //Este metodo toma parametros formales y parametros actuales como una lista y
    //los agrega dentro de la estructura de ALista
    public void agregarPares (String nombreFuncion, ArrayList<String> parametros, ArrayList<SExpression> argumentos) throws exceptionError{
        int parametrosSize = parametros.size();
        int argumentosSize = argumentos.size();

        if (parametrosSize != argumentosSize){
            throw new exceptionError("La funcion " + nombreFuncion + " espera " + parametrosSize + " argumentos. "
            +  "Se han pasado" + argumentosSize, "Evaluacion");
        }

        //Sigue agregando argumentos uno por uno para cada parametro al frente de la lista
        for (int i = 0; i < parametrosSize; i++){
            String parametro = parametros.get(i);
            SExpression argumento = argumentos.get(i);
            ArrayList<SExpression> lista = new ArrayList<>();
            if(mapaDeLista.containsKey(parametro)){
                lista = mapaDeLista.get(parametro);
            }
            lista.add(0, argumento);
            mapaDeLista.put(parametro, lista);
        }
    }

    //Despues de la ejecucion de la funcion, remueve el ultimo argumento de la estructura de ALista
    public void destruirPares(ArrayList<String> parametros){
        int parametrosSize = parametros.size();
        for(int i = 0; i < parametrosSize; i++){
            String parametro = parametros.get(i);
            ArrayList <SExpression> lista = mapaDeLista.get(parametro);
            lista.remove(0);
            mapaDeLista.put(parametro, lista);
        }
    }

}