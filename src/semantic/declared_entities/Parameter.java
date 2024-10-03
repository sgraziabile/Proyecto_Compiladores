package semantic.declared_entities;

import entities.Token;
import exceptions.CantResolveSymbolException;

import static main.MainModule.symbolTable;

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
    public void checkDeclaration() throws Exception {
        if(!type.getName().equals("void") && !type.getName().equals("int") && !type.getName().equals("boolean") && !type.getName().equals("float")) {
            if(symbolTable.getClass(type.getName()) == null) {
                throw new CantResolveSymbolException(id.getLineNumber(), type.getName());
            }
        }
    }
}
