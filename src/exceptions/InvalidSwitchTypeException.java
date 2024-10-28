package exceptions;

import semantic.declared_entities.Type;

public class InvalidSwitchTypeException extends Exception {
    private Type type;
    private int lineNumber;

    public InvalidSwitchTypeException(Type type, int lineNumber) {
        this.type = type;
        this.lineNumber = lineNumber;
    }
    public String getMessage() {
        String message = "Error Semantico en la linea " + lineNumber + ": ";
        message += "Tipo invalido para switch: " + type.getName() + "\n";
        message += "[Error:switch|" + lineNumber + "]";
        return message;
    }
}
