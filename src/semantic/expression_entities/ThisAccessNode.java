package semantic.expression_entities;

import exceptions.StaticReferenceException;
import semantic.declared_entities.ReferenceType;
import semantic.declared_entities.Symbol;
import semantic.declared_entities.Type;

import static main.MainModule.symbolTable;

public class ThisAccessNode extends PrimaryNode {
    private Symbol reference;

    public ThisAccessNode() {

    }
    public String toString() {
        return "ThisAccessNode";
    }
    public Type typeCheck() throws Exception {
        Type type;
        if(symbolTable.getCurrentMethod().getModifier().equals("static")) {
            throw new StaticReferenceException(0, "this");
        }
        reference = symbolTable.getCurrentClass();
        if(chained != null) {
            type = chained.typeCheck(reference.getType());
        } else {
            type = reference.getType();
        }
        return type;
    }

}
