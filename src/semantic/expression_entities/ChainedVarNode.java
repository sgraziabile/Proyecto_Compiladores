package semantic.expression_entities;

import entities.Token;
import semantic.declared_entities.ReferenceType;
import semantic.declared_entities.Type;

import static main.MainModule.symbolTable;

public class ChainedVarNode extends Chained{

    public ChainedVarNode(Token name) {
        this.id = name;
    }
    public ChainedVarNode(Token name, Chained next) {
        this.id = name;
        this.chained = next;
    }
    public ChainedVarNode() {

    }
    public Type typeCheck(PrimaryNode parentChain) throws Exception {
        Type type = null;
        if(parentChain instanceof MethodAccessNode) {
            resolveMethodAccessName(parentChain);
        } else if(parentChain instanceof ChainedCallNode) {
            resolveChainedMethodName(parentChain);
        }
        if(chained != null) {
            type = chained.typeCheck(this);
        } else {

        }
        return type;
    }
    private void resolveMethodAccessName(PrimaryNode parentChain) throws Exception {
        MethodAccessNode parent;
        if(parentChain instanceof MethodAccessNode) {
            parent = (MethodAccessNode) parentChain;
            String methodName = parent.getId().getLexeme();
            Type methodType = symbolTable.getCurrentClass().getMethod(methodName).getType();
            if(methodType instanceof ReferenceType) {

            } else {

            }
        }
    }
    private void resolveChainedMethodName(PrimaryNode parentChain) throws Exception {
        ChainedCallNode parent;
        if(parentChain instanceof ChainedCallNode) {
            parent = (ChainedCallNode) parentChain;
            String methodName = parent.getId().getLexeme();
            Type methodType = symbolTable.getCurrentClass().getMethod(methodName).getType();
            if(methodType instanceof ReferenceType) {

            } else {

            }
        }
    }

}
