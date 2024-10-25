package semantic.sentence_entities;

import semantic.expression_entities.PrimitiveLiteralNode;

public class CaseNode {
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
    public String toString() {
        String msg = "CaseNode: ";
        if(caseValue != null)
            msg += caseValue.toString() +" " +caseBody.toString();
        else
            msg += "default" +" " +caseBody.toString();
        return msg;
    }
}
