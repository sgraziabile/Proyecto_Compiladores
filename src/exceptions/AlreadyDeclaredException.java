package exceptions;

public class AlreadyDeclaredException extends Exception{
    int errorLine;
    String lexeme;
    String type;
    public AlreadyDeclaredException(String type, int errorLine, String lexeme) {
        this.errorLine = errorLine;
        this.lexeme = lexeme;
        this.type = type;
    }
    public String getMessage() {
        if(type == "class") {
            return getClassMessage();
        }
        else if(type == "method") {
            return getMethodMessage();
        }
        else if(type == "attribute") {
            return getAttributeMessage();
        }
        else if(type == "constructor") {
            return getConstructorMessage();
        }
        return "";
    }
    private String getClassMessage() {
        String message = "Error Semantico en linea " +errorLine+ ":";
        message += " La clase " + lexeme + " ya ha sido declarada \n";
        message += "[Error:"+ lexeme +"|"+ errorLine +"]";
        return message;
    }
    private String getMethodMessage() {
        String message = "Error Semantico en linea " +errorLine+ ":";
        message += " El metodo " + lexeme + " ya ha sido declarado \n";
        message += "[Error:"+ lexeme +"|"+ errorLine +"]";
        return message;
    }
    private String getAttributeMessage() {
        String message = "Error Semantico en linea " +errorLine+ ":";
        message += " El atributo " + lexeme + " ya ha sido declarado \n";
        message += "[Error:"+ lexeme +"|"+ errorLine +"]";
        return message;
    }
    private String getConstructorMessage() {
        String message = "Error Semantico en linea " +errorLine+ ":";
        message += " El constructor " + lexeme + " ya ha sido declarado \n";
        message += "[Error:"+ lexeme +"|"+ errorLine +"]";
        return message;
    }
}
