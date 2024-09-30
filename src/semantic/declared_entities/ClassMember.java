package semantic.declared_entities;

public class ClassMember {
    protected String id;
    protected Type type;
    protected String modifier;
    protected String visibility;

    public ClassMember(String name, Type type, String modifier, String visibility) {
        this.id = name;
        this.type = type;
        this.modifier = modifier;
        this.visibility = visibility;
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
    public String getVisibility() {
        return visibility;
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
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
