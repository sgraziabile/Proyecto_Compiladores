package semantic.sentence_entities;

import exceptions.IncompatibleTypesException;
import semantic.declared_entities.PrimitiveType;
import semantic.declared_entities.Type;
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
    public void checkSentence() throws Exception {
        Type conditionType = condition.typeCheck();
        if(!conditionType.getName().equals("boolean")) {
            throw new IncompatibleTypesException(new PrimitiveType("boolean"), conditionType, line,"if");
        }
        thenBody.checkSentence();
    }
    public String toString() {
        return "IfNode";
    }
}
