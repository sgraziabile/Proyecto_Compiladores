package exceptions;

public class InvalidStringException extends LexicalException {
    public InvalidStringException(String lexeme, int lineNumber,String currentLine,int columnNumber) {
        super(lexeme, lineNumber,currentLine,columnNumber);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + lineNumber + " columna: "+columnNumber+" "+lexeme+ " String invalido \n";
        message += "Detalle: " +getCurrentLine()+"\n";
        message += printArrow();
        message += "[Error:" + getLexeme() + "|" + getLineNumber() + "]";
        return message;
    }
    protected String printArrow() {
        String arrow = "";
        for(int i = 0; i < columnNumber + "Detalle: ".length(); i++) {
            arrow += " ";
        }
        arrow += "^\n";
        return arrow;
    }
}
