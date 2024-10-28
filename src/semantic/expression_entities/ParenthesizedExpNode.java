package semantic.expression_entities;

import semantic.declared_entities.Type;

public class ParenthesizedExpNode extends PrimaryNode {
    protected ExpressionNode expression;

    public ParenthesizedExpNode(ExpressionNode expression) {
        this.expression = expression;
    }
    public ExpressionNode getExpression() {
        return expression;
    }
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
    public Type typeCheck() throws Exception {
        return expression.typeCheck();
    }
    public String toString() {
        return expression.toString();
    }
}
