package semantic.expression_entities;

import entities.Token;
import exceptions.CannotResolveMethodException;
import exceptions.PrimitiveTypeCallException;
import semantic.declared_entities.*;
import semantic.declared_entities.Class;

import java.util.ArrayList;

import static main.MainModule.symbolTable;

public class ChainedMethodCallNode extends Chained {
    protected ArrayList<ExpressionNode> args;
    protected Type type;
    protected Symbol reference;

    public ChainedMethodCallNode(Token name) {
        this.id = name;
        this.args = new ArrayList<>();
    }
    public ChainedMethodCallNode(Token name, Chained next) {
        this.id = name;
        this.chained = next;
        this.args = new ArrayList<>();
    }
    public ChainedMethodCallNode() {
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
    public Type typeCheck(Type type) throws Exception {
        Type chainedType;
        findReference(type);
        chainedType = reference.getType();
        if(chained != null) {
            if(chainedType instanceof ReferenceType) {
                chainedType = chained.typeCheck(reference.getType());
            } else {
                throw new PrimitiveTypeCallException(id, chained.getId(), chainedType);
            }
        }
        return chainedType;
    }
    private void findReference(Type type) throws Exception {
        Class classRef;
        classRef = symbolTable.getClass(type.getName());
        Method method = classRef.getMethod(id.getLexeme());
        if(method != null) {
            if(method.getVisibility().equals("public")) {
                reference = method;
            } else {
                throw new CannotResolveMethodException(id);
            }
        } else {
            throw new CannotResolveMethodException(id);
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
