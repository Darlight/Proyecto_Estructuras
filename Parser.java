/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
Andrés Quan Littow       17652
Mario Andrés             18029
Josué Sagastume          18173
Parser.java
 */

/**
 * Clase parser y lectora de CDR y CAR.
 * Define métodos para leer un código lisp y convertirlo en el correspondiente obj SExp.
 * Utiliza el método de descenso recursivo para analizar la S-Expresión
 * El manejo de errores se realiza y se devuelven los mensajes de error apropiados en caso de que
 */
public class Parser {
    /**
     * Primer parse. Identificador.
     *
     * @param inputBuffer TZT
     * @return e Una SExpresion
     * @throws exceptionError
     */
    public SExpression parse(String inputBuffer) throws exceptionError {

        if (inputBuffer.isEmpty()) {
            throw new exceptionError("No valido. ", "Parser");

        } else {
            TraductorLISP t = new TraductorLISP(inputBuffer);

            SExpression e = parseCar(t, false);

            if (t.hasMoreTokens()) {
                throw new exceptionError("Error", "Parser");
            }
            return e;
        }
    }

    /**
     * Parser para la parte CDR del programa
     *
     * @param t Traductor LISP
     * @param seenLeftBrace Parentesis izquierdo del .txxt
     * @return NIL de Table
     * @throws exceptionError
     */
    public SExpression parseCdr(TraductorLISP t, boolean seenLeftBrace) throws exceptionError {
        // Para parsear CDR
        if (t.hasMoreTokens()) {
            if (t.isRightBrace()) {
                t.skip();
                // Decrementa el tamaño del Brace
                t.count--;
                // La parte izquierda se parsea, por lo que es NIL
                return SExpression.getTable("NIL");
            } else if (t.isDot()) {
                if (seenLeftBrace) {
                    t.skip();


                    // El punto significa que todo lo que le sigue es la parte CDR de la expresión. También puede existir como un int
                    SExpression cdr = parseCar(t, false);


                    // Cuando termina el CDR, se hace un return a ello
                    if (t.hasMoreTokens() && t.isRightBrace()) {
                        t.skip();
                        // Decrementa el tamaño del Brace
                        t.count--;
                        return cdr;
                    } else {
                        // Si no pasa el Brace derecho después de detectar un punto, entonces es inválido el utilizar este input.
                        throw new exceptionError("Error", "Parse");
                    }
                } else {
                    // Si existe un punto, pero no había una parte izquierda, tira error
                    throw new exceptionError("Error", "Parse");
                }
            } else {
                // Separación
                SExpression car = parseCar(t, false);
                SExpression cdr = parseCdr(t, false);
                return new SExpression(car, cdr);
            }
        } else {
            throw new exceptionError("Error", "Parse");
        }
    }


    /**
     * Parte CAR del código
     * @param t TraductorLISP
     * @param seenLeftBrace Parentesis derecho, donde identifica el list.
     * @return NIL de Table
     * @throws exceptionError
     */
    public SExpression parseCar(TraductorLISP t, boolean seenLeftBrace) throws exceptionError {

        if (t.hasMoreTokens()) {
            if (t.isLeftBrace()) {
                // Incrementa...
                t.count++;
                t.skip();

                if (t.isRightBrace()) {
                    t.skip();
                    return SExpression.getTable("NIL");
                }

                // Busca la parte izquierda
                SExpression car = parseCar(t, true);

                // Encuentra si es CDR
                if (t.hasMoreTokens()) {
                    SExpression cdr = parseCdr(t, true);
                    return new SExpression(car, cdr);
                }

                return car;

            } else if (t.isRightBrace()) {
                // Decrementa...
                t.count--;
                if (seenLeftBrace) {
                    t.skip();
                    return SExpression.getTable("NIL");
                } else {
                    // Error si no existe una parte izquierda
                    throw new exceptionError("Error", "Parse");
                }
            } else if (t.isIdentifier()) {
                // Retorna la expresión
                SExpression SExp = t.getIdentifier();
                return SExp;
            } else if (t.isInteger()) {
                // Integer para la expresión
                SExpression SExp = t.getInteger();
                return SExp;
            } else {
                // Error si las condiciones no funcionan
                throw new exceptionError("Error", "Parse");
            }
        } else {
            // Inválido si existen más instrucciones pero no más tokens
            throw new exceptionError("Error", "Parse");
        }
    }

}