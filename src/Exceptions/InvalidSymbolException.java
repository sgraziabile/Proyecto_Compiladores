package Exceptions;

public class InvalidSymbolException extends Exception{
    String lexeme;
    int lineNumber;
    public InvalidSymbolException(String lexeme, int lineNumber) {
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + lineNumber +": " + lexeme + " no es un símbolo válido \n";
        message += "[Error:" +lexeme+ "|"+lineNumber+"]";
        return message;
    }
}
