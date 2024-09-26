package semantic.declared_entities;

import java.util.Hashtable;

public class Method {
    private String name;
    private Type returnType;
    private String modifier;
    private Hashtable<String, Type> parameters;
    private boolean isConsolidated = false;

    public Method(String name, Type returnType, String modifier) {
        this.name = name;
        this.returnType = returnType;
        this.modifier = modifier;
        this.parameters = new Hashtable<>();
    }
    public void addParameter(String name, Type type) {
        parameters.put(name, type);
    }
    public String getName() {
        return name;
    }
    public Type getReturnType() {
        return returnType;
    }
    public String getModifier() {
        return modifier;
    }
    public boolean isConsolidated() {
        return isConsolidated;
    }

}
