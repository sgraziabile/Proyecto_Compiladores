package semantic.declared_entities;

public class ClassMember {
    protected String id;
    protected Type type;
    protected String modifier;

    public ClassMember(String name, Type type, String modifier) {
        this.id = name;
        this.type = type;
        this.modifier = modifier;
    }
    public ClassMember() {

    }
    public String getId() {
        return id;
    }
    public Type getType() {
        return type;
    }
    public String getModifier() {
        return modifier;
    }
    public void setName(String name) {
        this.id = name;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
