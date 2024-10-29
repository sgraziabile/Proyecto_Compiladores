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
        Type type;
        type = expression.typeCheck();
        if(chained != null) {
            type = chained.typeCheck(type);
        }
        return type;
    }
    public boolean isAssignable() {
        if(chained == null) {
            return expression.isAssignable();
        } else {
            return chained.isAssignable();
        }
    }
    public boolean canBeCalled() {
        if(chained == null) {
            return expression.canBeCalled();
        } else {
            return chained.canBeCalled();
        }
    }
    public String toString() {
        return expression.toString();
    }
}
