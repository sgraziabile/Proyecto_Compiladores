package semantic.sentence_entities;

import exceptions.IncompatibleTypesException;
import exceptions.MissingReturnStatementException;
import semantic.declared_entities.Type;
import semantic.expression_entities.ExpressionNode;

import static main.MainModule.symbolTable;

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
        if(returnExpression != null) {
            Type returnType = returnExpression.typeCheck();
            Type declaredReturnType = symbolTable.getCurrentMethod().getReturnType();
            if (!returnType.conformsTo(declaredReturnType)) {
                throw new IncompatibleTypesException(returnType, declaredReturnType, line, "return");
            }
        }
        else throw new MissingReturnStatementException(";", line);
    }
    public String toString() {
        String msg = "Return ";
        return msg + returnExpression.toString();
    }
}
