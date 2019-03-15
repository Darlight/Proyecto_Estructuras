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


    //FINISH THIS
    public SExpression parseCar(Tokenizer t, boolean s) throws customException {
        if(t.isLeftBrace()){
            tokenizer.count++;
            t.skip();

            if(t.isRightBrace()){
                t.skip();
            }
        }
    }
}