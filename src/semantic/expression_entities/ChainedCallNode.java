package semantic.expression_entities;

import entities.Token;
import semantic.declared_entities.Type;

import java.util.ArrayList;

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
    public String toString() {
        return name.getLexeme() + args.toString()+  (chained == null ? " " : chained.toString());
    }
}
