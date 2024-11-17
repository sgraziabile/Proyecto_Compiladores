package semantic.expression_entities;

import code_generator.CodeGenerator;
import exceptions.StaticReferenceException;
import semantic.declared_entities.ReferenceType;
import semantic.declared_entities.Symbol;
import semantic.declared_entities.Type;

import static main.MainModule.symbolTable;
import static main.MainModule.writer;

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
    public boolean canBeCalled() {
        if(chained != null) {
            return chained.canBeCalled();
        } else {
            return false;
        }
    }
    public void generateCode() throws Exception {
        writer.write(CodeGenerator.LOAD+ " 3 ; Cargo la referencia al this \n");
        if(chained != null) {
            chained.generateCode();
        }
    }

}
