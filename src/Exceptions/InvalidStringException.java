package Exceptions;

public class InvalidStringException extends LexicalException {
    public InvalidStringException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + getLineNumber() + ": " + getLexeme() + " string invalido \n";
        int arrowPosition = "Error lexico en la linea:".length() + String.valueOf(getLineNumber()).length() + getLexeme().length() - 2;
        for (int i = 0; i < arrowPosition; i++) {
            message += " ";
        }
        message += "^\n";
        message += "[Error:" + getLexeme() + "|" + getLineNumber() + "]";
        return message;
    }
}
