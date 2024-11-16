package semantic.expression_entities;

import code_generator.CodeGenerator;
import entities.Token;
import exceptions.CantResolveSymbolException;
import exceptions.PrimitiveTypeCallException;
import semantic.declared_entities.*;
import semantic.declared_entities.Class;

import static main.MainModule.symbolTable;
import static main.MainModule.writer;

public class ChainedVarNode extends Chained{
    protected Type type;
    protected Attribute reference;

    public ChainedVarNode(Token name) {
        this.id = name;
    }
    public ChainedVarNode(Token name, Chained next) {
        this.id = name;
        this.chained = next;
    }
    public ChainedVarNode() {

    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Type typeCheck(Type type) throws Exception {
        Type chainedType;
        findReference(type);
        chainedType = reference.getType();
        if(chained != null) {
            if (chainedType instanceof ReferenceType) {
                chainedType = chained.typeCheck(reference.getType());
            } else {
                throw new PrimitiveTypeCallException(chained.getId(), id, chainedType);
            }
        }
        return chainedType;
    }
    private void findReference(Type type) throws Exception {
        Class classRef;
        classRef = symbolTable.getClass(type.getName());
        Attribute attribute = classRef.getAttribute(id.getLexeme());
        if(attribute != null) {
            if(attribute.getVisibility().equals("public")) {
                reference = attribute;
            } else {
                throw new CantResolveSymbolException(id.getLineNumber(), id.getLexeme());
            }
        } else {
            throw new CantResolveSymbolException(id.getLineNumber(), id.getLexeme());
        }
    }
    public boolean isAssignable() {
        if(chained == null) {
            return true;
        } else {
            return chained.isAssignable();
        }
    }
    public boolean canBeCalled() {
        if(chained == null) {
            return false;
        } else {
            return chained.canBeCalled();
        }
    }
    public void generateCode(Token assignmentOp) throws Exception {

    }
    public void generateCode() throws Exception {
        if(reference.getModifier().equals("static")) {
            String label = reference.getLabel();
            generateStaticAttributeAccessCode(label);
        } else {
            int offset = reference.getOffset();
            generateDynamicAttributeAccessCode(offset);
        }
    }
    private void generateDynamicAttributeAccessCode(int offset) throws Exception {
        writer.write(CodeGenerator.DUP + " ; Duplica la referencia al CIR\n");
        writer.write(CodeGenerator.LOADREF + " "+offset+" ; Carga el valor del atributo "+id.getLexeme()+"\n");
    }
    private void generateStaticAttributeAccessCode(String label) throws Exception {
        writer.write(CodeGenerator.PUSH + " "+label+" ; Carga la direccion del atributo "+id.getLexeme()+"\n");
        writer.write(CodeGenerator.LOADREF + " 0 ; Carga el valor del atributo "+id.getLexeme()+"\n");
    }
}
