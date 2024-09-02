package exceptions;

public class InvalidFloatException extends LexicalException{

    public InvalidFloatException(String lexeme, int lineNumber, String currentLine, int columnNumber) {
        super(lexeme, lineNumber,currentLine,columnNumber);
    }
    public String getMessage() {
        columnNumber++;
        String message = "Error lexico en linea: " + getLineNumber() +" columna: "+columnNumber+" "+ getLexeme() + " no es un float valido \n";
        message +="Detalle: "+ getCurrentLine() + "\n";
        message += printArrow();
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }
    protected String printArrow() {
        String arrow = "";
        for(int i = 0; i < columnNumber-2+ "Detalle: ".length(); i++) {
            arrow += " ";
        }
        arrow += "^\n";
        return arrow;
    }
}
