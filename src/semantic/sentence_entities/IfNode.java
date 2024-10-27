package semantic.sentence_entities;

import semantic.expression_entities.CompoundExpNode;

public class IfNode extends SentenceNode {
    CompoundExpNode condition;
    SentenceNode thenBody;

    public IfNode(CompoundExpNode condition, SentenceNode thenBody) {
        this.condition = condition;
        this.thenBody = thenBody;
    }
    public IfNode() {
        this.condition = null;
        this.thenBody = null;
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
    public String toString() {
        return "IfNode";
    }
}