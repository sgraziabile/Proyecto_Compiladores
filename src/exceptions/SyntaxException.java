package exceptions;

import java.util.List;

public class SyntaxException extends Exception {
    List<String> expectedTokens;
    String currentToken;
    String errorLine;

    public SyntaxException(List<String> expectedTokens, String currentToken, String errorLine) {
        this.expectedTokens = expectedTokens;
        this.currentToken = currentToken;
        this.errorLine = errorLine;
    }
    public String getMessage() {
        if(expectedTokens.size() == 1) {
            return getSimpleMessage();
        } else {
            return getComplexMessage();
        }

    }
    private String getSimpleMessage() {
        String message = "Error Sintactico en linea: " + errorLine + " ";
        message += "Se esperaba: " + expectedTokens.getFirst() + " se encontró " + currentToken + "\n";
        message += "[Error:"+ currentToken +"|"+ errorLine +"]";
        return message;
    }
    private String getComplexMessage() {
        String message = "Error Sintactico en linea: " + errorLine + " ";
        message += "Se esperaba uno de los siguientes tokens: ";
        for (String token : expectedTokens) {
            message += token + " ";
        }
        message += "se encontró " + currentToken + "\n";
        message += "[Error:"+ currentToken +"|"+ errorLine +"]";
        return message;
    }



}
