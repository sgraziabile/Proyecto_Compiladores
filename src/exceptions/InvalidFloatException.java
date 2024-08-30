package exceptions;

public class InvalidFloatException extends LexicalException{

    public InvalidFloatException(String lexeme, int lineNumber, String currentLine) {
        super(lexeme, lineNumber,currentLine);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + getLineNumber() +": " + getLexeme() + " no es un float valido \n";
        message +="Detalle: "+ getCurrentLine() + "\n";
        message += printArrow();
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }
    protected String printArrow() {
        String arrow = "";
        for(int i = 0; i < lexeme.length()-1 + "Detalle: ".length(); i++) {
            arrow += " ";
        }
        arrow += "^\n";
        return arrow;
    }
}
