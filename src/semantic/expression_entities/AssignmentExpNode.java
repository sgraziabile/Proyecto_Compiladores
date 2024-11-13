package semantic.expression_entities;

import entities.Token;
import exceptions.InvalidAssignmentException;
import exceptions.InvalidOperatorException;
import semantic.declared_entities.Type;

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
    public Type typeCheck() throws Exception {
        if(!(leftExp instanceof PrimaryNode)) {
            throw new InvalidAssignmentException(operator.getLineNumber(),operator.getLexeme());
        }
        else if(!leftExp.isAssignable()) {
            throw new InvalidAssignmentException(operator.getLineNumber(),operator.getLexeme());
        }
        Type leftType = leftExp.typeCheck();
        Type rightType = rightExp.typeCheck();
        if(leftType != null && rightType != null) {
            if(operator.getLexeme().equals("+=")|| operator.getLexeme().equals("-=")) {
                if(!leftType.getName().equals("int") || !rightType.getName().equals("int")) {
                    throw new InvalidOperatorException(operator.getLexeme(), leftType.getName(), rightType.getName(), operator.getLineNumber());
                }
            } else {
                if(!rightType.conformsTo(leftType)) {
                    throw new InvalidOperatorException(operator.getLexeme(), leftType.getName(), rightType.getName(), operator.getLineNumber());
                }
            }
        }
        return leftType;
    }
    public void generateCode() throws Exception {
        rightExp.generateCode();
        leftExp.generateCode(operator);
    }
    public String toString() {
        return leftExp.toString() + " " + operator.getLexeme() + " " + rightExp.toString();
    }

}
