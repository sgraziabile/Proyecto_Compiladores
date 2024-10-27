package semantic.expression_entities;

import entities.Token;
import exceptions.CannotResolveMethodException;
import exceptions.PrimitiveTypeCallException;
import semantic.declared_entities.ReferenceType;
import semantic.declared_entities.Type;

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
        }
        if(chained != null) {
            type = chained.typeCheck(this);
        } else {
            //encontrar la clase que tiene el metodo
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
                }
            }
            else {
                throw new PrimitiveTypeCallException(parent.getId(), id,methodType);
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
    public String toString() {
        return id.getLexeme() + args.toString()+  (chained == null ? " " : chained.toString());
    }
}
