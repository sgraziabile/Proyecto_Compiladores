package semantic.sentence_entities;

import entities.Token;
import semantic.expression_entities.CompoundExpNode;

public class LocalVarNode extends SentenceNode {
    protected Token id;
    protected Token assignOp;
    protected CompoundExpNode expression;

    public LocalVarNode(Token id, Token assignOp, CompoundExpNode expression) {
        this.id = id;
        this.assignOp = assignOp;
        this.expression = expression;
    }
    public LocalVarNode() {
        this.id = null;
        this.assignOp = null;
        this.expression = null;
    }
    public Token getId() {
        return id;
    }
    public Token getAssignOp() {
        return assignOp;
    }
    public CompoundExpNode getExpression() {
        return expression;
    }
    public void setId(Token id) {
        this.id = id;
    }
    public void setAssignOp(Token assignOp) {
        this.assignOp = assignOp;
    }
    public void setExpression(CompoundExpNode expression) {
        this.expression = expression;
    }
    public String toString() {
        return id.getLexeme() + " " + assignOp.getLexeme() + " " + expression.toString();
    }

}
