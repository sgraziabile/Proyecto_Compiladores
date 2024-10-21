package semantic.expression_entities;

import entities.Token;
import semantic.declared_entities.Type;

public class ChainedVarNode extends Chained{

    public ChainedVarNode(Token name) {
        this.name = name;
    }
    public ChainedVarNode(Token name, Chained next) {
        this.name = name;
        this.chained = next;
    }
    public Type check (Type type) {
        return type;
    }
}
