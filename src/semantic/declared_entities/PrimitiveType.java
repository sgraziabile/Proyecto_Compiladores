package semantic.declared_entities;

public class PrimitiveType extends Type {
    private String name;

    public PrimitiveType(String name) {
        super(name);
    }
    public String getName() {
        return name;
    }
}
