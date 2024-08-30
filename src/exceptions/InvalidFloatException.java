package exceptions;

public class InvalidFloatException extends LexicalException{

    public InvalidFloatException(String lexeme, int lineNumber, String currentLine) {
        super(lexeme, lineNumber,currentLine);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + getLineNumber() +": " + getLexeme() + " no es un float valido \n";
        message +="Detalle: "+ getCurrentLine() + "\n";
        message += super.printArrow();
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }
}
