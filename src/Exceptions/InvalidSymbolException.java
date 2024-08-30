package Exceptions;

public class InvalidSymbolException extends LexicalException{
    public InvalidSymbolException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + getLineNumber() +": " + getLexeme() + " no es un símbolo válido \n";
        int arrowPosition = "Error lexico en la linea:".length() + String.valueOf(getLineNumber()).length() + getLexeme().length()-1;
        for(int i = 0; i < arrowPosition; i++) {
            message += " ";
        }
        message += "^\n";
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }
}
