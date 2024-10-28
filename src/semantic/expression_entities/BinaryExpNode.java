package semantic.expression_entities;

import entities.Token;
import exceptions.InvalidOperatorException;
import semantic.declared_entities.PrimitiveType;
import semantic.declared_entities.Type;

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
    public Type typeCheck() throws Exception {
        Type leftType = leftExp.typeCheck();
        Type rightType = rightExp.typeCheck();
        Type expressionType = null;
        if(leftType != null && rightType != null) {
            if(operator.getLexeme().equals("+") || operator.getLexeme().equals("-") || operator.getLexeme().equals("*") || operator.getLexeme().equals("/")) {
                if(!leftType.getName().equals("int") || !rightType.getName().equals("int")) {
                    throw new InvalidOperatorException(operator.getLexeme(), leftType.getName(), rightType.getName(), operator.getLineNumber());
                } else {
                    expressionType = leftType;
                }
            } else if(operator.getLexeme().equals("==") || operator.getLexeme().equals("!=")) {
                if(!leftType.conformsTo(rightType) && !rightType.conformsTo(leftType)) {
                    throw new InvalidOperatorException(operator.getLexeme(), leftType.getName(), rightType.getName(), operator.getLineNumber());
                } else {
                    expressionType = new PrimitiveType("boolean");
                }
            } else if(operator.getLexeme().equals("<") || operator.getLexeme().equals(">") || operator.getLexeme().equals("<=") || operator.getLexeme().equals(">=")) {
                if(!leftType.getName().equals("int") || !rightType.getName().equals("int")) {
                    throw new InvalidOperatorException(operator.getLexeme(), leftType.getName(), rightType.getName(), operator.getLineNumber());
                } else {
                    expressionType = new PrimitiveType("boolean");
                }
            } else if(operator.getLexeme().equals("&&") || operator.getLexeme().equals("||")) {
                if(!leftType.getName().equals("boolean") || !rightType.getName().equals("boolean")) {
                    throw new InvalidOperatorException(operator.getLexeme(), leftType.getName(), rightType.getName(), operator.getLineNumber());
                } else {
                    expressionType = leftType;
                }
            }
        }
        return expressionType;
    }
    public String toString() {
        String leftExpString = leftExp == null ? "" : leftExp.toString();
        String rightExpString = rightExp == null ? "" : rightExp.toString();
        String operatorString = operator == null ? "" : operator.getLexeme();
        return leftExpString + " " + operatorString + " " + rightExpString;
    }
}
