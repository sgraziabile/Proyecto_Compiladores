package semantic.sentence_entities;

import exceptions.IncompatibleTypesException;
import semantic.declared_entities.Type;
import semantic.expression_entities.PrimitiveLiteralNode;

public class CaseNode extends SentenceNode {
    protected PrimitiveLiteralNode caseValue;
    protected SentenceNode caseBody;

    public CaseNode(PrimitiveLiteralNode caseValue, SentenceNode caseBody) {
        this.caseValue = caseValue;
        this.caseBody = caseBody;
    }
    public CaseNode() {
        this.caseValue = null;
        this.caseBody = null;
    }
    public PrimitiveLiteralNode getCaseValue() {
        return caseValue;
    }
    public void setCaseValue(PrimitiveLiteralNode caseValue) {
        this.caseValue = caseValue;
    }
    public SentenceNode getCaseBody() {
        return caseBody;
    }
    public void setCaseBody(SentenceNode caseBody) {
        this.caseBody = caseBody;
    }
    public SentenceNode getBody() {
        return caseBody;
    }
    public void checkSentence(Type expressionType) throws Exception {
        if(caseValue != null) {
            Type caseType = caseValue.typeCheck();
            if(!caseType.getName().equals(expressionType.getName())) {
                throw new IncompatibleTypesException(expressionType, caseType, line, caseValue.getValue().getLexeme());
            }
        }
        caseBody.checkSentence();
    }
    public void checkSentence() throws Exception {
        caseBody.checkSentence();
    }
    public void setBreakable() {
        caseBody.setBreakable();
    }
    public String toString() {
        String msg = "CaseNode: ";
        if(caseValue != null)
            msg += caseValue.toString() +" " +caseBody.toString();
        else
            msg += "default" +" " +caseBody.toString();
        return msg;
    }
}
