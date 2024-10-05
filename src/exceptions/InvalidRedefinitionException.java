package exceptions;

public class InvalidRedefinitionException extends Exception{
    private int errorLine;
    private String lexeme;

    public InvalidRedefinitionException(String lexeme, int errorLine) {
        this.errorLine = errorLine;
        this.lexeme = lexeme;
    }
    public String getMessage() {
        String message = "Error Semantico en linea " +errorLine+ ":";
        message += " La redefinicion de " + lexeme + " no es valida \n";
        message += "[Error:"+ lexeme +"|"+ errorLine +"]";
        return message;
    }
}
