package exceptions;

public class RepeatedDefaultCaseException extends Exception {
    private int errorLine;

    public RepeatedDefaultCaseException(int errorLine) {
        this.errorLine = errorLine;
    }
    public String getMessage() {
        String message = "Error Semantico en la linea " + errorLine + ": Caso default repetido \n";
        message += "[Error:default|" + errorLine + "]";
        return message;
    }
}
