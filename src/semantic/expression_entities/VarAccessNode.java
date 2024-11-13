package semantic.expression_entities;

import code_generator.CodeGenerator;
import entities.Token;
import exceptions.CantResolveSymbolException;
import exceptions.PrimitiveTypeCallException;
import exceptions.StaticReferenceException;
import exceptions.VariableNotInitializedException;
import semantic.declared_entities.*;
import semantic.sentence_entities.LocalVarNode;

import java.sql.Ref;
import java.util.ArrayList;

import static main.MainModule.symbolTable;
import static main.MainModule.writer;

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
                throw new PrimitiveTypeCallException(chained.getId(), id, type);
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
                if(localVar.getType() != null) {
                    if (localVar.getId().getLineNumber() <= var.getLineNumber()) {
                        reference = localVar;
                        declared = true;
                    }
                } else {
                    throw new VariableNotInitializedException(varName, var.getLineNumber());
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
                reference = parameter;
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
            reference = attribute;
            declared = true;
        }
        return declared;
    }
    public boolean canBeCalled() {
        if(chained == null) {
            return false;
        } else {
            return chained.canBeCalled();
        }
    }
    public void generateCode() throws Exception {
        if(reference instanceof Parameter) {
            Parameter parameter = (Parameter) reference;
            int paramOffset = parameter.getOffset();
            writer.write(CodeGenerator.LOAD + " "+paramOffset+" ; Carga el valor del parametro "+id.getLexeme()+"\n");
        } else if (reference instanceof LocalVarNode) {
            LocalVarNode localVar = (LocalVarNode) reference;
            int localVarOffset = localVar.getOffset();
            writer.write(CodeGenerator.LOAD + " "+localVarOffset+" ; Carga el valor de la variable local "+id.getLexeme()+"\n");
        } else if (reference instanceof Attribute) {
            Attribute attribute = (Attribute) reference;
            int attrOffset = attribute.getOffset();
            writer.write(CodeGenerator.LOAD + " 3 ; Carga la referencia al CIR \n");
            writer.write(CodeGenerator.LOADREF + " "+attrOffset+" ; Carga el valor del atributo "+id.getLexeme()+"\n");
        }
        if(chained != null) {
            chained.generateCode();
        }
    }
    public void generateCode(Token assignmentOp) throws Exception{
        if(chained == null) {
            if(reference instanceof Parameter) {
                Parameter parameter = (Parameter) reference;
                int paramOffset = parameter.getOffset();
                generateParamAssignmentCode(paramOffset, assignmentOp);
            } else if (reference instanceof LocalVarNode) {
                LocalVarNode localVar = (LocalVarNode) reference;
                int localVarOffset = localVar.getOffset();
                generateVarAssignmentCode(localVarOffset, assignmentOp);
            } else if (reference instanceof Attribute) {
                Attribute attribute = (Attribute) reference;
                int attrOffset = attribute.getOffset();
                generateAttributeAssignmentCode(attrOffset, assignmentOp);
            }
        } else {
            generateCode();
        }
    }
    private void generateParamAssignmentCode(int offset, Token operator) throws Exception{
        if(operator.getLexeme().equals("=")) {
            writer.write(CodeGenerator.DUP + " ; Duplica el valor de la asignacion " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.STORE + " " + offset + " ; Almacena el valor del parametro " + id.getLexeme() + "\n");
        } else if(operator.getLexeme().equals("+=")) {
            writer.write(CodeGenerator.LOAD + " " + offset + " ; Carga el valor del parametro " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.ADD + " ; Suma el valor del parametro " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.DUP + " ; Duplica el valor de la asignacion " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.STORE + " " + offset + " ; Almacena el valor del parametro " + id.getLexeme() + "\n");
        } else if(operator.getLexeme().equals("-=")) {
            writer.write(CodeGenerator.LOAD + " " + offset + " ; Carga el valor del parametro " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.SWAP + " ; Invierto los operandos " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.SUB + " ; Resta el valor del parametro " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.DUP + " ; Duplica el valor de la asignacion " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.STORE + " " + offset + " ; Almacena el valor del parametro " + id.getLexeme() + "\n");
        }
    }
    private void generateVarAssignmentCode(int offset, Token operator) throws Exception {
        if(operator.getLexeme().equals("=")) {
            writer.write(CodeGenerator.DUP + " ; Duplica el valor de la asignacion " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.STORE + " " + offset + " ; Almacena el valor de la variable " + id.getLexeme() + "\n");
        } else if(operator.getLexeme().equals("+=")) {
            writer.write(CodeGenerator.LOAD + " " + offset + " ; Carga el valor de la variable " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.ADD + " ; Suma el valor de la variable " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.DUP + " ; Duplica el valor de la asignacion " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.STORE + " " + offset + " ; Almacena el valor de la variable " + id.getLexeme() + "\n");
        } else if(operator.getLexeme().equals("-=")) {
            writer.write(CodeGenerator.LOAD + " " + offset + " ; Carga el valor de la variable " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.SWAP + " ; Invierto los operandos " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.SUB + " ; Resta el valor de la variable " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.DUP + " ; Duplica el valor de la asignacion " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.STORE + " " + offset + " ; Almacena el valor de la variable " + id.getLexeme() + "\n");
        }
    }
    private void generateAttributeAssignmentCode(int offset, Token operator) throws Exception {
        if(operator.getLexeme().equals("=")) {
            writer.write(CodeGenerator.LOAD + " 3 ; Carga la referencia al CIR \n");
            writer.write(CodeGenerator.SWAP + " ; Bajo la referencia del CIR\n");
            writer.write(CodeGenerator.DUP + " ; Duplica el valor de la asignacion " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.STOREREF + " " + offset + " ; Almacena el valor del atributo " + id.getLexeme() + "\n");
        } else if(operator.getLexeme().equals("+=")) {
            writer.write(CodeGenerator.LOAD + " 3 ; Carga la referencia al CIR \n");
            writer.write(CodeGenerator.LOADREF + " " + offset + " ; Carga el valor del atributo " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.ADD + " ; Suma el valor del atributo " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.DUP + " ; Duplica el valor de la asignacion " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.STOREREF + " " + offset + " ; Almacena el valor del atributo " + id.getLexeme() + "\n");
        } else if(operator.getLexeme().equals("-=")) {
            writer.write(CodeGenerator.LOAD + " 3 ; Carga la referencia al CIR \n");
            writer.write(CodeGenerator.LOADREF + " " + offset + " ; Carga el valor del atributo " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.SWAP + " ; Invierto los operandos " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.SUB + " ; Resta el valor del atributo " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.DUP + " ; Duplica el valor de la asignacion " + id.getLexeme() + "\n");
            writer.write(CodeGenerator.STOREREF + " " + offset + " ; Almacena el valor del atributo " + id.getLexeme() + "\n");
        }
    }
    public String toString() {
        return id.getLexeme() + " " + (chained == null ? "" : chained.toString());
    }

}
