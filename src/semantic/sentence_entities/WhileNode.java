package semantic.sentence_entities;

import exceptions.IncompatibleTypesException;
import exceptions.StaticReferenceException;
import semantic.declared_entities.PrimitiveType;
import semantic.declared_entities.Type;
import semantic.expression_entities.ExpressionNode;

public class WhileNode extends SentenceNode {
    private ExpressionNode condition;
    private SentenceNode body;

    public WhileNode(ExpressionNode condition, SentenceNode body) {
        this.condition = condition;
        this.body = body;
    }
    public WhileNode() {
        this.condition = null;
        this.body = null;
    }

    public ExpressionNode getCondition() {
        return condition;
    }
    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }
    public SentenceNode getBody() {
        return body;
    }
    public void setBody(SentenceNode body) {
        this.body = body;
    }
    public void checkSentence() throws Exception {
        Type conditionType;
        try {
            conditionType = condition.typeCheck();
        } catch (StaticReferenceException e) {
            throw new StaticReferenceException(line, e.getToken());
        }
        if(!conditionType.getName().equals("boolean")) {
            throw new IncompatibleTypesException(new PrimitiveType("boolean"), conditionType, line,"while");
        }
        body.checkSentence();
    }
    public String toString() {
        return "WhileNode";
    }
}
