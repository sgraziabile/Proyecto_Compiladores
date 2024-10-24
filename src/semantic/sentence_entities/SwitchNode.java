package semantic.sentence_entities;

import semantic.expression_entities.ExpressionNode;

import java.util.ArrayList;

public class SwitchNode extends SentenceNode {
    protected ExpressionNode expression;
    protected ArrayList<CaseNode> cases;
    protected CaseNode defaultCase;

    public SwitchNode(ExpressionNode expression, ArrayList<CaseNode> cases, CaseNode defaultCase) {
        this.expression = expression;
        this.cases = cases;
        this.defaultCase = defaultCase;
    }
    public SwitchNode() {
        this.expression = null;
        this.cases = new ArrayList<CaseNode>();
        this.defaultCase = null;
    }
    public ExpressionNode getExpression() {
        return expression;
    }
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
    public ArrayList<CaseNode> getCases() {
        return cases;
    }
    public void setCases(ArrayList<CaseNode> cases) {
        this.cases = cases;
    }
    public CaseNode getDefaultCase() {
        return defaultCase;
    }
    public void setDefaultCase(CaseNode defaultCase) {
        this.defaultCase = defaultCase;
    }
    public String toString() {
        String msg = "SwitchNode: ";
        return msg + expression.toString() + cases.toString();
    }
}
