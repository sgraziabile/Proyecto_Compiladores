package semantic.expression_entities;

import semantic.declared_entities.Type;

public abstract class ExpressionNode {
    public String toString() {
        return "ExpressionNode";
    }

    public  Type typeCheck() throws Exception {
        Type type = null;
        return type;
    }



}
