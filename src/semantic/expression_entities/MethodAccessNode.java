package semantic.expression_entities;

import entities.Token;
import exceptions.CannotResolveMethodException;
import exceptions.StaticReferenceException;
import semantic.declared_entities.Parameter;
import semantic.declared_entities.Type;

import java.util.ArrayList;

import static main.MainModule.symbolTable;

public class MethodAccessNode extends PrimaryNode {
    protected Token id;
    protected ArrayList<ExpressionNode> arguments;

    public MethodAccessNode() {

    }
    public Token getId() {
        return id;
    }
    public void setId(Token id) {
        this.id = id;
    }
    public ArrayList<ExpressionNode> getArguments() {
        return arguments;
    }
    public void setArguments(ArrayList<ExpressionNode> arguments) {
        this.arguments = arguments;
    }
    public Type typeCheck() throws Exception{
        Type type;
        String methodName = id.getLexeme();
        if(symbolTable.getCurrentClass().getMethod(methodName) == null) {
            throw new CannotResolveMethodException(id);
        }
        checkStatic();
        chechArguments();
        if(chained != null) {
            type = chained.typeCheck(this);
            return type;
        }
        return symbolTable.getCurrentClass().getMethod(methodName).getType();
    }
    public boolean isAssignable() {
        if(chained == null) {
            return false;
        } else {
            return chained.isAssignable();
        }
    }
    private void chechArguments() throws Exception {
        String methodName = id.getLexeme();
        ArrayList<Parameter> methodArgs = symbolTable.getCurrentClass().getMethod(methodName).getParameterList();
        if(arguments.size() != methodArgs.size()) {
            throw new CannotResolveMethodException(id);
        }
        for(int i = 0; i < arguments.size(); i++) {
            if(!arguments.get(i).typeCheck().conformsTo(methodArgs.get(i).getType())) {
                throw new CannotResolveMethodException(id);
            }
        }
    }
    private void checkStatic() throws Exception {
        if(symbolTable.getCurrentMethod().getModifier().equals("static") && symbolTable.getCurrentClass().getMethod(id.getLexeme()).getModifier().equals("dynamic")) {
            throw new StaticReferenceException(0, id.getLexeme());
        }
    }
    public String toString() {
        return id.getLexeme() + arguments.toString()+  (chained == null ? " " : chained.toString());
    }
}
