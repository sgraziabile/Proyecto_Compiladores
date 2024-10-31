package semantic.expression_entities;

import entities.Token;

public class PrimaryNode extends OperandNode {
    protected Chained chained;

    public PrimaryNode() {

    }
    public Chained getOptionalChained() {
        return chained;
    }
    public void setChained(Chained optionalChained) {
        this.chained = optionalChained;
    }
    public String toString() {
        return "PrimaryNode";
    }
    public boolean canBeCalled() {
        if(chained != null) {
            return chained.canBeCalled();
        } else {
            return false;
        }
    }
}
