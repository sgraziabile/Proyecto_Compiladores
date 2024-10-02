package exceptions;

public class ClassAlreadyDeclaredException extends Exception{
    int errorLine;
    String lexeme;
    public ClassAlreadyDeclaredException(int errorLine,String lexeme) {
        this.errorLine = errorLine;
        this.lexeme = lexeme;

    }
    public String getMessage() {
        String message = "Error Semantico en linea: " +errorLine+ " ";
        message += "La clase " + lexeme + " ya ha sido declarada \n";
        message += "[Error:"+ lexeme +"|"+ errorLine +"]";
        return message;
    }
}
