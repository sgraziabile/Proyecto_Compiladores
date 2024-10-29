package semantic.sentence_entities;

import entities.Token;
import exceptions.AlreadyDeclaredException;
import exceptions.InvalidLocalVarAssignment;
import exceptions.StaticReferenceException;
import semantic.declared_entities.Parameter;
import semantic.declared_entities.Symbol;
import semantic.declared_entities.Type;
import semantic.expression_entities.CompoundExpNode;

import static main.MainModule.symbolTable;

public class LocalVarNode extends SentenceNode implements Symbol {
    protected Token id;
    protected Token assignOp;
    protected CompoundExpNode expression;
    protected Type type;

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
    public void setType(Type type) {
        this.type = type;
    }
    public Type getType() {
        return type;
    }
    public void checkSentence() throws Exception {
        checkRepeatedVar();
        checkParameter();
        Type type;
        try {
            type = expression.typeCheck();
        } catch (StaticReferenceException e) {
            throw new StaticReferenceException(id.getLineNumber(), e.getToken());
        }
        if(type != null) {
            if(type.getName().equals("null")) {
                throw new InvalidLocalVarAssignment(id.getLineNumber());
            } else {
                setType(type);
            }
        }
    }
    private void checkRepeatedVar() throws Exception {
        if(parentBlock.getLocalVar(id.getLexeme()) == null) {
            Block previousBlock = parentBlock.getParentBlock();
            while(previousBlock != null) {
                if(previousBlock.getLocalVar(id.getLexeme()) != null) {
                    throw new AlreadyDeclaredException("local_var", id.getLineNumber(), id.getLexeme());
                }
                previousBlock = previousBlock.getParentBlock();
            }
            parentBlock.addLocalVar(this);
        } else {
            throw new AlreadyDeclaredException("local_var", id.getLineNumber(), id.getLexeme());
        }
    }
    private void checkParameter() throws Exception {
        if((symbolTable.getCurrentMethod().getParameter(id.getLexeme())) != null) {
            throw new AlreadyDeclaredException("local_var", id.getLineNumber(), id.getLexeme());
        }
    }
    public String toString() {
        return id.getLexeme() + " " + assignOp.getLexeme() + " " + expression.toString();
    }

}
