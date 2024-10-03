package semantic;

import entities.Token;
import semantic.declared_entities.Method;
import semantic.declared_entities.Class;

import java.util.Hashtable;

public class SymbolTable {
    boolean isConsolidated = false;
    private Class currentClass;
    private Method currentMethod;
    private Hashtable<String,Class> classHash;

    public SymbolTable() {
        this.currentClass = null;
        this.currentMethod = null;
        this.classHash = new Hashtable<>();
        initBaseClasses();
    }
    public void setCurrentClass(Class c) {
        currentClass = c;
    }
    public void setCurrentMethod(Method method) {
        currentMethod = method;
    }
    public void insertClass(Class c) {
        classHash.put(c.getName(), c);
    }
    public void printClasses() {
        for (Class c : classHash.values()) {
            System.out.println(c.getName() + " extends " + c.getSuperclass().getLexeme());
        }
    }
    public Class getCurrentClass() {
        return currentClass;
    }
    public Method getCurrentMethod() {
        return currentMethod;
    }
    public Class getClass(String id) {
        return classHash.get(id);
    }
    public Hashtable<String,Class> getClasses() {
        return classHash;
    }
    public void checkDeclarations() throws Exception {
        for(Class c : classHash.values()) {
            if (!c.getName().equals("Object") && !c.getName().equals("System") && !c.getName().equals("String")) {
                c.checkDeclaration();
            }
        }
    }
    public void consolidate() {
        isConsolidated = true;
    }
    private void initBaseClasses() {
        Class objectClass = new Class(new Token("idClase", "Object", 0));
        insertClass(objectClass);
        Class stringClass = new Class(new Token("idClase", "String", 0));
        stringClass.setSuperclass(objectClass.getId());
        insertClass(stringClass);
        Class systemClass = new Class(new Token("idClase", "System", 0));
        systemClass.setSuperclass(objectClass.getId());
        insertClass(systemClass);
    }
}
