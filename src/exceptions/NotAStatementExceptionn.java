package exceptions;

public class NotAStatementExceptionn extends Exception{
    private int line;
    public NotAStatementExceptionn(int line) {
        this.line = line;
    }
    public String getMessage() {
        String message = "Error Semantico en la linea " + line + ": No es una sentencia \n";
        message += "[Error:;|"+line+"]";
        return message;
    }
}
