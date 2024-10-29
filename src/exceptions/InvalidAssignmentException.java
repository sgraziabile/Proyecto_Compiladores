package exceptions;

public class InvalidAssignmentException extends Exception {
    private int line;
    private String operator;
    public InvalidAssignmentException(int line, String operator) {
        this.line = line;
        this.operator = operator;
    }
    public String getMessage() {
        String message = "Error Semantico en la linea " + line + ": Se esperaba una variable en la asignacion \n";
        message += "[Error"+operator+"|"+line+"]";
        return message;
    }
}
