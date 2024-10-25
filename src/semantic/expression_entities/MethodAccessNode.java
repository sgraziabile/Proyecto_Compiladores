package semantic.expression_entities;

import entities.Token;
import exceptions.CannotResolveMethodException;

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
    public void resolveNames() throws Exception{
        String methodName = id.getLexeme();
        if(symbolTable.getCurrentClass().getMethod(methodName) == null) {
            throw new CannotResolveMethodException(id);
        }
        if(chained != null) {
            chained.resolveNames(this);
        }
    }
    public void typeCheck() throws Exception {

    }
    public String toString() {
        return id.getLexeme() + (arguments == null ? " " : arguments.toString());
    }
}
