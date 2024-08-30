package Exceptions;

public class IllegalCommentException extends Exception {
    int lineNumber;
    public IllegalCommentException(int lineNumber) {
        this.lineNumber = lineNumber;
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + lineNumber +": comentario no cerrado \n";
        message += "[Error:|"+lineNumber+"]";
        return message;
    }
}
