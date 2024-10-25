package semantic.sentence_entities;

import semantic.expression_entities.AssignmentExpNode;
import semantic.expression_entities.ExpressionNode;

public class AssignmentNode extends SentenceNode {
    private AssignmentExpNode assignmentExp;

    public AssignmentNode(AssignmentExpNode expression) {
        this.assignmentExp = expression;
    }
    public AssignmentNode() {
        this.assignmentExp = null;
    }
    public AssignmentExpNode getAssignmentExp() {
        return assignmentExp;
    }
    public void setAssignmentExp(AssignmentExpNode assignmentExp) {
        this.assignmentExp = assignmentExp;
    }
    public String toString() {
        return "AssignmentNode " + assignmentExp.toString();
    }
}
