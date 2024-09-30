package semantic.declared_entities;

public class Attribute extends ClassMember {

    public Attribute(String name, Type type, String modifier,String visibility) {
        super(name,type,modifier,visibility);
    }
    public Attribute() {

    }
    public void print() {
        System.out.println("Attribute: " + id + " " + type.getName() + " " + modifier + " " + visibility);
    }
}
