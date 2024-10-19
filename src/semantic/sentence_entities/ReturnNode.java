package semantic.sentence_entities;

import semantic.expression_entities.ExpressionNode;

public class ReturnNode extends SentenceNode {
    private ExpressionNode returnExpression;

    public ReturnNode(ExpressionNode returnExpression) {
        this.returnExpression = returnExpression;
    }
    public ReturnNode() {
        this.returnExpression = null;
    }
    public ExpressionNode getReturnExpression() {
        return returnExpression;
    }
}
