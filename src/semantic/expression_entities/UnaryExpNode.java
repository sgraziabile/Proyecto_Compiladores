package semantic.expression_entities;

import code_generator.CodeGenerator;
import entities.Token;
import exceptions.InvalidOperatorException;
import semantic.declared_entities.Type;

import static main.MainModule.writer;

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
    public void generateCode() throws Exception {
        operand.generateCode();
        if(operator.getLexeme().equals("!")) {
            writer.write("NOT ; Negacion logica\n");
        } else if(operator.getLexeme().equals("-")) {
            writer.write(CodeGenerator.PUSH + " -1 ; Carga el valor 0\n");
            writer.write(CodeGenerator.MUL + " ; Multiplica por -1\n");
        }
    }
    public String toString() {
        return operator.getLexeme() + " " + operand.toString();
    }
}
