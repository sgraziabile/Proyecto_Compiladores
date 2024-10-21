package semantic.expression_entities;

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
}
