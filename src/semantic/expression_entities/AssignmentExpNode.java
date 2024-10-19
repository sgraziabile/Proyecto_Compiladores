package semantic.expression_entities;

import entities.Token;

public class AssignmentExpNode extends ExpressionNode {
    private CompoundExpNode rightExp;
    private ExpressionNode leftExp;
    private Token operator;

    public AssignmentExpNode(ExpressionNode leftExp, Token operator, CompoundExpNode rightExp) {
        this.leftExp = leftExp;
        this.operator = operator;
        this.rightExp = rightExp;
    }
    public AssignmentExpNode() {
        this.leftExp = null;
        this.operator = null;
        this.rightExp = null;
    }
    public CompoundExpNode getRightExp() {
        return rightExp;
    }
    public void setRightExp(CompoundExpNode rightExp) {
        this.rightExp = rightExp;
    }
    public ExpressionNode getLeftExp() {
        return leftExp;
    }
    public void setLeftExp(ExpressionNode leftExp) {
        this.leftExp = leftExp;
    }
    public Token getOperator() {
        return operator;
    }
    public void setOperator(Token operator) {
        this.operator = operator;
    }

}
