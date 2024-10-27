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
        switch (type) {
            case "class" -> {
                return getClassMessage();
            }
            case "method" -> {
                return getMethodMessage();
            }
            case "attribute" -> {
                return getAttributeMessage();
            }
            case "constructor" -> {
                return getConstructorMessage();
            }
            case "parameter" -> {
                return getParameterMessage();
            }
            case "local_var" -> {
                return getLocalVarMessage();
            }
            default -> {
                return "";
            }
        }
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
    private String getParameterMessage() {
        String message = "Error Semantico en linea " +errorLine+ ":";
        message += " El parametro " + lexeme + " ya fue definido en este alcance \n";
        message += "[Error:"+ lexeme +"|"+ errorLine +"]";
        return message;
    }
    private String getLocalVarMessage() {
        String message = "Error Semantico en linea " +errorLine+ ":";
        message += " La variable local " + lexeme + " ya fue definida en este alcance \n";
        message += "[Error:"+ lexeme +"|"+ errorLine +"]";
        return message;
    }
}
