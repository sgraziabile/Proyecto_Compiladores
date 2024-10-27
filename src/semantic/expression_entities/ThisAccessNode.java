package semantic.expression_entities;

import semantic.declared_entities.ReferenceType;
import semantic.declared_entities.Type;

import static main.MainModule.symbolTable;

public class ThisAccessNode extends PrimaryNode {

    public ThisAccessNode() {

    }
    public String toString() {
        return "ThisAccessNode";
    }
    public Type typeCheck(PrimaryNode parentChain) throws Exception {
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
