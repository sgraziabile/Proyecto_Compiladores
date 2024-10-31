package exceptions;

public class RepeatedCaseLabelException extends Exception{
    private int errorLine;
    private String label;

    public RepeatedCaseLabelException(int errorLine, String label) {
        this.errorLine = errorLine;
        this.label = label;
    }
    public String getMessage() {
        String message = "Error Semantico en la linea " + errorLine + ": Etiqueta duplicada: "+label+ "\n";
        message += "[Error:" + label + "|" + errorLine + "]";
        return message;
    }
}
