package Exceptions;

public class NumberTooLongException extends LexicalException{
    public NumberTooLongException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }
    public String getMessage() {
        String message = "Error léxico en linea: " + getLineNumber() +": " + getLexeme() + " número muy largo \n";
        int arrowPosition = "Error léxico en la linea:".length() + String.valueOf(getLineNumber()).length() + getLexeme().length()-1;
        for(int i = 0; i < arrowPosition; i++) {
            message += " ";
        }
        message += "^\n";
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }
}
