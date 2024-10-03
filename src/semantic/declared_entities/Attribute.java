package semantic.declared_entities;

import entities.Token;

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

    }
}
