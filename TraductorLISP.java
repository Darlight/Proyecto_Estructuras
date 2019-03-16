/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
Andrés Quan Littow       17652
Mario Andrés             18029
Josué Sagastume          18173
TraductorLISP.java
Proposito: Traduce la sintaxis de Lisp a String, ayuda al Parser
 */


import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *Esta es una clase auxiliar para extraer tokens de la entrada.
 * Primero, reemplaza todo tipo de espacios (múltiples, espacios en blanco, etc.)
 * Más tarde, inserta espacios antes y después - '(', ')', '.'
 * Ahora, después de dividir en un solo espacio (''), se obtienen los tokens.
 */
class TraductorLISP{
    //Crear getters y setters
    String inputLine;
    int num_tokens;
    int currentToken;
    String[] tokens;
    boolean intLimite;
    boolean symbolLimite;
    int count;

    /**
     * @param input El String que contenga el contenido extraido del archivo.txt
     */
    public TraductorLISP(String input){
        inputLine = input;

        // Reemplaza todos los parentesis con un espacio por cada lista de Lisp
        inputLine = inputLine.replaceAll("\\(", " ( ");
        inputLine = inputLine.replaceAll("\\)", " ) ");
        inputLine = inputLine.replaceAll("\\.", " . ");

        // Reemplaza todo tipo de espacion con solo " " en cada uno
        //Siendo mas entendible el oodigo
        inputLine = inputLine.replaceAll("\\s+", " ");
        // Ajusta el texto para que no contenga espacio al principio del sting
        inputLine = inputLine.trim();

        // Tenemos los tokenes identificados al ver que un espacio separa un valor
        tokens = inputLine.split(" ");

        // Cantidad de tokenes y el numero actual
        num_tokens = tokens.length;
        currentToken = 0;

        // Limites para que no excedan
        intLimite = false;
        symbolLimite = false;

        // Cantidad de nodos
        count = 0;
    }

    /**Esta funciona define un matcher de un patron, para determinar la longitud del dato con patrones similiares.
     * @return Un identificador
     */
    // Referencia
    // https://www.tutorialspoint.com/javaregex/javaregex_pattern_compile.htm
    public boolean isIdentifier(){
        // Crear patrones con el fin de encontrar similitudes
        Matcher matchIdentifier = Pattern.compile("[A-Z][A-Z0-9]*").matcher(tokens[currentToken]);
        return matchIdentifier.matches();
    }

    /**
     * @return Se ovtiene el identificador
     * @throws exceptionError Exceptuando cuuando supera el limite de 20
     */
    // Utiliza el token actual como patron
    public SExpression getIdentifier() throws exceptionError{
        String identifier = tokens[currentToken];
        //Compara la longitud de un string
        if(identifier.length() > 20){
            symbolLimite = true;
            throw new exceptionError(getError(), "Parsing");
        }
        currentToken++;
        return SExpression.getTable(identifier);
    }

    /**
     * @return Retorna el token de un ATOM tipo entero
     */
    // Revisa si es un entero
    public boolean isInteger(){
        // Compara patrones de enteros utilizando signos de suma o resta
        Matcher matchInteger = Pattern.compile("[+-]?\\d+").matcher(tokens[currentToken]);
        return matchInteger.matches();
    }

    /**
     * @return Retorna el Token tipo Entero
     * @throws exceptionError Exceptuando cuando supera el limite de 20 numeros
     */
    // Consigue el entero del token actual
    public SExpression getInteger() throws exceptionError {
        String num = tokens[currentToken];
        // Check for length limit
        if(num.length() > 20){
            intLimite = true;
            throw new exceptionError(getError(), "Parsing");
        }
        currentToken++;
        return new SExpression(Integer.parseInt(num));
    }

    /**
     * @return Returna valores expresados con un punto
     */
    // Revisa si es un punto
    public boolean isDot(){
        return tokens[currentToken].equals(".");
    }

