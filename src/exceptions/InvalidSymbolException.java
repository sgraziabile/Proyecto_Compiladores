package exceptions;

public class InvalidSymbolException extends LexicalException{
    public InvalidSymbolException(String lexeme, int lineNumber, String currentLine) {
        super(lexeme, lineNumber, currentLine);
    }
    public String getMessage() {
        String message = "Error lexico en linea: " + getLineNumber() +": " + getLexeme() + " no es un símbolo válido \n";
        message +="Detalle: "+ getCurrentLine() + "\n";
        message += super.printArrow();
        message += "[Error:" +getLexeme()+ "|"+getLineNumber()+"]";
        return message;
    }

}
