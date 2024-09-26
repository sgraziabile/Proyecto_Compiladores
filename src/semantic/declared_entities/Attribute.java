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
}
