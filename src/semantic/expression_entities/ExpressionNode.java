package semantic.expression_entities;

import entities.Token;
import semantic.declared_entities.Type;

public abstract class ExpressionNode {
    public String toString() {
        return "ExpressionNode";
    }

    public  Type typeCheck() throws Exception {
        Type type = null;
        return type;
    }
    public boolean isAssignable() {
        return false;
    }
    public boolean canBeCalled() {
        return false;
    }
    public void generateCode() throws Exception {
    }
    public void generateCode(Token op) throws Exception {
    }
}
