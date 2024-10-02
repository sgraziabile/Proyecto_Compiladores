package semantic.declared_entities;

public abstract class Type {
    private String name;            //hacerlo Token

    public Type(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
