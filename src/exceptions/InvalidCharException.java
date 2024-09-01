package exceptions;

public class InvalidCharException extends LexicalException{

    public InvalidCharException(String lexeme, int lineNumber, String currentLine, int columnNumber) {
        super(lexeme, lineNumber,currentLine,columnNumber);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + getLineNumber() +": columna: "+columnNumber+" "+getLexeme()+" no es un char valido \n";
        message +="Detalle: "+ getCurrentLine() + "\n";
        message += printArrow();
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }
    protected String printArrow() {
        String arrow = "";
        for(int i = 0; i < columnNumber+ "Detalle: ".length()-1; i++) {
            arrow += " ";
        }
        arrow += "^\n";
        return arrow;
    }
}
