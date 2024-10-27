package semantic.declared_entities;

public abstract class Type {
    protected String name;            //hacerlo Token

    public Type(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public abstract boolean conformsTo(Type other);
}
