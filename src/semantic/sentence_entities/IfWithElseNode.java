package semantic.sentence_entities;

import semantic.expression_entities.CompoundExpNode;

public class IfWithElseNode extends IfNode {

    protected SentenceNode elseBody;

    public IfWithElseNode(CompoundExpNode condition, SentenceNode thenBody, SentenceNode elseBody) {
        super(condition, thenBody);
        this.elseBody = elseBody;
    }
    public IfWithElseNode() {
        super();
        this.elseBody = null;
    }
    public SentenceNode getElseBody() {
        return elseBody;
    }
    public void setElseBody(SentenceNode elseBody) {
        this.elseBody = elseBody;
    }
    public String toString() {
        return "IfWithElseNode";
    }
}
