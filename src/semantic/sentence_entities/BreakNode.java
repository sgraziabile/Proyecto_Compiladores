package semantic.sentence_entities;

import entities.Token;

public class BreakNode extends SentenceNode {
    protected Token breakToken;

    public BreakNode(Token breakToken) {
        this.breakToken = breakToken;
    }


}
