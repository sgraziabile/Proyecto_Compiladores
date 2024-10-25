package exceptions;

import entities.Token;

public class CannotResolveMethodException extends Exception{
    Token method;

    public CannotResolveMethodException(Token method){
        this.method = method;
    }

    public String getMessage(){
        String message = "Error Semantico en linea " + method.getLineNumber() + ":";
        message += " No se puede resolver el metodo " + method.getLexeme() + "\n";
        message += "[Error:" + method.getLexeme() + "|" + method.getLineNumber() + "]";
        return message;
    }

}
