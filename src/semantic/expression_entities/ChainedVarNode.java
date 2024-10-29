package semantic.expression_entities;

import entities.Token;
import exceptions.CantResolveSymbolException;
import exceptions.PrimitiveTypeCallException;
import semantic.declared_entities.*;
import semantic.declared_entities.Class;

import static main.MainModule.symbolTable;

public class ChainedVarNode extends Chained{
    protected Type type;
    protected Symbol reference;

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
                throw new PrimitiveTypeCallException(id, chained.getId(), chainedType);
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
        return true;
    }
}
