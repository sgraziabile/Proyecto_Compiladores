package exceptions;

abstract class LexicalException extends Exception {
    String lexeme;
    int lineNumber;
    String currentLine;
    int columnNumber;
    public LexicalException(String lexeme, int lineNumber, String currentLine,int columnNumber) {
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
        this.currentLine = currentLine;
        this.columnNumber = columnNumber;
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
        for(int i = 0; i < columnNumber+"Detalle: ".length()-1; i++) {
            arrow += " ";
        }
        arrow += "^\n";
        return arrow;
    }
}
