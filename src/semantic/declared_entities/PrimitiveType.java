package semantic.declared_entities;

public class PrimitiveType extends Type {
    private static final String INT = "int";
    private static final String FLOAT = "float";
    private static final String BOOLEAN = "boolean";
    private static final String CHAR = "char";

    public PrimitiveType(String name) {
        super(name);
    }
    public boolean conformsTo(Type other) {
        return other.getName().equals(this.getName());
    }
}
