package semantic.sentence_entities;

import code_generator.CodeGenerator;
import exceptions.IncompatibleTypesException;
import exceptions.InvalidSwitchTypeException;
import exceptions.RepeatedCaseLabelException;
import exceptions.RepeatedDefaultCaseException;
import semantic.declared_entities.PrimitiveType;
import semantic.declared_entities.Type;
import semantic.expression_entities.ExpressionNode;

import java.util.ArrayList;

import static main.MainModule.writer;

public class SwitchNode extends SentenceNode {
    protected ExpressionNode expression;
    protected ArrayList<CaseNode> cases;
    protected CaseNode defaultCase;
    protected ArrayList<CaseNode> defaultCases;

    public SwitchNode(ExpressionNode expression, ArrayList<CaseNode> cases, CaseNode defaultCase) {
        this.expression = expression;
        this.cases = cases;
        this.defaultCase = defaultCase;
        this.defaultCases = new ArrayList<CaseNode>();
    }
    public SwitchNode() {
        this.expression = null;
        this.cases = new ArrayList<CaseNode>();
        this.defaultCase = null;
        this.defaultCases = new ArrayList<CaseNode>();
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
        for(CaseNode c : cases) {
            c.setBreakable();
        }
        this.cases = cases;
    }
    public CaseNode getDefaultCase() {
        return defaultCase;
    }
    public void setDefaultCase(CaseNode defaultCase) {
        defaultCases.add(defaultCase);
        defaultCase.setBreakable();
        this.defaultCase = defaultCase;
    }
    public void checkSentence() throws Exception {
        Type type;
        type = expression.typeCheck();
        if(type.getName().equals("int") || type.getName().equals("char") || type.getName().equals("boolean")) {
            for (int i = 0; i < cases.size(); i++) {
                CaseNode c = cases.get(i);
                checkRepeatedLabel(c.getCaseValue().getValue().getLexeme(),i+1);
                c.checkSentence(type);
            }
            if(defaultCases.size() == 1)
                defaultCase.checkSentence(type);
            else if(defaultCases.size() > 1)
                throw new RepeatedDefaultCaseException(defaultCases.get(1).getLine());
        }
        else {
            throw new InvalidSwitchTypeException(type,line);
        }
    }
    public void generateCode() throws Exception {
        String endLabel = CodeGenerator.generateEndSwitchLabel();
        for(CaseNode c : cases) {
            expression.generateCode();
            writer.write("\n");
            c.generateValueCode();
        }
        if(defaultCase != null) {
            String defaultLabel = CodeGenerator.generateDefaultLabel();
            writer.write(CodeGenerator.JUMP + " " + defaultLabel + " ; Salto al default \n");
        } else {
            writer.write(CodeGenerator.JUMP + " " + endLabel + " ; Salto al final del switch \n");
        }
        writer.write("\n");
        for(CaseNode c : cases) {
            c.generateBodyCode();
        }
        if(defaultCase != null) {
            defaultCase.generateValueCode();
            defaultCase.generateBodyCode();
        }
        writer.write(endLabel + ": NOP \n");
    }
    private void checkRepeatedLabel(String label, int index) throws Exception {
        for(int i = index; i < cases.size(); i++) {
            CaseNode caseNode = cases.get(i);
            if(caseNode.getCaseValue().getValue().getLexeme().equals(label)) {
                throw new RepeatedCaseLabelException(caseNode.getLine(),label);
            }
        }
    }
    public String toString() {
        String msg = "SwitchNode: ";
        return msg + expression.toString() + cases.toString() + " " +(defaultCase == null ? "" : defaultCase.toString());
    }
}
