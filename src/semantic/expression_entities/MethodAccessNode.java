package semantic.expression_entities;

import entities.Token;

import java.util.ArrayList;

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
    public String toString() {
        return id.getLexeme() + (arguments == null ? " " : arguments.toString());
    }
}
