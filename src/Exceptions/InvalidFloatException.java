package Exceptions;

public class InvalidFloatException extends LexicalException{

    public InvalidFloatException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + getLineNumber() +": " + getLexeme() + " no es un float valido \n";
        int arrowPosition = "Error lexico en la linea:".length() + String.valueOf(getLineNumber()).length();
        for(int i = 0; i < arrowPosition; i++) {
            message += " ";
        }
        message += "^\n";
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }
}
