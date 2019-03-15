/*
Universidad del Valle de Guatemala
Estructura de datos - Seccion 10
traductorLisp.java
Proposito: Traduce la sintaxis de Lisp a String, ayuda al Parser
 */
import java.util.*;

class TraductorLisp{
    //Crear getters y setters
    String inputLine;
    int num_tokens;
    int currentToken;
    String[] tokens;
    boolean intLimite;
    boolean symbolLimite;
    int countBrace;

    public Tokenizer(String input){
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
        countBrace = 0;
    }
    // Referencia
    // https://www.tutorialspoint.com/javaregex/javaregex_pattern_compile.htm
    public boolean isIdentifier(){
        // Crear patrones con el fin de encontrar similitudes
        Matcher matchIdentifier = Pattern.compile("[A-Z][A-Z0-9]*").matcher(tokens[currentToken]);
        return matchIdentifier.matches();
    }

    // Utiliza el token actual como patron
    public SExpression getIdentifier() throws customException{
        String identifier = tokens[currentToken];
        //Compara la longitud de un string
        if(identifier.length() > 20){
            symbolLimite = true;
            throw new customException(getError(), "Parsing");
        }
        currentToken++;
        return SExpression.getFromTable(identifier);
    }

    // Revisa si es un entero
    public boolean isInteger(){
        // Compara patarones de enteros utilizando signos de suma o resta
        Matcher matchInteger= Pattern.compile("[+-]?\\d+").matcher(tokens[currentToken]);
        return matchInteger.matches();
    }

    // Consigue el entero del token actual
    public SExpression getInteger() throws customException {
        String num = tokens[currentToken];
        // Check for length limit
        if(num.length() > 20){
            intLimite = true;
            throw new customException(getError(), "Parsing");
        }
        currentToken++;
        return new SExpression(Integer.parseInt(num));
    }

    // Revisa si es un punto
    public boolean isDot(){
        return tokens[currentToken].equals(".");
    }

    // Revisa si es un nodo izquierdo del arbol
    public boolean isLeftBrace(){
        return tokens[currentToken].equals("(");
    }

    // Revisa si es un nodo derecho del arbol
    public boolean isRightBrace(){
        return tokens[currentToken].equals(")");
    }

    // Revisa si hay mas tokens que deberan ser enviado por el Parser
    public boolean hasMoreTokens(){
        return currentToken < numberOfTokens;
    }


    // Salta el token actual
    public void skipToken(){
        currentToken++;
    }

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
}
