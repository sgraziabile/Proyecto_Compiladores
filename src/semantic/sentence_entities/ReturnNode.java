package semantic.sentence_entities;

import code_generator.CodeGenerator;
import exceptions.IncompatibleTypesException;
import exceptions.MissingReturnStatementException;
import exceptions.StaticReferenceException;
import semantic.declared_entities.Type;
import semantic.expression_entities.ExpressionNode;

import static main.MainModule.symbolTable;
import static main.MainModule.writer;

public class ReturnNode extends SentenceNode {
    private ExpressionNode returnExpression;

    public ReturnNode(ExpressionNode returnExpression) {
        this.returnExpression = returnExpression;
    }
    public ReturnNode() {
        this.returnExpression = null;
    }
    public ExpressionNode getReturnExpression() {
        return returnExpression;
    }
    public void setReturnExpression(ExpressionNode returnExpression) {
        this.returnExpression = returnExpression;
    }
    public void checkSentence() throws Exception {
        Type returnType;
        if(returnExpression != null) {
            try {
                returnType = returnExpression.typeCheck();
            } catch(StaticReferenceException e) {
                throw new StaticReferenceException(line, e.getToken());
            }
            Type declaredReturnType = symbolTable.getCurrentMethod().getReturnType();
            if (!returnType.conformsTo(declaredReturnType)) {
                throw new IncompatibleTypesException(returnType, declaredReturnType, line, "return");
            }
        }
        else if(!symbolTable.getCurrentMethod().getReturnType().getName().equals("void")) {
            throw new MissingReturnStatementException("return", line);
        }
    }
    public void generateCode() throws Exception {
        if(returnExpression != null) {
            returnExpression.generateCode();
            int retunOffset = symbolTable.getCurrentMethod().getReturnOffset();
            writer.write(CodeGenerator.STORE+ " "+ retunOffset+ " ; Copia el valor de retorno \n");
        }
    }
    public String toString() {
        String msg = "Return ";
        return msg + returnExpression.toString();
    }
}
