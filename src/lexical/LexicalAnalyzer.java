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
            return eSingleLine();
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
        else if(currentChar == '+') {
            updateLexeme();
            updateCurrentChar();
            return eSuma();
        }
        else if(currentChar == '=') {
            updateLexeme();
            updateCurrentChar();
            return eIgual();
        }
        else if(currentChar == '&') {
            updateLexeme();
            updateCurrentChar();
            return singleAnd();
        }
        else if(currentChar == '|') {
            updateLexeme();
            updateCurrentChar();
            return eSingleOr();
        }
        else if(currentChar == '%') {
            updateLexeme();
            updateCurrentChar();
            return eMod();
        }
        else if(currentChar == '(') {
            updateLexeme();
            updateCurrentChar();
            return openParenthesis();
        }
        else if (currentChar == '\'') {
            updateLexeme();
            updateCurrentChar();
            return eChar1();
        }
        else if(currentChar == ')') {
            updateLexeme();
            updateCurrentChar();
            return closeParenthesis();
        }
        else if(currentChar == '{') {
            updateLexeme();
            updateCurrentChar();
            return openBrace();
        }
        else if(currentChar == '}') {
            updateLexeme();
            updateCurrentChar();
            return closeBrace();
        }
        else if(currentChar == ';') {
            updateLexeme();
            updateCurrentChar();
            return semiColon();
        }
        else if(currentChar == ',') {
            updateLexeme();
            updateCurrentChar();
            return eComma();
        }
        else if(currentChar == '.') {
            updateLexeme();
            updateCurrentChar();
            return ePeriod();
        }
        else if(currentChar == ':') {
            updateLexeme();
            updateCurrentChar();
            return eColon();
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
    private Token eChar1() throws Exception {
        if(currentChar == '\\') {
            updateLexeme();
            updateCurrentChar();
            return eChar2();
        }
        else if(Character.isWhitespace(currentChar) || currentChar == '\n' || currentChar == '\t')
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        else if(currentChar == '\'') {
            updateLexeme();
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        }
        else {
            updateLexeme();
            updateCurrentChar();
            return eChar3();
        }
    }
    private Token eChar2() throws Exception {
        if(currentChar == '\'' || currentChar == '\\') {
            updateLexeme();
            updateCurrentChar();
            return eChar4();
        }
        else if(Character.isWhitespace(currentChar) || currentChar == '\n' || currentChar == '\t')
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        else {
            updateLexeme();
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eChar3() throws Exception {
        if(currentChar == '\'') {
            updateLexeme();
            updateCurrentChar();
            return eCharFin();
        } else {
            updateLexeme();
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eChar4() throws Exception {
        if(currentChar == '\'') {
            updateLexeme();
            updateCurrentChar();
            return eCharFin();
        }
        else if(Character.isWhitespace(currentChar) || currentChar == '\n' || currentChar == '\t')
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        else {
            updateLexeme();
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eCharFin() {
        return new Token("charLiteral", lexeme, sourceManager.getLineNumber());
    }
    private Token eSingleLine() throws Exception{
        if(currentChar == '/') {
            lexeme = "";
            updateCurrentChar();
            try {
                return eMultiLine();
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
            return new Token("opMayor", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eMayorIgual() {
        return new Token("opMayorIgual", lexeme, sourceManager.getLineNumber());
    }
    private Token eMenor() {
        if (currentChar == '=') {
            updateLexeme();
            updateCurrentChar();
            return eMenorIgual();
        } else {
            return new Token("opMenor", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eMenorIgual() {
        return new Token("opMenorIgual", lexeme, sourceManager.getLineNumber());
    }
    private Token eNegacion() {
        if(currentChar == '=') {
            updateLexeme();
            updateCurrentChar();
            return eDistinto();
        } else {
            return new Token("opNegacion", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eDistinto() {
        return new Token("opDistinto", lexeme, sourceManager.getLineNumber());
    }
    private Token eSuma() {
        if(currentChar == '=') {
            updateLexeme();
            updateCurrentChar();
            return eSumaAsignacion();
        } else {
            return new Token("opSuma", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eIgual() {
        if(currentChar == '=') {
            updateLexeme();
            updateCurrentChar();
            return eIgualdad();
        } else {
            return new Token("opAsignacion", lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eIgualdad() {
        return new Token("opIgualdad", lexeme, sourceManager.getLineNumber());
    }
    private Token eSumaAsignacion() {
        return new Token("opSumaAsign", lexeme, sourceManager.getLineNumber());
    }
    private Token singleAnd() throws Exception {
        if(currentChar == '&') {
            updateLexeme();
            updateCurrentChar();
            return eDoubleAnd();
        } else {
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eDoubleAnd() {
        return new Token("opAnd", lexeme, sourceManager.getLineNumber());
    }
    private Token eSingleOr() throws Exception {
        if(currentChar == '|') {
            updateLexeme();
            updateCurrentChar();
            return eDoubleOr();
        } else {
            throw new LexicalException(lexeme, sourceManager.getLineNumber());
        }
    }
    private Token eDoubleOr() {
        return new Token("opOr", lexeme, sourceManager.getLineNumber());
    }
    private Token eMultiLine() throws Exception {
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
            return eMultiLine();
        }
    }
    private Token openParenthesis() {
        return new Token("parentesisAbre", lexeme, sourceManager.getLineNumber());
    }
    private Token closeParenthesis() {
        return new Token("parentesisCierra", lexeme, sourceManager.getLineNumber());
    }
    private Token openBrace() {
        return new Token("llaveAbre", lexeme, sourceManager.getLineNumber());
    }
    private Token closeBrace() {
        return new Token("llaveCierra", lexeme, sourceManager.getLineNumber());
    }
    private Token semiColon() {
        return new Token("puntoYComa", lexeme, sourceManager.getLineNumber());
    }
    private Token eComma() {
        return new Token("coma", lexeme, sourceManager.getLineNumber());
    }
    private Token ePeriod() {
        return new Token("punto", lexeme, sourceManager.getLineNumber());
    }
    private Token eColon() {
        return new Token("dosPuntos", lexeme, sourceManager.getLineNumber());
    }
    private Token eMod() {
        return new Token("opMod", lexeme, sourceManager.getLineNumber());
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
