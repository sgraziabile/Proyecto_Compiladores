package exceptions;

public class CantResolveSymbolException extends Exception {
    int errorLine;
    String lexeme;
    public CantResolveSymbolException(int errorLine, String lexeme) {
        this.errorLine = errorLine;
        this.lexeme = lexeme;
    }
    public String getMessage() {
        String message = "Error Semantico en linea " + errorLine + ":";
        message += " No se puede resolver el simbolo " + lexeme + "\n";
        message += "[Error:" + lexeme + "|" + errorLine + "]";
        return message;
    }
}
