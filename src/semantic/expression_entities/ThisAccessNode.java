package semantic.expression_entities;

import exceptions.StaticReferenceException;
import semantic.declared_entities.ReferenceType;
import semantic.declared_entities.Type;

import static main.MainModule.symbolTable;

public class ThisAccessNode extends PrimaryNode {

    public ThisAccessNode() {

    }
    public String toString() {
        return "ThisAccessNode";
    }
    public Type typeCheck() throws Exception {
        if(symbolTable.getCurrentMethod().getModifier().equals("static")) {
            throw new StaticReferenceException(0, "this");
        }
        String className = symbolTable.getCurrentClass().getName();
        return new ReferenceType(className);
    }
    public boolean isAssignable() {
        if(chained == null) {
            return true;
        } else {
            return chained.isAssignable();
        }
    }

}
