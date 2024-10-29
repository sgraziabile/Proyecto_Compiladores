package semantic.expression_entities;

import entities.Token;
import exceptions.CantResolveSymbolException;
import semantic.declared_entities.Class;
import semantic.declared_entities.ReferenceType;
import semantic.declared_entities.Type;

import static main.MainModule.symbolTable;

public class ChainedVarNode extends Chained{
    protected Type type;

    public ChainedVarNode(Token name) {
        this.id = name;
    }
    public ChainedVarNode(Token name, Chained next) {
        this.id = name;
        this.chained = next;
    }
    public ChainedVarNode() {

    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Type typeCheck(PrimaryNode parentChain) throws Exception {
        Type type = null;
        if(parentChain instanceof MethodAccessNode) {
            resolveMethodAccessName(parentChain);
        } else if(parentChain instanceof ChainedMethodCallNode) {
            resolveChainedMethodName(parentChain);
        } else if(parentChain instanceof VarAccessNode) {
            resolveChainVarName(parentChain);
        }
        if(chained != null) {
            type = chained.typeCheck(this);
        } else {

        }
        return type;
    }
    private void resolveMethodAccessName(PrimaryNode parentChain) throws Exception {
        MethodAccessNode parent;
        parent = (MethodAccessNode) parentChain;
        String methodName = parent.getId().getLexeme();
        Type methodType = symbolTable.getCurrentClass().getMethod(methodName).getType();
        if(methodType instanceof ReferenceType) {

        } else {

        }
    }
    private void resolveChainedMethodName(PrimaryNode parentChain) throws Exception {
        ChainedMethodCallNode parent;
        parent = (ChainedMethodCallNode) parentChain;
        Type methodType = parent.getType();
        if(methodType instanceof ReferenceType) {
            for(Class c: symbolTable.getClasses().values()) {
                if(c.getName().equals(methodType.getName())) {
                    if (c.getAttribute(id.getLexeme()) == null) {
                        throw new CantResolveSymbolException(id.getLineNumber(), id.getLexeme());
                    } else {
                        this.type = c.getAttribute(id.getLexeme()).getType();
                    }
                }
            }
        } else {
            throw new Exception("Primitive type call");
        }
    }
    private void resolveChainVarName(PrimaryNode parentChain) throws Exception {

    }
    public boolean isAssignable() {
        if(chained == null) {
            return true;
        } else {
            return chained.isAssignable();
        }
    }
    public boolean canBeCalled() {
        return true;
    }
}
