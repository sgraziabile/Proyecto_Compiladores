package semantic.sentence_entities;

import code_generator.CodeGenerator;
import semantic.expression_entities.CompoundExpNode;

import static main.MainModule.writer;

public class IfWithElseNode extends IfNode {

    protected SentenceNode elseBody;

    public IfWithElseNode(CompoundExpNode condition, SentenceNode thenBody, SentenceNode elseBody) {
        super(condition, thenBody);
        this.elseBody = elseBody;
    }
    public IfWithElseNode() {
        super();
        this.elseBody = null;
    }
    public SentenceNode getElseBody() {
        return elseBody;
    }
    public void setElseBody(SentenceNode elseBody) {
        this.elseBody = elseBody;
    }
    public void checkSentence() throws Exception {
        super.checkSentence();
        elseBody.checkSentence();
    }
    public void setBreakable() {
        super.setBreakable();
        elseBody.setBreakable();
    }
    public void generateCode() throws Exception{
        String elseLabel = CodeGenerator.generateElseLabel();
        String endLabel = CodeGenerator.generateEndIfLabel();
        condition.generateCode();
        writer.write(CodeGenerator.BF + " " + elseLabel + " ; Salto al else \n");
        thenBody.generateCode();
        writer.write(CodeGenerator.JUMP + " " + endLabel + " ; Salto al final del if \n");
        writer.write(elseLabel + ": NOP \n");
        elseBody.generateCode();
        writer.write(endLabel + ": NOP \n");
    }
    public String toString() {
        return "IfWithElseNode";
    }
}
