package semantic.declared_entities;

import entities.Token;

import java.util.Hashtable;


public class Class {
    private String name;
    private Token superclass;
    private boolean isConsolidated = false;
    private Hashtable<String, Attribute> attributes;
    private Hashtable<String, Method> methods;

    public Class(String name, Token superclass) {
        this.name = name;
        this.superclass = superclass;
        this.attributes = new Hashtable<>();
        this.methods = new Hashtable<>();
    }
}
