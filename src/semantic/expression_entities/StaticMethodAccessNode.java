package semantic.expression_entities;

import entities.Token;

import java.util.ArrayList;

public class StaticMethodAccessNode extends PrimaryNode {
    protected Token methodId;
    protected Token className;
    protected ArrayList<ExpressionNode> arguments;

    public StaticMethodAccessNode() {

    }
    public StaticMethodAccessNode(Token methodId, Token className, ArrayList<ExpressionNode> arguments) {
        this.methodId = methodId;
        this.className = className;
        this.arguments = arguments;
    }
    public Token getMethodId() {
        return methodId;
    }
    public void setMethodId(Token methodId) {
        this.methodId = methodId;
    }
    public Token getClassName() {
        return className;
    }
    public void setClassName(Token className) {
        this.className = className;
    }
    public String toString() {
        return className.getLexeme() + "." + methodId.getLexeme();
    }
}
