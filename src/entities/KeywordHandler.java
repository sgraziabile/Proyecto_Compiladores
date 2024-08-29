package entities;

import java.util.HashMap;
import java.util.Map;

public class KeywordHandler {
    private Map<String, Boolean> keywords;
    public KeywordHandler() {
        keywords = new HashMap<>();
        keywords.put("class", true);
        keywords.put("extends", true);
        keywords.put("public", true);
        keywords.put("static", true);
        keywords.put("void", true);
        keywords.put("boolean", true);
        keywords.put("char", true);
        keywords.put("int", true);
        keywords.put("if", true);
        keywords.put("else", true);
        keywords.put("while", true);
        keywords.put("return", true);
        keywords.put("var", true);
        keywords.put("switch", true);
        keywords.put("case", true);
        keywords.put("break", true);
        keywords.put("this", true);
        keywords.put("new", true);
        keywords.put("null", true);
        keywords.put("true", true);
        keywords.put("false", true);
        keywords.put("float",true);
    }
    public boolean isKeyword(String lexeme) {
        return keywords.containsKey(lexeme);
    }
}

