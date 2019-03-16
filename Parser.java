/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */
public class Parser {
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

    public SExpression parseCdr(TraductorLISP t, boolean seenLeftBrace) throws exceptionError{
        // For parsing cdr part of input expression
        if(t.hasMoreTokens()){
            if(t.isRightBrace()){
                t.skip();
                // Decrement count of open brace
                t.count--;
                // Left part is parsed and right brace occurs which means it is NIL
                return SExpression.getTable("NIL");
            }
            else if(t.isDot()){
                if(seenLeftBrace){
                    t.skip();

                    // A dot means rest of the part is cdr part of the SExpression
                    // It can be an integer, symbolic atom or a new non-atomic Sexpression so call parseCar to parse it
                    SExpression cdr = parseCar(t, false);

                    // Once the cdr brace occurs, return the above cdr expression
                    if(t.hasMoreTokens() && t.isRightBrace()){
                        t.skip();
                        // Decrement count of open brace
                        t.count--;
                        return cdr;
                    }
                    else{
                        // If a right brace doesn't occur after parsing input after '.', input is invalid
                        throw new exceptionError("Error", "Parse");
                    }
                }
                else{
                    // If a dot occurs without previously seen left brace, input is invalid
                    throw new exceptionError("Error", "Parse");
                }
            }
            else{
                // If no dot or right brace occurs, parse rest of the input as new input having both car and cdr
                SExpression car = parseCar(t, false);
                SExpression cdr = parseCdr(t, false);
                return new SExpression(car, cdr);
            }
        }
        else{
            // If no more tokens left and still parseCdr is called, input is invalid
            throw new exceptionError("Error", "Parse");
        }
    }
    /*
    public SExpression parseCdr(TraductorLISP t, boolean s) throws exceptionError {
        if (t.hasMoreTokens()) {
            if (t.isRightBrace()) {
                t.skip();

                t.count--;
                return SExpression.getTable("NIL");
            } else if (t.isDot()) {
                if (s) {
                    t.skip();
                    SExpression fr = parseCar(t, false);
                    if (t.isRightBrace() && t.hasMoreTokens()) {
                        t.skip();
                        t.count--;
                        return fr;
                    } else {
                        System.out.println("Error");

                    }

                } else {
                    System.out.println("Error");
                }
            } else {
                SExpression c1 = parseCar(t, false);
                SExpression c2 = parseCdr(t, false);
                return new SExpression(c1, c2);

            }

        }
        else{
            throw new exceptionError("error", "Parser");
        }
        return
    }

    */



    
    public SExpression parseCar(TraductorLISP t, boolean seenLeftBrace) throws exceptionError {

        if (t.hasMoreTokens()) {
            if (t.isLeftBrace()) {
                // Increment count of open brace
                t.count++;
                t.skip();

                if (t.isRightBrace()) {
                    t.skip();
                    return SExpression.getTable("NIL");
                }

                // Called with seenLeftBrace = true since a brace is seen
                SExpression car = parseCar(t, true);

                // Otherwise parse rest of the input as Cdr
                if (t.hasMoreTokens()) {
                    SExpression cdr = parseCdr(t, true);

                    // Form new Sexpression with returned Car and Cdr parts
                    return new SExpression(car, cdr);
                }

                return car;

            } else if (t.isRightBrace()) {
                // Decrement count of open brace
                t.count--;
                if (seenLeftBrace) {
                    t.skip();
                    return SExpression.getTable("NIL");
                } else {
                    // If right brace occurs without a previously seen left brace, throw error
                    throw new exceptionError("Error", "Parse");
                }
            } else if (t.isIdentifier()) {
                // Return SExpression for corresponding identifier
                SExpression SExp = t.getIdentifier();
                return SExp;
            } else if (t.isInteger()) {
                // Get SExpression for corresponding integer
                SExpression SExp = t.getInteger();
                return SExp;
            } else {
                // If not symbol, integer, left brace or right brace with a previous seen left brace, input is invalid
                throw new exceptionError("Error", "Parse");
            }
        } else {
            // If no more tokens left and still parseCar is called, input is invalid
            throw new exceptionError("Error", "Parse");
        }
    }

}