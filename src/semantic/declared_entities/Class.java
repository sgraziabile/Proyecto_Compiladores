package semantic.declared_entities;

import entities.Token;

import java.util.ArrayList;
import java.util.Hashtable;


public class Class {
    private Token id;
    private Token superclass;
    private boolean isConsolidated = false;
    private Hashtable<String, Attribute> attributes;
    private ArrayList<Attribute> attributeList;
    private Hashtable<String, Method> methods;
    private ArrayList<Method> methodList;

    public Class(Token idClass) {
        id = idClass;
        superclass = null;
        attributes = new Hashtable<>();
        methods = new Hashtable<>();
        attributeList = new ArrayList<>();
        methodList = new ArrayList<>();
    }
    public void setSuperclass(Token superclass) {
        this.superclass = superclass;
    }
    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getId().getLexeme(), attribute);
        attributeList.add(attribute);
    }
    public void addMethod(Method method) {
        methods.put(method.getId().getLexeme(), method);
        methodList.add(method);
    }
    public Token getId() {
        return id;
    }
    public String getName() {
        return id.getLexeme();
    }
    public Token getSuperclass() {
        return superclass;
    }
    public boolean isConsolidated() {
        return isConsolidated;
    }
    public Attribute getAttribute(String id) {
        return attributes.get(id);
    }
    public ArrayList<Attribute> getAttributeList() {
        return attributeList;
    }
    public Hashtable<String,Attribute> getAttributes() {
        return attributes;
    }
    public Method getMethod(String id) {
        return methods.get(id);
    }
    public Hashtable<String,Method> getMethods() {
        return methods;
    }
    public ArrayList<Method> getMethodList() {
        return methodList;
    }
}
