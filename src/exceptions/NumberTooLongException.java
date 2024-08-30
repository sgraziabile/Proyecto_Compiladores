package exceptions;

public class NumberTooLongException extends LexicalException{
    public NumberTooLongException(String lexeme, int lineNumber, String currentLine) {
        super(lexeme, lineNumber, currentLine);
    }
    public String getMessage() {
        String message = "Error léxico en linea: " + getLineNumber() +": " + getLexeme() + " número muy largo \n";
        message +="Detalle: "+ getCurrentLine() + "\n";
        message += printArrow();
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }
    protected String printArrow() {
        String arrow = "";
        for(int i = 0; i < lexeme.length() + "Detalle: ".length()-1; i++) {
            arrow += " ";
        }
        arrow += "^\n";
        return arrow;
    }
}
