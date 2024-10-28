package semantic.sentence_entities;

import exceptions.StaticReferenceException;
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
    public void checkSentence() throws Exception {
        try {
            expression.typeCheck();
        }catch (StaticReferenceException e) {
            throw new StaticReferenceException(line, e.getToken());
        }
    }
    public String toString() {
        return expression.toString();
    }
}
