package semantic.expression_entities;

import entities.Token;
import exceptions.CannotResolveMethodException;
import exceptions.ClassNotDeclaredException;
import semantic.declared_entities.*;
import semantic.declared_entities.Class;

import java.util.ArrayList;

import static main.MainModule.symbolTable;

public class ConstructorAccessNode extends PrimaryNode{
    private Token id;
    private ArrayList<ExpressionNode> arguments;
    private Symbol reference;

    public ConstructorAccessNode(Token id, ArrayList<ExpressionNode> arguments) {
        this.id = id;
        this.arguments = arguments;
    }
    public ConstructorAccessNode() {

    }
    public Token getId() {
        return id;
    }
    public void setId(Token id) {
        this.id = id;
    }
    public ArrayList<ExpressionNode> getArguments() {
        return arguments;
    }
    public void setArguments(ArrayList<ExpressionNode> arguments) {
        this.arguments = arguments;
    }
    public Type typeCheck() throws Exception{
        Type type;
        reference = symbolTable.getClass(id.getLexeme());
        if(reference == null) {
            throw new ClassNotDeclaredException(id.getLineNumber(), id.getLexeme());
        } else {
            checkArguments();
            if(chained != null) {
                type = chained.typeCheck(reference.getType());
            }
            else {
                type = reference.getType();
            }
        }
        return type;
    }
    public void checkArguments() throws Exception{
        String constructorName = id.getLexeme();
        ArrayList<Parameter> methodArgs = ((Class)reference).getMethod(constructorName).getParameterList();
        if(arguments.size() != methodArgs.size()) {
            throw new CannotResolveMethodException(id);
        }
        for(int i = 0; i < arguments.size(); i++) {
            if(!arguments.get(i).typeCheck().conformsTo(methodArgs.get(i).getType())) {
                throw new CannotResolveMethodException(id);
            }
        }
    }
    public boolean canBeCalled() {
        if(chained != null) {
            return chained.canBeCalled();
        } else {
            return true;
        }
    }
    public String toString() {
        return id.getLexeme() + (arguments == null ? " " : arguments.toString());
    }
}
