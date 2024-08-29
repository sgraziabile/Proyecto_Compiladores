package Exceptions;

public class InvalidFloatException extends Exception{
    String lexeme;
    int lineNumber;
    public InvalidFloatException(String lexeme, int lineNumber) {
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + lineNumber +": " + lexeme + " no es un float valido \n";
        message += "[Error:" +lexeme+ "|"+lineNumber+"]";
        return message;
    }
}
