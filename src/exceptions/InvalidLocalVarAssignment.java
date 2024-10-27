package exceptions;

public class InvalidLocalVarAssignment extends Exception {
    private int errorLine;
    public InvalidLocalVarAssignment(int errorLine) {
        this.errorLine = errorLine;
    }
    public String getMessage() {
        String message = "Error Semantico en linea " +errorLine+ ": ";
        message += "No se puede asignar el valor null a una variable local \n";
        message += "[Error: null|"+ errorLine +"]";
        return message;
    }
}
