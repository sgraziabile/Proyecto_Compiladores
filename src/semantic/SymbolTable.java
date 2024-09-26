package semantic;

import semantic.declared_entities.Method;
import semantic.declared_entities.Class;

import java.util.Hashtable;

public class SymbolTable {
    boolean isConsolidated = false;
    private Class currentClass;
    private Method currentMethod;
    private Hashtable<String,Class> classes;

    public SymbolTable() {
        this.currentClass = null;
        this.currentMethod = null;
        this.classes = new Hashtable<>();
    }

    public void setCurrentClass(Class c) {
        currentClass = c;
    }
    public void setCurrentMethod(Method method) {
        currentMethod = method;
    }
    public void insertClass(Class c) {
        classes.put(c.getName(), c);
    }
    public void printClasses() {
        for (Class c : classes.values()) {
            System.out.println(c.getName() + " extends " + c.getSuperclass().getLexeme());
        }
    }
}
