package semantic.sentence_entities;

import code_generator.CodeGenerator;
import exceptions.IncompatibleTypesException;
import semantic.declared_entities.Type;
import semantic.expression_entities.PrimitiveLiteralNode;

import static main.MainModule.writer;

public class CaseNode extends SentenceNode {
    protected PrimitiveLiteralNode caseValue;
    protected SentenceNode caseBody;
    private String label;

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
    public void generateValueCode() throws Exception{
        if(caseValue != null) {
            label = CodeGenerator.generateCaseLabel();
            caseValue.generateCode();
            writer.write(CodeGenerator.EQ + " ; Chequear el valor\n");
            writer.write(CodeGenerator.BT + " " + label + " ; Salto al case \n");
            writer.write("\n");
        } else {
            label = CodeGenerator.getCurrentDefaultLabel();
        }
    }
    public void generateBodyCode() throws Exception{
        writer.write(label + ": NOP \n");
        caseBody.generateCode();
        writer.write(CodeGenerator.JUMP + " " + CodeGenerator.getCurrentLoopLabel() + " ; Salto al final del switch \n");
        writer.write("\n");
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
