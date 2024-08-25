package Exceptions;

public class LexicalException extends Exception{
    String lexeme = " ";
    int lineNumber = 0;
    public LexicalException(String lexeme, int lineNumber) {
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + lineNumber +": " + lexeme + " no es un simbolo valido \n";
        message += "[Error:" +lexeme+ "|"+lineNumber+"]";
        return message;
    }
}
