package semantic.expression_entities;

import entities.Token;
import exceptions.CannotResolveMethodException;
import exceptions.PrimitiveTypeCallException;
import semantic.declared_entities.*;
import semantic.declared_entities.Class;

import java.util.ArrayList;

import static main.MainModule.symbolTable;

public class ChainedCallNode extends Chained {
    protected ArrayList<ExpressionNode> args;
    protected Type type;

    public ChainedCallNode(Token name) {
        this.id = name;
        this.args = new ArrayList<>();
    }
    public ChainedCallNode(Token name, Chained next) {
        this.id = name;
        this.chained = next;
        this.args = new ArrayList<>();
    }
    public ChainedCallNode() {
        this.args = new ArrayList<>();
    }
    public ArrayList<ExpressionNode> getArgs() {
        return args;
    }
    public void setArguments(ArrayList<ExpressionNode> args) {
        this.args = args;
    }
    public void addArgument(ExpressionNode arg) {
        this.args.add(arg);
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Type typeCheck(PrimaryNode parentChain) throws Exception {
        Type type = null;
        PrimaryNode parent;
        if(parentChain instanceof MethodAccessNode) {
            resolveMethodAccessName(parentChain);
        } else if(parentChain instanceof ChainedCallNode) {
            resolveChainedMethodName(parentChain);
        } else if(parentChain instanceof VarAccessNode) {
            resolveChainVarName(parentChain);
        }
        if(chained != null) {
            type = chained.typeCheck(this);
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
                if(symbolTable.getClass(methodType.getName()).getMethod(id.getLexeme()) == null) {
                    throw new CannotResolveMethodException(id);
                } else {
                    this.type = symbolTable.getClass(methodType.getName()).getMethod(id.getLexeme()).getType();
                }
            }
            else {
                throw new PrimitiveTypeCallException(parent.getId(), id,methodType);
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
                if(symbolTable.getClass(methodType.getName()).getMethod(id.getLexeme()) == null) {
                    throw new CannotResolveMethodException(id);
                } else {
                    this.type = ((ChainedCallNode) parentChain).getType();
                }
            }
            else {
                throw new PrimitiveTypeCallException(parent.getId(), id,methodType);
            }
        }
    }
    private void resolveChainVarName(PrimaryNode parentChain) throws Exception {
        VarAccessNode parent;
        boolean found = false;
        if(parentChain instanceof VarAccessNode) {
            VarAccessNode var = (VarAccessNode) parentChain;
            if (var.getType() instanceof PrimitiveType) {
                throw new PrimitiveTypeCallException(id, var.getId(), var.getType());
            } else {
                ReferenceType refType = (ReferenceType)var.getType();
                Class c = symbolTable.getClass(refType.getName());
                for(Method m: c.getMethods().values()) {
                    if(m.getName().equals(id.getLexeme())) {
                        if(m.getParameterList().size() == args.size()) {
                            for(int i = 0; i < args.size(); i++) {
                                if(!args.get(i).typeCheck().conformsTo(m.getParameterList().get(i).getType())) {
                                    throw new CannotResolveMethodException(id);
                                }
                            }
                            this.type = m.getType();
                            found = true;
                        } else {
                            throw new CannotResolveMethodException(id);
                        }
                    }
                }
                if(!found) {
                    throw new CannotResolveMethodException(id);
                }
            }
        }
    }
    public boolean isAssignable() {
        if(chained == null) {
            return false;
        } else {
            return chained.isAssignable();
        }
    }
    public boolean canBeCalled() {
        return true;
    }
    public String toString() {
        return id.getLexeme() + args.toString()+  (chained == null ? " " : chained.toString());
    }
}