    /**
     * @return Identifica si es el parentesis izquierdo dentro de una instruccion.
     */
    // Revisa si es un nodo izquierdo del arbol
    public boolean isLeftBrace(){

        return tokens[currentToken].equals("(");
    }

    /**
     * @return Identifica si es el parentesis derecho para finalzar de guardar una instruccion
     */
    // Revisa si es un nodo derecho del arbol
    public boolean isRightBrace(){

        return tokens[currentToken].equals(")");
    }

    /**
     * @return Retorna la cantidad de token locales con la cantidad creada globalmente
     */
    // Revisa si hay mas tokens que deberan ser enviado por el Parser
    public boolean hasMoreTokens(){
        return currentToken < num_tokens;
    }


    /**
     * SOlo le da skip a un token
     */
    // Salta el token actual
    public void skip(){
        currentToken++;
    }

    /**
     * @return Revisa carateres extranios dentro de una .txt
     */
    // Revisas caracteres invalidos
    public String checkIfInvalid(){
        String found = null;
        // Revisa el patron respecto a estos caracters con el token actual
        Matcher matchInvalidCharacters = Pattern.compile("[~#@$*%{}<>\\[\\]|\"\\_^]").matcher(tokens[currentToken]);
        if(matchInvalidCharacters.find()){
            int start_index = matchInvalidCharacters.start();
            found = Character.toString(tokens[currentToken].charAt(start_index));
        };

        return found;
    }

    /**
     * @return Chequea las instrucciones sean palabras con capitales mayusuclas
     */
    // Revisa si estan con letra minusculas
    public String checkIfLowerCase(){
        String found = null;
        // Compara con letras minusculas el token actual de string
        Matcher matchLowerCaseCharacter = Pattern.compile("[a-z]").matcher(tokens[currentToken]);
        if(matchLowerCaseCharacter.find()){
            int start_index = matchLowerCaseCharacter.start();
            found = Character.toString(tokens[currentToken].charAt(start_index));
        }

        return found;
    }

    /**
     * @return Retorna un mensaje identificado que tipo de error cometio el usuario
     */
    public String getError(){
        String error;
        if(!hasMoreTokens()){
            if(count > 0)
                error = "Falta el parentesis derecho.**";
            else
                error = "Expresion de input terminado inesperadamente.**";
        }
        else if(isDot()){
            error = "Punto inesperado.**";
        }
        else if(isLeftBrace()){
            error = "Parentesis izquierdo inesperado.**";
        }
        else if(isRightBrace()){
            error = "Parentesis derecho extra inesperado.**";
        }
        else if(isLeftBrace()){
            error = "Parentesis izquierdo inesperado.**";
        }
        else if(symbolLimite){
            error = "Límite de longitud del símbolo excedido para el símbolo '" + tokens[currentToken] + "' **";
            symbolLimite = false;
        }
        else if(intLimite){
            error = "Se ha excedido el límite de longitud de entero para entero '" + tokens[currentToken] + "' **";
            intLimite = false;
        }
        else if(checkIfInvalid() != null){
            error = "Caracter invalido '" + checkIfInvalid() + "' encontrado al analizar la expresión de entrada de análisis**";
        }
        else if(checkIfLowerCase() != null){
            error = "Lower Case character '" + checkIfLowerCase() + "' encontrado al analizar la expresión de entrada de análisis **";
        }
        else if(tokens[currentToken].equals("+")){
            error = "Carácter no válido '+' encontrado al analizar. Si desea ingresar un número entero, elimine los espacios.";
        }
        else if(tokens[currentToken].equals("-")){
            error = "Carácter no válido '-' encontrado al analizar. Si desea ingresar un número entero, elimine los espacios.";
        }
        else
            error = "Se encontró un token inesperado'" + tokens[currentToken] + "' al analizar la entrada. Compruebe si ha introducido una expresión de entrada válida. ** ";

        return error;
    }
}
