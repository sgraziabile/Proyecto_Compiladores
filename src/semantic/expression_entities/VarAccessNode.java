package semantic.expression_entities;

import entities.Token;

public class VarAccessNode extends PrimaryNode {
    protected Token id;

    public VarAccessNode(Token id) {
        this.id = id;
    }
    public Token getId() {
        return id;
    }
    public void setId(Token id) {
        this.id = id;
    }
    public String toString() {
        return id.getLexeme() + " " + (chained == null ? "" : chained.toString());
    }


}
