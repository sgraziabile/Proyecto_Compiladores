package exceptions;

public class InvalidStringException extends LexicalException {
    public InvalidStringException(String lexeme, int lineNumber,String currentLine) {
        super(lexeme, lineNumber,currentLine);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + getLineNumber() + ": " + getLexeme() + " string invalido \n";
        message += "Detalle: " +getCurrentLine()+"\n";
        message += printArrow();
        message += "[Error:" + getLexeme() + "|" + getLineNumber() + "]";
        return message;
    }
    protected String printArrow() {
        String arrow = "";
        for(int i = 0; i < lexeme.length()-1 + "Detalle: ".length()-1; i++) {
            arrow += " ";
        }
        arrow += "^\n";
        return arrow;
    }
}
