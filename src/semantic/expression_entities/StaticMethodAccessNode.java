package semantic.expression_entities;

import entities.Token;
import exceptions.CannotResolveMethodException;
import exceptions.StaticReferenceException;
import semantic.declared_entities.Parameter;
import semantic.declared_entities.Symbol;
import semantic.declared_entities.Type;

import java.util.ArrayList;

import static main.MainModule.symbolTable;

public class StaticMethodAccessNode extends PrimaryNode {
    protected Token methodId;
    protected Token className;
    protected ArrayList<ExpressionNode> arguments;
    protected Symbol reference;

    public StaticMethodAccessNode() {

    }
    public StaticMethodAccessNode(Token methodId, Token className, ArrayList<ExpressionNode> arguments) {
        this.methodId = methodId;
        this.className = className;
        this.arguments = arguments;
    }
    public Token getMethodId() {
        return methodId;
    }
    public void setMethodId(Token methodId) {
        this.methodId = methodId;
    }
    public Token getClassName() {
        return className;
    }
    public void setClassName(Token className) {
        this.className = className;
    }
    public ArrayList<ExpressionNode> getArguments() {
        return arguments;
    }
    public void setArguments(ArrayList<ExpressionNode> arguments) {
        this.arguments = arguments;
    }
    public Symbol getReference() {
        return reference;
    }
    public Type typeCheck() throws Exception {
        Type type;
        if(symbolTable.getClass(className.getLexeme()) == null) {
            throw new CannotResolveMethodException(methodId);
        }
        checkStatic();
        chechArguments();
        reference = symbolTable.getClass(className.getLexeme()).getMethod(methodId.getLexeme());
        if(chained != null) {
            type = chained.typeCheck(this);
        } else {
            type = reference.getType();
        }
        return type;
    }
    public boolean canBeCalled() {
        return true;
    }
    public boolean isAssignable() {
        if(chained == null) {
            return false;
        } else {
            return chained.isAssignable();
        }
    }
    private void chechArguments() throws Exception {
        String methodName = methodId.getLexeme();
        ArrayList<Parameter> methodArgs = symbolTable.getClass(className.getLexeme()).getMethod(methodName).getParameterList();
        if(arguments.size() != methodArgs.size()) {
            throw new CannotResolveMethodException(methodId);
        }
        for(int i = 0; i < arguments.size(); i++) {
            if(!arguments.get(i).typeCheck().conformsTo(methodArgs.get(i).getType())) {
                throw new CannotResolveMethodException(methodId);
            }
        }
    }
    private void checkStatic() throws Exception {
        String className = this.className.getLexeme();
        if(symbolTable.getClass(className).getMethod(methodId.getLexeme()).getModifier().equals("dynamic")) {
            throw new StaticReferenceException(0, methodId.getLexeme());
        }
    }
    public String toString() {
        return className.getLexeme() + "." + methodId.getLexeme();
    }
}
