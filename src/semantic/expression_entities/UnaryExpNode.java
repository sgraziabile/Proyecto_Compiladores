package semantic.expression_entities;

import entities.Token;

public class UnaryExpNode extends CompoundExpNode{
    OperandNode operand;
    Token operator;

    public UnaryExpNode(OperandNode operand, Token operator) {
        this.operand = operand;
        this.operator = operator;
    }
    public UnaryExpNode() {
        this.operand = null;
        this.operator = null;
    }
    public OperandNode getOperand() {
        return operand;
    }
    public void setOperand(OperandNode operand) {
        this.operand = operand;
    }
    public Token getOperator() {
        return operator;
    }
    public void setOperator(Token operator) {
        this.operator = operator;
    }
    public String toString() {
        return operator.getLexeme() + " " + operand.toString();
    }
}
