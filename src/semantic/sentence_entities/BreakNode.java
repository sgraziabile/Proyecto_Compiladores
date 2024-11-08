package semantic.sentence_entities;

import entities.Token;
import exceptions.InvalidBreakException;

import static main.MainModule.symbolTable;

public class BreakNode extends SentenceNode {
    protected Token breakToken;

    public BreakNode(Token breakToken) {
        this.breakToken = breakToken;
    }
    public void checkSentence() throws Exception {
        if(!isInsideLoop()) {
            throw new InvalidBreakException(breakToken.getLineNumber());
        }
    }
    public boolean isInsideLoop() {
        return isBrekable;
    }

    private boolean isInsideBlock(SentenceNode block) {
        boolean insideBlock = false;
        for(SentenceNode sentence : ((Block)block).getSentenceList()) {
            if(sentence == this) {
                insideBlock = true;
                break;
            }
        }
        return insideBlock;
    }
    public String toString() {
        return "BreakNode";
    }


}
