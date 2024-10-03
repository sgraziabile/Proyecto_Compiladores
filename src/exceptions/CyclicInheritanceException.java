package exceptions;

public class CyclicInheritanceException extends Exception {
    int errorLine;
    String lexeme;
    public CyclicInheritanceException(int errorLine, String lexeme) {
        this.errorLine = errorLine;
        this.lexeme = lexeme;
    }
    public String getMessage() {
        String message = "Error Semantico en linea " + errorLine + ":";
        message += " Herencia ciclica en la clase " + lexeme + "\n";
        message += "[Error:" + lexeme + "|" + errorLine + "]";
        return message;
    }
}
