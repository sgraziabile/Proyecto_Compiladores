package semantic.declared_entities;

public class PrimitiveType extends Type {

    public PrimitiveType(String name) {
        super(name);
    }
    public boolean conformsTo(Type other) {
        return other.getName().equals(this.getName());
    }
}
