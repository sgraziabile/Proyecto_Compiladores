package exceptions;

public class VariableNotInitializedException extends Exception{
    private int lineNumber;
    private String variableName;

    public VariableNotInitializedException(String variableName, int lineNumber) {
        this.variableName = variableName;
        this.lineNumber = lineNumber;
    }
    public String getMessage() {
        String message = "Error Semantico en la linea " + lineNumber + ": La variable " + variableName + " puede no haber sido inicializada. \n";
        message += "[Error:"+variableName+"|"+lineNumber+"]";
        return message;
    }
}
