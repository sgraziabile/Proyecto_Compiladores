package entities;

public class Token {
    private String tokenClass;
    private String lexeme;
    private int lineNumber;

    public Token(String tokenClass, String lexeme, int lineNumber) {
        this.tokenClass = tokenClass;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }
    public String getLexeme() {
        return lexeme;
    }

    public String getTokenClass() {
        return tokenClass;
    }
}
