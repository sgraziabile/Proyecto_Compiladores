package semantic.expression_entities;

import entities.Token;
import semantic.declared_entities.Type;

import static main.MainModule.symbolTable;

public abstract class Chained extends PrimaryNode{
    protected Token name;
    protected Chained chained;

    public Token getName() {
        return name;
    }
    public void setName(Token name) {
        this.name = name;
    }
    public Chained getChained() {
        return chained;
    }
    public void setChained(Chained chained) {
        this.chained = chained;
    }
    public void resolveNames(PrimaryNode parentChain) throws Exception {

    }
    public abstract Type check(Type type);

    public String toString() {
        return name.getLexeme() + " " + (chained == null ? "" : chained.toString());
    }
}
