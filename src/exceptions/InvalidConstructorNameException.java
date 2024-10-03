package exceptions;

public class InvalidConstructorNameException extends Exception {
    private int errorLine;
    private String lexeme;
    public InvalidConstructorNameException(int lineNumber, String lexeme) {
        this.errorLine = lineNumber;
        this.lexeme = lexeme;
    }
    public String getMessage() {
        String message = "Error Semantico en linea " +errorLine+ ": ";
        message += "Nombre de constructor incorrecto: " + lexeme + "\n";
        message += "[Error:"+ lexeme +"|"+ errorLine +"]";
        return message;
    }
}
