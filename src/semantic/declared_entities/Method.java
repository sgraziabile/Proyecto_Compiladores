package semantic.declared_entities;

import java.util.ArrayList;
import java.util.Hashtable;

public class Method extends ClassMember {
    private Hashtable<String, Parameter> parameterHash;
    private ArrayList<Parameter> parameterList;
    private boolean isConsolidated = false;

    public Method(String name, Type returnType, String modifier,String visibility) {
        super(name, returnType, modifier,visibility);
        this.parameterHash = new Hashtable<>();
        this.parameterList = new ArrayList<>();
    }
    public Method() {
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
    public void print() {
        System.out.println("Method: " + id + " " + type.getName() + " " + modifier + " " + visibility);
    }

}
