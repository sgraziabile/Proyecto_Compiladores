package semantic.expression_entities;

import entities.Token;
import exceptions.CannotResolveMethodException;
import semantic.declared_entities.Type;

import java.util.ArrayList;

import static main.MainModule.symbolTable;

public class MethodAccessNode extends PrimaryNode {
    protected Token id;
    protected ArrayList<ExpressionNode> arguments;

    public MethodAccessNode() {

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
        String methodName = id.getLexeme();
        if(symbolTable.getCurrentClass().getMethod(methodName) == null) {
            throw new CannotResolveMethodException(id);
        }
        if(chained != null) {
            type = chained.typeCheck(this);
            return type;
        }
        return symbolTable.getCurrentClass().getMethod(methodName).getType();
    }
    public boolean isAssignable() {
        if(chained == null) {
            return false;
        } else {
            return chained.isAssignable();
        }
    }
    public String toString() {
        return id.getLexeme() + arguments.toString()+  (chained == null ? " " : chained.toString());
    }
}
