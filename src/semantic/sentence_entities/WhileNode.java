package semantic.sentence_entities;

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
    public String toString() {
        return "WhileNode";
    }
}
