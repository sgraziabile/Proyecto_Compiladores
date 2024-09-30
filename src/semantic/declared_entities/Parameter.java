package semantic.declared_entities;

public class Parameter {
    private String id;
    private Type type;

    public Parameter(String name, Type type) {
        this.id = name;
        this.type = type;
    }
    public String getId() {
        return id;
    }
    public Type getType() {
        return type;
    }
}
