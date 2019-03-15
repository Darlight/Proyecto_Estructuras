/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
InterpreteLISP.java
Proposito:
 */
public class Parser {
    public SExpression parse(String inputBuffer) throws customException {

        if (inputBuffer.isEmptu()) {
            throw new customException("No valido. ");

        } else {
            Tokenizer t = new Tokenizer(inputBuffer);

            SExpression e = parseCar(tokenizer, false);

            if (tokenizer.hasMoreTokens()) {
                throw new customException("Error");
            }
            return e;
        }
    }

    public SExpression parseCdr(Tokenizer t, boolean s) throws customException {
        if (t.hasMoreTokens()) {
            if (t.isRightBrace()) {
                t.skip();

                t.count--;
                return SExpression.getTable("NIL");
            } else if (t.isDot()) {
                if (s) {
                    t.skip();
                    SExpresion fr = parseCar(t, false);
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

            }
            else{
                System.out.println("Error");
            }
        }
    }


    //Finished it :3
    public SExpression parseCar(Tokenizer t, boolean s) throws customException {
        if(t.isLeftBrace()){
            tokenizer.count++; //Ca
            t.skip();

            if(t.isRightBrace()){
                t.skip();
                return SExpression.getTable("NIL");
            }

            SExpression c2 = parseCar(t, true);

            if(t.hasMoreTokens()){
                SExpression c0 = parseCdr(t, true);

                return new SExpression(c1, c0);
            }
            return c2;
        } else if (t.isRightBrace()) {
            t.counter--;
            if(seenLeftBrace){
                t.skip();
                return SExpression.getTable("NIL");
            }
            else{
                System.out.println("RError");
            }

        }
        else if(t.isIdentifier()){
            SExpression exp = t.getIdentifier();
            return exp;
        }

        else if(t.isInteger()){
            SExpression exp = t.getInteger();
            return exp;
        }

        else {
            System.out.println("Error");
        }

    }
    else{
        System.out.println("Error");
    }
}