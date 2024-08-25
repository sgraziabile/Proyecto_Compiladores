package lexical;

import entities.Token;
import sourcemanager.SourceManagerImpl;

import java.io.IOException;

public class LexicalAnalyzer {
    String lexeme;
    char currentChar;
    SourceManagerImpl sourceManager;

    public LexicalAnalyzer(SourceManagerImpl sourceManager) {
        this.sourceManager = sourceManager;
        updateCurrentChar();
    }
    public Token nextToken() {
        lexeme = "";
        return e0();
    }
    private void updateLexeme() {
        lexeme += currentChar;
    }
    private void updateCurrentChar() {
        try {
            currentChar = sourceManager.getNextChar();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private Token e0() {
        return new Token("END_OF_FILE", "", sourceManager.getLineNumber());
    }
}
