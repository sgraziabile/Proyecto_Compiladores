package semantic.expression_entities;

import entities.Token;

public abstract class CompoundExpNode extends ExpressionNode {
    protected Token operator;

    public Token getOperator() {
        return operator;
    }
    public void setOperator(Token operator) {
        this.operator = operator;
    }


}
