package exceptions;

public class InvalidCharException extends LexicalException{

    public InvalidCharException(String lexeme, int lineNumber, String currentLine) {
        super(lexeme, lineNumber,currentLine);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + getLineNumber() +": " + getLexeme() + " no es un char valido \n";
        message +="Detalle: "+ getCurrentLine() + "\n";
        message += printArrow();
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }
    protected String printArrow() {
        String arrow = "";
        for(int i = 0; i < getErrorPosition()+ "Detalle: ".length()+1; i++) {
            arrow += " ";
        }
        arrow += "^\n";
        return arrow;
    }
}
