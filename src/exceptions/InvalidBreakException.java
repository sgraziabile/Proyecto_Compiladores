package exceptions;

public class InvalidBreakException extends Exception{
    private int line;
    public InvalidBreakException(int line) {
        this.line = line;
    }
    public String getMessage() {
        String message = "Error Semantico en la linea " + line + ": break fuera de loop \n";
        message += "[Error:break|"+line+"]";
        return message;
    }
}
