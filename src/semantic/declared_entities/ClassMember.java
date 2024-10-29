package semantic.declared_entities;

import entities.Token;

public class ClassMember implements Symbol{
    protected Token id;
    protected Type type;
    protected String modifier;
    protected String visibility;

    public ClassMember(Token id, Type type, String modifier, String visibility) {
        this.id = id;
        this.type = type;
        this.modifier = modifier;
        this.visibility = visibility;
    }
    public ClassMember() {

    }
    public Token getId() {
        return id;
    }
    public String getName() {
        return id.getLexeme();
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
    public void setId(Token id) {
        this.id = id;
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
