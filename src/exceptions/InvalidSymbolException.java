package exceptions;

public class InvalidSymbolException extends LexicalException{
    public InvalidSymbolException(String lexeme, int lineNumber, String currentLine,int columnNumber) {
        super(lexeme, lineNumber, currentLine, columnNumber);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + getLineNumber() +" columa: "+columnNumber+": " + getLexeme() + " no es un símbolo válido \n";
        message +="Detalle: "+ getCurrentLine() + "\n";
        message += super.printArrow();
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }

}
