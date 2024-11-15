package semantic.declared_entities;

import entities.Token;
import exceptions.CantResolveSymbolException;

import static main.MainModule.symbolTable;

public class Attribute extends ClassMember {

    private int offset;
    private String label;
    private Class myClass;
    public Attribute(Token name, Type type, String modifier, String visibility) {
        super(name,type,modifier,visibility);
    }
    public Attribute() {

    }
    public void print() {
        System.out.println("Attribute: " + id.getLexeme() + " " + type.getName() + " " + modifier + " " + visibility);
        System.out.println("Offset: " + offset);
    }
    public void checkDeclaration() throws Exception {
        if(type.getName().equals("void")) {
            throw new CantResolveSymbolException(id.getLineNumber(), type.getName());
        }
        else if(!type.getName().equals("int") && !type.getName().equals("boolean") && !type.getName().equals("float") && !type.getName().equals("char")){
            if(symbolTable.getClass(type.getName()) == null) {
                throw new CantResolveSymbolException(id.getLineNumber(), type.getName());
            }
        }
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getOffset() {
        return offset;
    }
    public void setLabel(Class myClass) {
        label = "lblAttr"+id.getLexeme()+"@"+myClass.getName();
    }
    public String getLabel() {
        return label;
    }
    public void setMyClass(Class myClass) {
        this.myClass = myClass;
    }
    public Class getMyClass() {
        return myClass;
    }
}
