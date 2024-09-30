package semantic.declared_entities;

import java.util.ArrayList;
import java.util.Hashtable;

public class Method extends ClassMember {
    private Hashtable<String, Parameter> parameterHash;
    private ArrayList<Parameter> parameterList;
    private boolean isConsolidated = false;

    public Method(String name, Type returnType, String modifier) {
        super(name, returnType, modifier);
        this.parameterHash = new Hashtable<>();
        this.parameterList = new ArrayList<>();
    }
    public void addParameter(String name, Parameter parameter) {
        parameterHash.put(name,parameter);
        parameterList.add(parameter);
    }
    public Type getReturnType() {
        return type;
    }
    public boolean isConsolidated() {
        return isConsolidated;
    }
    public ArrayList<Parameter> getParameterList() {
        return parameterList;
    }
    public Hashtable<String, Parameter> getParameterHash() {
        return parameterHash;
    }

}
