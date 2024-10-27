package semantic.expression_entities;

import entities.Token;
import exceptions.ClassNotDeclaredException;
import semantic.declared_entities.Class;
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
            return new ReferenceType("constructor");
        }
    }
    public String toString() {
        return id.getLexeme() + (arguments == null ? " " : arguments.toString());
    }
}
