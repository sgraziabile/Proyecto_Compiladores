package exceptions;

import entities.Token;
import semantic.declared_entities.Type;

public class PrimitiveTypeCallException extends Exception {
    private Token methodName;
    private Token parentName;
    private Type primitiveType;

    public PrimitiveTypeCallException(Token methodName, Token parentName, Type primitiveType) {
        this.methodName = methodName;
        this.parentName = parentName;
        this.primitiveType = primitiveType;
    }
    public String getMessage() {
        String message = "Error Semantico en linea " + methodName.getLineNumber() + ":";
        message += " No se puede llamar a "+methodName.getLexeme()+" porque "+ parentName.getLexeme()+" tiene tipo primitivo " + primitiveType.getName() + "\n";
        message += "[Error:" + methodName.getLexeme() + "|" + methodName.getLineNumber() + "]";
        return message;
    }

}
