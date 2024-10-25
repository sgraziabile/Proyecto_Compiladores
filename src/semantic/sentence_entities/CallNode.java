package semantic.sentence_entities;

import semantic.expression_entities.ExpressionNode;

public class CallNode extends SentenceNode {
    private ExpressionNode expression;

    public CallNode(ExpressionNode expression) {
        this.expression = expression;
    }
    public CallNode() {
        this.expression = null;
    }
    public ExpressionNode getExpression() {
        return expression;
    }
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
    public String toString() {
        return expression.toString();
    }
}
