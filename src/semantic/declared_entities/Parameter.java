package semantic.declared_entities;

import entities.Token;
import exceptions.CantResolveSymbolException;

import static main.MainModule.symbolTable;

public class Parameter implements Symbol{
    private Token id;
    private Type type;
    private int offset;

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
        System.out.println("Parameter: " + id.getLexeme() + " " + type.getName()+" Offset: "+offset);
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getOffset() {
        return offset;
    }
    public void checkDeclaration() throws Exception {
        if(!type.getName().equals("void") && !type.getName().equals("int") && !type.getName().equals("boolean") && !type.getName().equals("float") && !type.getName().equals("char")){
            if(symbolTable.getClass(type.getName()) == null) {
                throw new CantResolveSymbolException(id.getLineNumber(), type.getName());
            }
        }
    }
}
