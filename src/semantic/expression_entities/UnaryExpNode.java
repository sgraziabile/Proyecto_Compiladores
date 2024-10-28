package semantic.expression_entities;

import entities.Token;
import exceptions.InvalidOperatorException;
import semantic.declared_entities.Type;

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
    public Type typeCheck() throws Exception {
        Type type = operand.typeCheck();
        if(type != null) {
            if(operator.getLexeme().equals("!")) {
                if(!type.getName().equals("boolean")) {
                    throw new InvalidOperatorException(operator.getLexeme(), type.getName(),null ,operator.getLineNumber());
                }
            } else if(operator.getLexeme().equals("-") || operator.getLexeme().equals("+")) {
                if(!type.getName().equals("int") && !type.getName().equals("float")) {
                    throw new InvalidOperatorException(operator.getLexeme(), type.getName(),null ,operator.getLineNumber());
                }
            }
        }
        return type;
    }
    public String toString() {
        return operator.getLexeme() + " " + operand.toString();
    }
}
