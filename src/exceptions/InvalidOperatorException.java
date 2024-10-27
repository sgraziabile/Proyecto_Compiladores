package exceptions;

public class InvalidOperatorException extends Exception{
    private String operator;
    private String type1;
    private String type2;
    private int errorLine;
    public InvalidOperatorException(String operator, String type1, String type2, int errorLine) {
        this.operator = operator;
        this.type1 = type1;
        this.type2 = type2;
        this.errorLine = errorLine;

    }
    public String getMessage() {
        String message = "Error Semantico en linea " +errorLine+ ": ";
        message += "El operador "+operator+" no se puede aplicar a los tipos " + type1 + " : " + type2 + "\n";
        message += "[Error: "+operator+"|"+ errorLine +"]";
        return message;
    }
}
