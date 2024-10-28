package semantic.expression_entities;

import entities.Token;
import exceptions.CannotResolveMethodException;
import exceptions.ClassNotDeclaredException;
import semantic.declared_entities.Class;
import semantic.declared_entities.Parameter;
import semantic.declared_entities.ReferenceType;
import semantic.declared_entities.Type;

import java.util.ArrayList;

import static main.MainModule.symbolTable;

public class ConstructorAccessNode extends PrimaryNode{
    Token id;
    ArrayList<ExpressionNode> arguments;

    public ConstructorAccessNode(Token id, ArrayList<ExpressionNode> arguments) {
        this.id = id;
        this.arguments = arguments;
    }
    public ConstructorAccessNode() {

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
        Class classRef = symbolTable.getClass(id.getLexeme());
        if(classRef == null) {
            throw new ClassNotDeclaredException(id.getLineNumber(), id.getLexeme());
        } else {
            checkArguments();
            return new ReferenceType(classRef.getName());
        }
    }
    public void checkArguments() throws Exception{
        String constructorName = id.getLexeme();
        Class classRef = symbolTable.getClass(constructorName);
        ArrayList<Parameter> methodArgs = classRef.getMethod(constructorName).getParameterList();
        if(arguments.size() != methodArgs.size()) {
            throw new CannotResolveMethodException(id);
        }
        for(int i = 0; i < arguments.size(); i++) {
            if(!arguments.get(i).typeCheck().conformsTo(methodArgs.get(i).getType())) {
                throw new CannotResolveMethodException(id);
            }
        }
    }
    public boolean canBeCalled() {
        return true;
    }
    public String toString() {
        return id.getLexeme() + (arguments == null ? " " : arguments.toString());
    }
}
