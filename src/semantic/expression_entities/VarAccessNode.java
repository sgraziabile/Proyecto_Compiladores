package semantic.expression_entities;

import entities.Token;
import exceptions.CantResolveSymbolException;
import exceptions.PrimitiveTypeCallException;
import exceptions.StaticReferenceException;
import semantic.declared_entities.Attribute;
import semantic.declared_entities.Parameter;
import semantic.declared_entities.PrimitiveType;
import semantic.declared_entities.Type;
import semantic.sentence_entities.LocalVarNode;

import java.util.ArrayList;

import static main.MainModule.symbolTable;

public class VarAccessNode extends PrimaryNode {
    protected Token id;
    private Type type;
    private boolean isAssignable;

    public VarAccessNode(Token id) {
        this.id = id;
    }
    public Token getId() {
        return id;
    }
    public void setId(Token id) {
        this.id = id;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Type getType() {
        return type;
    }
    public boolean isAssignable() {
        if(chained == null) {
            return true;
        } else {
            return chained.isAssignable();
        }
    }
    public Type typeCheck() throws Exception {
        Type type;
        boolean isDeclared;
        isDeclared = checkLocalVar(id);
        if(!isDeclared) {
            isDeclared = checkParameter(id);
            if (!isDeclared)
                isDeclared = checkAttribute(id);
        }
        if(!isDeclared) {
            throw new CantResolveSymbolException(id.getLineNumber(), id.getLexeme());
        }
        if(chained != null) {
            type = chained.typeCheck(this);
        } else {
            type = this.type;
        }
        return type;
    }
    private boolean checkLocalVar(Token var) throws Exception {
        boolean declared = false;
        String varName = var.getLexeme();
        if(symbolTable.getCurrentBlock() != null) {
            LocalVarNode localVar = symbolTable.getCurrentBlock().getLocalVar(varName);
            if(localVar != null) {
                if(localVar.getId().getLineNumber() <= var.getLineNumber()) {
                    setType(localVar.getType());
                    declared = true;
                }
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
                setType(parameter.getType());
                declared = true;
            }
        }
        return declared;
    }
    private boolean checkAttribute(Token var) throws Exception {
        boolean declared = false;
        String varName = var.getLexeme();
        Attribute attribute = symbolTable.getCurrentClass().getAttribute(varName);
        if(attribute != null) {
            if(attribute.getModifier().equals("dynamic") && symbolTable.getCurrentMethod().getModifier().equals("static")) {
                throw new StaticReferenceException(var.getLineNumber(), var.getLexeme());
            }
            setType(attribute.getType());
            declared = true;
        }
        return declared;
    }
    public boolean canBeCalled() {
        if(chained == null) {
            return true;
        } else {
            return chained.canBeCalled();
        }
    }
    public String toString() {
        return id.getLexeme() + " " + (chained == null ? "" : chained.toString());
    }

}
