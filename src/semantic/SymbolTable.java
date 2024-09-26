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
}
