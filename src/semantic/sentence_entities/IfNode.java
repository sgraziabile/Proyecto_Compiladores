package semantic.sentence_entities;

import semantic.expression_entities.CompoundExpNode;

public class IfNode extends SentenceNode {
    CompoundExpNode condition;
    SentenceNode thenBody;
    SentenceNode elseBody;

    public IfNode(CompoundExpNode condition, SentenceNode thenBody, SentenceNode elseBody) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = elseBody;
    }
    public IfNode() {
        this.condition = null;
        this.thenBody = null;
        this.elseBody = null;
    }
    public CompoundExpNode getCondition() {
        return condition;
    }
    public void setCondition(CompoundExpNode condition) {
        this.condition = condition;
    }
    public SentenceNode getThenBody() {
        return thenBody;
    }
    public void setThenBody(SentenceNode thenBody) {
        this.thenBody = thenBody;
    }
    public SentenceNode getElseBody() {
        return elseBody;
    }
    public void setElseBody(SentenceNode elseBody) {
        this.elseBody = elseBody;
    }
    public boolean hasElseBody() {
        return elseBody != null;
    }
    public String toString() {
        return "IfNode";
    }
}
