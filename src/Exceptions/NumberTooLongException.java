package Exceptions;

public class NumberTooLongException extends Exception{
    String lexeme;
    int lineNumber;
    public NumberTooLongException(String lexeme, int lineNumber) {
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + lineNumber +": " + lexeme + " es un número muy largo \n";
        message += "[Error:" +lexeme+ "|"+lineNumber+"]";
        return message;
    }
}
