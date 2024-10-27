package semantic.expression_entities;

import entities.Token;
import semantic.declared_entities.Type;

public abstract class Chained extends PrimaryNode{
    protected Token id;
    protected Chained chained;

    public Token getId() {
        return id;
    }
    public void setId(Token id) {
        this.id = id;
    }
    public Chained getChained() {
        return chained;
    }
    public void setChained(Chained chained) {
        this.chained = chained;
    }
    public boolean isAssignable() {
        if(chained == null) {
            return this instanceof ChainedVarNode;
        } else {
            return chained.isAssignable();
        }
    }
    public Type typeCheck(PrimaryNode parentChain) throws Exception {
        Type type = null;
        return type;
    }

    public String toString() {
        return id.getLexeme() + " " + (chained == null ? "" : chained.toString());
    }
}
