package semantic;

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
}
