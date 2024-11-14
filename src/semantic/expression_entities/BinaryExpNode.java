package semantic.expression_entities;

import code_generator.CodeGenerator;
import entities.Token;
import exceptions.InvalidOperatorException;
import semantic.declared_entities.PrimitiveType;
import semantic.declared_entities.Type;

import static main.MainModule.writer;

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
            if(operator.getLexeme().equals("+") || operator.getLexeme().equals("-") || operator.getLexeme().equals("*") || operator.getLexeme().equals("/") || operator.getLexeme().equals("%")) {
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
    public void generateCode() throws Exception {
        String op = operator.getLexeme();
        leftExp.generateCode();
        rightExp.generateCode();
        switch(op) {
            case("+"):
                generateSumCode();
                break;
            case("-"):
                generateSubtractionCode();
                break;
            case("*"):
                generateMultiplicationCode();
                break;
            case("/"):
                generateDivisionCode();
                break;
            case("=="):
                generateEqualCode();
                break;
            case("!="):
                generateNECode();
                break;
            case("<"):
                generateLessThanCode();
                break;
            case(">"):
                generateGreaterThanCode();
                break;
            case("<="):
                generateLessEqualCode();
                break;
            case(">="):
                generateGreaterEqualCode();
                break;
            case("&&"):
                generateAndCode();
                break;
            case("||"):
                generateOrCode();
                break;
            case("%"):
                generateModCode();
                break;
        }
    }
    private void generateSumCode() throws Exception {
        writer.write(CodeGenerator.ADD + "\n");
    }
    private void generateSubtractionCode() throws Exception {
        writer.write(CodeGenerator.SUB + "\n");
    }
    private void generateMultiplicationCode() throws Exception {
        writer.write(CodeGenerator.MUL + "\n");
    }
    private void generateDivisionCode() throws Exception {
        writer.write(CodeGenerator.DIV + "\n");
    }
    private void generateEqualCode() throws Exception {
        writer.write(CodeGenerator.EQ + "\n");
    }
    private void generateNECode() throws Exception {
        writer.write(CodeGenerator.NE + "\n");
    }
    private void generateLessThanCode() throws Exception {
        writer.write(CodeGenerator.LT + "\n");
    }
    private void generateGreaterThanCode() throws Exception {
        writer.write(CodeGenerator.GT + "\n");
    }
    private void generateLessEqualCode() throws Exception {
        writer.write(CodeGenerator.LE + "\n");
    }
    private void generateGreaterEqualCode() throws Exception {
        writer.write(CodeGenerator.GE + "\n");
    }
    private void generateAndCode() throws Exception {
        writer.write(CodeGenerator.AND + "\n");
    }
    private void generateOrCode() throws Exception {
        writer.write(CodeGenerator.OR + "\n");
    }
    private void generateModCode() throws Exception {
        writer.write(CodeGenerator.MOD + "\n");
    }
    public String toString() {
        String leftExpString = leftExp == null ? "" : leftExp.toString();
        String rightExpString = rightExp == null ? "" : rightExp.toString();
        String operatorString = operator == null ? "" : operator.getLexeme();
        return leftExpString + " " + operatorString + " " + rightExpString;
    }
}
