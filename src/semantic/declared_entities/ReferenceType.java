package semantic.declared_entities;

public class ReferenceType extends Type {
    private String name;

    public ReferenceType(String name) {
        super(name);
    }
    public String getName() {
        return name;
    }
}

