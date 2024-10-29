package semantic.expression_entities;

import entities.Token;
import exceptions.CantResolveSymbolException;
import exceptions.PrimitiveTypeCallException;
import exceptions.StaticReferenceException;
import semantic.declared_entities.*;
import semantic.sentence_entities.LocalVarNode;

import java.sql.Ref;
import java.util.ArrayList;

import static main.MainModule.symbolTable;

public class VarAccessNode extends PrimaryNode {
    protected Token id;
    private Type type;
    private Symbol reference;


    public VarAccessNode(Token id) {
        this.id = id;
        reference = null;
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
    public void setReference(Symbol reference) {
        this.reference = reference;
    }
    public Symbol getReference() {
        return reference;
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
        isDeclared = checkParameter(id);
        if(!isDeclared) {
            isDeclared = checkLocalVar(id);
            if (!isDeclared)
                isDeclared = checkAttribute(id);
        }
        if(!isDeclared) {
            throw new CantResolveSymbolException(id.getLineNumber(), id.getLexeme());
        }
        type = reference.getType();
        if(chained != null) {
            if(type instanceof ReferenceType) {
                type = chained.typeCheck(reference.getType());
            }
            else {
                throw new PrimitiveTypeCallException(id, chained.getId(), type);
            }
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
                    reference = (Symbol) localVar;
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
                reference = (Symbol) parameter;
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
            reference = (Symbol) attribute;
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
