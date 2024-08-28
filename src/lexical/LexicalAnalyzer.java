package lexical;

import Exceptions.LexicalException;
import entities.KeywordHandler;
import entities.Token;
import sourcemanager.SourceManagerImpl;
import java.io.IOException;

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
        else if(currentChar == '"'){
            updateLexeme();
            updateCurrentChar();
            return e17();
        }
        else if(currentChar == '*') {
            updateLexeme();
            updateCurrentChar();
            return e23();
        }
        else if(currentChar == '/') {
            updateLexeme();
            updateCurrentChar();
            return e24();
        }
        else if(currentChar == '>') {
            updateLexeme();
            updateCurrentChar();
            return eMayor();
        }
        else if(currentChar == '<') {
            updateLexeme();
            updateCurrentChar();
            return eMenor();
        }
        else if(currentChar == '!') {
            updateLexeme();
            updateCurrentChar();
            return eNegacion();
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
    private Token e17() {
        if(currentChar == '"') {
            updateLexeme();
            updateCurrentChar();
            return e22();
        }
        else if(currentChar == '\\') {
            updateLexeme();
            updateCurrentChar();
            return e22();
        } else {
            updateLexeme();
            updateCurrentChar();
            return e17();
        }
    }
    private Token e22() {
        return new Token("stringLiteral", lexeme, sourceManager.getLineNumber());
    }
    private Token e23() {
        return new Token("opMult", lexeme, sourceManager.getLineNumber());
    }
    private Token e24() throws Exception{
        if(currentChar == '/') {
            lexeme = "";
            updateCurrentChar();
            try {
                return e34();
            } catch (Exception e) {
                throw new LexicalException(lexeme, sourceManager.getLineNumber());
            }
        }
        else if(currentChar == '*') {
            lexeme = "";
            updateCurrentChar();
            try {
                return e32();
            }catch (Exception e) {
                throw new LexicalException(lexeme, sourceManager.getLineNumber());
            }
        } else {
            return new Token("opDiv", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eMayor() {
        if(currentChar == '=') {
            updateLexeme();
            updateCurrentChar();
            return eMayorIgual();
        } else {
            return new Token("Mayor", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eMayorIgual() {
        return new Token("MayorIgual", lexeme, sourceManager.getLineNumber());
    }
    private Token eMenor() {
        if (currentChar == '=') {
            updateLexeme();
            updateCurrentChar();
            return eMenorIgual();
        } else {
            return new Token("Menor", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eMenorIgual() {
        return new Token("MenorIgual", lexeme, sourceManager.getLineNumber());
    }
    private Token eNegacion() {
        if(currentChar == '=') {
            updateLexeme();
            updateCurrentChar();
            return eDistinto();
        } else {
            return new Token("Negacion", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eDistinto() {
        return new Token("Distinto", lexeme, sourceManager.getLineNumber());
    }
    private Token e34() throws Exception {
        if(currentChar == '\n') {
            updateCurrentChar();
            try {
                return e0();
            } catch (Exception e) {
                throw new LexicalException(lexeme, sourceManager.getLineNumber());
            }
        }
        else if(currentChar == '*') {
            updateCurrentChar();
            try {
                return e32();
            }catch(Exception e) {
                throw new LexicalException(lexeme, sourceManager.getLineNumber());
            }
        } else {
            updateCurrentChar();
            return e34();
        }
    }
    private Token e32() throws LexicalException {
        if(currentChar == '*') {
            updateCurrentChar();
            try {
                return e33();
            } catch (Exception e) {
                throw new LexicalException(lexeme, sourceManager.getLineNumber());
            }
        } else {
            updateCurrentChar();
            return e32();
        }
    }
    private Token e33() throws Exception {
        if(currentChar == '/') {
            updateCurrentChar();
            try {
                return e0();
            } catch (Exception e) {
                throw new LexicalException(lexeme, sourceManager.getLineNumber());
            }
        } else {
            updateCurrentChar();
            return e32();
        }
    }
    private Token eFin() {
        return new Token("EOF",lexeme, sourceManager.getLineNumber());
    }
}
