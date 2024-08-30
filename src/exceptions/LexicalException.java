package exceptions;

abstract class LexicalException extends Exception {
    String lexeme;
    int lineNumber;
    String currentLine;
    public LexicalException(String lexeme, int lineNumber, String currentLine) {
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
        this.currentLine = currentLine;
    }
    public String getLexeme() {
        return lexeme;
    }
    public int getLineNumber() {
        return lineNumber;
    }
    public String getCurrentLine() {
        return currentLine;
    }
    protected int getErrorPosition() {
        return currentLine.indexOf(lexeme);
    }
    protected String printArrow() {
        String arrow = "";
        for(int i = 0; i < getErrorPosition()+ +"Detalle: ".length(); i++) {
            arrow += " ";
        }
        arrow += "^\n";
        return arrow;
    }
}
