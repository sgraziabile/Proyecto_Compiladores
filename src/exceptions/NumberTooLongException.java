package exceptions;

public class NumberTooLongException extends LexicalException{
    public NumberTooLongException(String lexeme, int lineNumber, String currentLine, int columnNumber) {
        super(lexeme, lineNumber, currentLine, columnNumber);
    }
    public String getMessage() {
        String message = "Error léxico en linea: " + getLineNumber() +": columna: "+columnNumber+" "+ getLexeme() + " número muy largo \n";
        message +="Detalle: "+ getCurrentLine() + "\n";
        message += printArrow();
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }
    protected String printArrow() {
        String arrow = "";
        for(int i = 0; i < columnNumber + "Detalle: ".length()-1; i++) {
            arrow += " ";
        }
        arrow += "^\n";
        return arrow;
    }
}
