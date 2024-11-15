package semantic.sentence_entities;

import code_generator.CodeGenerator;
import semantic.expression_entities.AssignmentExpNode;
import semantic.expression_entities.ExpressionNode;

import static main.MainModule.writer;

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
    public void checkSentence() throws Exception {
        assignmentExp.typeCheck();
    }
    public void generateCode() throws Exception {
        assignmentExp.generateCode();
        writer.write(CodeGenerator.POP + " ; Se desapila el valor de la asignacion \n");
    }
    public String toString() {
        return "AssignmentNode " + assignmentExp.toString();
    }
}
