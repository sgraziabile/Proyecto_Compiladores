package lexical;

import Exceptions.LexicalException;
import entities.KeywordHandler;
import entities.Token;
import sourcemanager.SourceManagerImpl;
import java.io.IOException;
import java.util.HashMap;

import static sourcemanager.SourceManager.END_OF_FILE;

public class LexicalAnalyzer {
    String lexeme;
    char currentChar;
    SourceManagerImpl sourceManager;
    KeywordHandler keywordHandler;

    public LexicalAnalyzer(SourceManagerImpl sourceManager, KeywordHandler keywordHandler) {
        this.sourceManager = sourceManager;
        this.keywordHandler = keywordHandler;
        updateCurrentChar();
    }
    public Token nextToken() throws Exception{
        lexeme = "";
        try {
            return e0();
        } catch (Exception e) {
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        }
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
    private Token e0() throws Exception {
        if(Character.isDigit(currentChar)) {
            updateLexeme();
            updateCurrentChar();
            return e9();
        }
        else if(Character.isUpperCase(currentChar)) {
            updateLexeme();
            updateCurrentChar();
            return e10();
        }
        else if(Character.isLowerCase(currentChar)) {
            updateLexeme();
            updateCurrentChar();
            return e11();
        }
        else if(currentChar == END_OF_FILE) {
            return eFin();
        }
        else if(Character.isWhitespace(currentChar) || currentChar == '\n' || currentChar == '\t') {
            updateCurrentChar();
            return e0();
        }
        else {
            updateLexeme();
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        }
    }
    private Token e9() {
        if (Character.isDigit(currentChar)) {
            updateLexeme();
            updateCurrentChar();
            return e9();
        } else {
            return new Token("intLiteral", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token e10() {
        if(Character.isDigit(currentChar) || Character.isLetter(currentChar) || currentChar == '_') {
            updateLexeme();
            updateCurrentChar();
            return e10();
        } else {
            return new Token("idClase", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token e11() {
        if(Character.isDigit(currentChar) || Character.isLetter(currentChar) || currentChar == '_') {
            updateLexeme();
            updateCurrentChar();
            return e11();
        } else {
            if(keywordHandler.isKeyword(lexeme)) {
                return new Token("keyword", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("idMetVar", lexeme, sourceManager.getLineNumber());
            }
        }
    }
    private Token eFin() {
        return new Token("EOF",lexeme, sourceManager.getLineNumber());
    }
}
