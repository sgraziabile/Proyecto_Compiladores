package exceptions;

public class MissingReturnStatementException extends Exception {
    private String symbol;
    private int lineNumber;

    public MissingReturnStatementException(String symbol, int lineNumber) {
        this.symbol = symbol;
        this.lineNumber = lineNumber;
    }
    public String getMessage() {
        String message = "Error Semantico en linea " +lineNumber+ "\n";
        message += "Sentencia de retorno vacia \n";
        message += "[Error:"+symbol+"|"+ lineNumber +"]";
        return message;
    }
}
