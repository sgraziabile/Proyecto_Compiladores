package exceptions;

public class MainNotDeclaredException extends Exception{
    public MainNotDeclaredException() {

    }
    public String getMessage() {
        String message = "Error Semantico: ";
        message += "No se ha declarado el metodo main\n";
        message += "[Error:main|1]";
        return message;
    }
}
