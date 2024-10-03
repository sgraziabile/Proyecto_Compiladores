package semantic.declared_entities;

import entities.Token;
import exceptions.CantResolveSymbolException;

import static main.MainModule.symbolTable;

public class Attribute extends ClassMember {

    public Attribute(Token name, Type type, String modifier, String visibility) {
        super(name,type,modifier,visibility);
    }
    public Attribute() {

    }
    public void print() {
        System.out.println("Attribute: " + id.getLexeme() + " " + type.getName() + " " + modifier + " " + visibility);
    }
    public void checkDeclaration() throws Exception {
        if(type.getName().equals("void")) {
            throw new CantResolveSymbolException(id.getLineNumber(), type.getName());
        }
        else if(!type.getName().equals("int") && !type.getName().equals("boolean") && !type.getName().equals("float")){
            if(symbolTable.getClass(type.getName()) == null) {
                throw new CantResolveSymbolException(id.getLineNumber(), type.getName());
            }
        }
    }
}
