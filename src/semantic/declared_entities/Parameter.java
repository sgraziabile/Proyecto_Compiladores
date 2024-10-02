package semantic.declared_entities;

import entities.Token;

public class Parameter {
    private Token id;
    private Type type;

    public Parameter(Token name, Type type) {
        this.id = name;
        this.type = type;
    }
    public Token getId() {
        return id;
    }
    public Type getType() {
        return type;
    }
    public void print() {
        System.out.println("Parameter: " + id.getLexeme() + " " + type.getName());
    }
}
