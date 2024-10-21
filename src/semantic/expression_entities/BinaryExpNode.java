package semantic.expression_entities;

import entities.Token;

public class BinaryExpNode extends CompoundExpNode {
    protected CompoundExpNode leftExp;
    protected CompoundExpNode rightExp;
    protected Token operator;

    public BinaryExpNode(CompoundExpNode leftExp, Token operator, CompoundExpNode rightExp) {
        this.leftExp = leftExp;
        this.operator = operator;
        this.rightExp = rightExp;
    }
    public BinaryExpNode() {
        this.leftExp = null;
        this.operator = null;
        this.rightExp = null;
    }
    public void setLeftExp(CompoundExpNode leftExp) {
        this.leftExp = leftExp;
    }
    public CompoundExpNode getLeftExp() {
        return leftExp;
    }
    public void setRightExp(CompoundExpNode rightExp) {
        this.rightExp = rightExp;
    }
    public CompoundExpNode getRightExp() {
        return rightExp;
    }
    public void setOperator(Token operator) {
        this.operator = operator;
    }
    public Token getOperator() {
        return operator;
    }
    public String toString() {
        String leftExpString = leftExp == null ? "" : leftExp.toString();
        String rightExpString = rightExp == null ? "" : rightExp.toString();
        String operatorString = operator == null ? "" : operator.getLexeme();
        return leftExpString + " " + operatorString + " " + rightExpString;
    }
}
