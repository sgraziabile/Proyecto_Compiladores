package semantic.declared_entities;

import entities.Token;

import java.util.Hashtable;


public class Class {
    private String name;
    private Token superclass;
    private boolean isConsolidated = false;
    private Hashtable<String, Attribute> attributes;
    private Hashtable<String, Method> methods;

    public Class(Token idClass) {
        name = idClass.getLexeme();
        superclass = null;
        attributes = new Hashtable<>();
        methods = new Hashtable<>();
    }
    public void setSuperclass(Token superclass) {
        this.superclass = superclass;
    }
    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getId(), attribute);
    }
    public void addMethod(Method method) {
        methods.put(method.getId(), method);
    }
    public String getName() {
        return name;
    }
    public Token getSuperclass() {
        return superclass;
    }
    public boolean isConsolidated() {
        return isConsolidated;
    }
}
