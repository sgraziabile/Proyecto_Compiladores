package semantic.expression_entities;

import entities.Token;
import semantic.declared_entities.ReferenceType;
import semantic.declared_entities.Type;

public class ObjectLiteralNode extends LiteralNode{

    public ObjectLiteralNode(Token value) {
        super(value);
    }
    public Type typeCheck() throws Exception {
        Type type;
        if(value.getLexeme().equals("null")) {
            type = new ReferenceType("null");
        } else {
            type = new ReferenceType("String");
        }
        return type;
    }

}
