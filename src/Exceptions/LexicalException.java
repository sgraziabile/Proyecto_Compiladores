package Exceptions;

abstract class LexicalException extends Exception {
    String lexeme;
    int lineNumber;
    public LexicalException(String lexeme, int lineNumber) {
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }
    public String getLexeme() {
        return lexeme;
    }
    public int getLineNumber() {
        return lineNumber;
    }
}
