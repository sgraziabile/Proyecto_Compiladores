package semantic.expression_entities;

import entities.Token;
import semantic.declared_entities.PrimitiveType;
import semantic.declared_entities.Type;

import static main.MainModule.writer;

public class PrimitiveLiteralNode extends LiteralNode {

    public PrimitiveLiteralNode(Token token) {
        super(token);
    }
    public Type typeCheck() {
        Type type;
        if(value.getLexeme().equals("true") || value.getLexeme().equals("false")) {
            type = new PrimitiveType("boolean");
        } else if(value.getTokenClass().equals("floatLiteral")) {
            type = new PrimitiveType("float");
        } else if(value.getTokenClass().equals("charLiteral")) {
            type = new PrimitiveType("char");
        } else {
            type = new PrimitiveType("int");
        }
        return type;
    }
    public void generateCode() throws Exception{
        if(value.getLexeme().equals("true")) {
            writer.write("PUSH 1 ; Apila el valor\n");
        } else if(value.getLexeme().equals("false")) {
            writer.write("PUSH 0 ; Apila el valor\n");
        } else {
            writer.write("PUSH "+value.getLexeme()+" ; Apila el valor\n");
        }
    }
    public String toString() {
        return value.getLexeme();
    }

}
