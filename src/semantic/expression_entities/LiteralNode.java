package semantic.expression_entities;

import entities.Token;

public class LiteralNode extends OperandNode{
    Token value;

    public LiteralNode(Token value) {
        this.value = value;
    }
    public Token getValue() {
        return value;
    }
    public void setValue(Token value) {
        this.value = value;
    }
    public String toString() {
        return value.getLexeme();
    }
}
