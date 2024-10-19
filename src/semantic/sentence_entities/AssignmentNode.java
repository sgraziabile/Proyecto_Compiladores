package semantic.sentence_entities;

import semantic.expression_entities.ExpressionNode;

public class AssignmentNode extends SentenceNode {
    private ExpressionNode assignmentExp;

    public AssignmentNode(ExpressionNode expression) {
        this.assignmentExp = expression;
    }
    public AssignmentNode() {
        this.assignmentExp = null;
    }
    public ExpressionNode getAssignmentExp() {
        return assignmentExp;
    }
    public void setAssignmentExp(ExpressionNode assignmentExp) {
        this.assignmentExp = assignmentExp;
    }
}
