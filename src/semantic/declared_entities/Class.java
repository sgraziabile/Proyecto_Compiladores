package semantic.declared_entities;

import entities.Token;
import exceptions.ClassNotDeclaredException;
import exceptions.CyclicInheritanceException;

import java.util.ArrayList;
import java.util.Hashtable;

import static main.MainModule.symbolTable;


public class Class {
    private Token id;
    private Token superclass;
    private boolean isConsolidated = false;
    private Hashtable<String, Attribute> attributes;
    private ArrayList<Attribute> attributeList;
    private Hashtable<String, Method> methods;
    private ArrayList<Method> methodList;
    private boolean constructorDeclared = false;

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
    public void addAttribute(Attribute attribute) throws Exception {
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
    public void consolidate() {
        isConsolidated = true;
    }
    public void checkDeclaration() throws Exception {
        checkSuperclass();
        checkCircularInheritance();
        checkAttributes();
        checkMethods();
        checkConstructor();
    }
    private void checkSuperclass() throws Exception {
        if(superclass != null) {
            if(symbolTable.getClass(superclass.getLexeme()) == null) {
                throw new ClassNotDeclaredException(superclass.getLineNumber(), superclass.getLexeme());
            }
        }
    }
    private void checkAttributes() throws Exception {
        for(Attribute a : attributeList) {
            a.checkDeclaration();
        }
    }
    private void checkMethods() throws Exception {
        for(Method m : methodList) {
            m.checkDeclaration(this);
        }
    }
    private void checkCircularInheritance() throws Exception {
        if(superclass != null) {
            Class currentClass = this;
            while(currentClass.getSuperclass() != null) {
                currentClass = symbolTable.getClass(currentClass.getSuperclass().getLexeme());
                if(currentClass.getName().equals(this.getName())) {
                    throw new CyclicInheritanceException(this.getId().getLineNumber(), this.getName());
                }
            }
        }
    }
    public boolean isConstructorDeclared() {
        return constructorDeclared;
    }
    public void checkConstructor() {
        System.out.println("clase: " +id.getLexeme()+ constructorDeclared);
        if(!isConstructorDeclared()) {
            Method constructor = new Method();
            constructor.setId(new Token("idMetVar",id.getLexeme() ,0));
            constructor.setType(new PrimitiveType("constructor"));
            constructor.setModifier("dymamic");
            constructor.setVisibility("public");
            addMethod(constructor);
        }
    }
    public void setConstructorDeclared() {
        constructorDeclared = true;
    }
}
