package exceptions;

import semantic.declared_entities.Type;

public class IncompatibleTypesException extends Exception {
    private Type type1;
    private Type type2;
    private int lineNumber;
    private String expression;
    public IncompatibleTypesException(Type type1, Type type2, int lineNumber, String expression) {
        this.type1 = type1;
        this.type2 = type2;
        this.lineNumber = lineNumber;
        this.expression = expression;
    }
    public String getMessage() {
        String message = "Error Semantico en linea " +lineNumber+ ": ";
        message += "Tipos incompatibles: " + type1.getName() + " : " + type2.getName() + "\n";
        message += "[Error:"+expression+"|"+ lineNumber +"]";
        return message;
    }

}
