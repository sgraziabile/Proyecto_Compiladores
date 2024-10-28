package exceptions;

public class StaticReferenceException extends Exception{
    private String token;
    private int lineNumber;

    public StaticReferenceException(int lineNumber, String token) {
        this.lineNumber = lineNumber;
        this.token = token;
    }
    public String getMessage() {
        String message = "Error Semantico en la linea " + lineNumber + ": No se puede hacer referencia a un miembro no estatico desde un contexto estatico\n";
        message += "[Error:" + token + "|" + lineNumber + "]";
        return message;
    }
    public String getToken() {
        return token;
    }
}
