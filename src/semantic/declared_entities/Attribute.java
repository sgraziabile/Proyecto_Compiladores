package semantic.declared_entities;

public class Attribute {
    private String name;
    private Type type;
    private String modifier;

    public Attribute(String name, Type type, String modifier) {
        this.name = name;
        this.type = type;
        this.modifier = modifier;
    }
    public String getName() {
        return name;
    }
    public Type getType() {
        return type;
    }
    public String getModifier() {
        return modifier;
    }
}
