package semantic.expression_entities;

import entities.Token;
import exceptions.CantResolveSymbolException;
import semantic.declared_entities.Parameter;
import semantic.sentence_entities.LocalVarNode;
import semantic.sentence_entities.SentenceNode;

import java.util.ArrayList;

import static main.MainModule.symbolTable;

public class VarAccessNode extends PrimaryNode {
    protected Token id;

    public VarAccessNode(Token id) {
        this.id = id;
    }
    public Token getId() {
        return id;
    }
    public void setId(Token id) {
        this.id = id;
    }
    public void resolveNames() throws Exception {
        boolean isDeclared = false;
        isDeclared = checkLocalVar(id);
        if(!isDeclared) {
            isDeclared = checkParameter(id);
            if (!isDeclared)
                isDeclared = checkAttribute(id);
        }
        if(!isDeclared) {
            throw new CantResolveSymbolException(id.getLineNumber(), id.getLexeme());
        }
    }
    public String toString() {
        return id.getLexeme() + " " + (chained == null ? "" : chained.toString());
    }
    private boolean checkLocalVar(Token var) throws Exception {
        boolean declared = false;
        String varName = var.getLexeme();
        ArrayList<SentenceNode> sentenceList = symbolTable.getCurrentMethod().getMainBlock().getSentenceList();
            for(SentenceNode sentence : sentenceList) {
                if (sentence instanceof LocalVarNode) {
                    LocalVarNode varNode = (LocalVarNode) sentence;
                    if (varNode.getId().getLexeme().equals(varName)) {
                        declared = true;
                        break;
                    }
                } else {
                    //cortar cuando encuentro la sentencia que contiene la llamada
                }
            }
        return declared;
    }
    private boolean checkParameter(Token var) throws Exception {
        boolean declared = false;
        String varName = var.getLexeme();
        ArrayList<Parameter> parameterList = symbolTable.getCurrentMethod().getParameterList();
        for(Parameter parameter : parameterList) {
            if(parameter.getId().getLexeme().equals(varName)) {
                declared = true;
            }
        }
        return declared;
    }
    private boolean checkAttribute(Token var) throws Exception {
        boolean declared = false;
        String varName = var.getLexeme();
        if(symbolTable.getCurrentClass().getAttribute(varName) != null) {
            declared = true;
        }
        return declared;
    }


}
