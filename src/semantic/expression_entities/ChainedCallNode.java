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

    public ChainedCallNode(Token name) {
        this.name = name;
        this.args = new ArrayList<>();
    }
    public ChainedCallNode(Token name, Chained next) {
        this.name = name;
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
    public Type check (Type type) {
        return type;
    }
    public void resolveNames(PrimaryNode parentChain) throws Exception {
        MethodAccessNode parent;
        if(parentChain instanceof MethodAccessNode) {
            parent = (MethodAccessNode) parentChain;
            String methodName = parent.getId().getLexeme();
            Type methodType = symbolTable.getCurrentClass().getMethod(methodName).getType();
            if(methodType instanceof ReferenceType) {
                if(symbolTable.getClass(methodType.getName()).getMethod(name.getLexeme()) == null) {
                    throw new CannotResolveMethodException(name);
                }
            }
            else {
                throw new PrimitiveTypeCallException(parent.getId(),name,methodType);
            }
        }
    }
    public String toString() {
        return name.getLexeme() + args.toString()+  (chained == null ? " " : chained.toString());
    }
}
