package semantic.expression_entities;

import code_generator.CodeGenerator;
import entities.Token;
import exceptions.CannotResolveMethodException;
import exceptions.PrimitiveTypeCallException;
import exceptions.StaticReferenceException;
import semantic.declared_entities.*;

import java.util.ArrayList;

import static main.MainModule.*;

public class MethodAccessNode extends PrimaryNode {
    protected Token id;
    protected ArrayList<ExpressionNode> arguments;
    protected Method reference;

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
        checkArguments();
        reference = symbolTable.getCurrentClass().getMethod(methodName);
        if(chained != null) {
            if(reference.getType() instanceof ReferenceType) {
                type = chained.typeCheck(reference.getType());
            } else {
                throw new PrimitiveTypeCallException(id, chained.getId(), reference.getType());
            }
            return type;
        }
        return reference.getType();
    }
    public boolean isAssignable() {
        if(chained == null) {
            return false;
        } else {
            return chained.isAssignable();
        }
    }
    private void checkArguments() throws Exception {
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
    public boolean canBeCalled() {
        if(chained == null) {
            return true;
        } else {
            return chained.canBeCalled();
        }
    }
    public String toString() {
        return id.getLexeme() + arguments.toString()+  (chained == null ? " " : chained.toString());
    }
    public void generateCode() throws Exception {
        if(reference.getModifier().equals("static")) {
            generateStaticMethodCode();
        } else {
            generateDynamicMethodCode();
        }
    }
    private void generateStaticMethodCode() throws Exception{
        for(ExpressionNode e : arguments) {
            e.generateCode();
        }
        writer.write(CodeGenerator.PUSH+" "+reference.getLabel()+" ; Apila el metodo\n");
        writer.write(CodeGenerator.CALL+" ; Llama al metodo\n");
    }
    private void generateDynamicMethodCode() throws Exception{
        if(reference.getType().getName().equals("void")) {
            generateVoidMethodCode();
        } else {
            generateNonVoidMethodCode();
        }
    }
    private void generateVoidMethodCode() throws Exception {
        writer.write(CodeGenerator.LOAD+ " 3 ; Carga el CIR de la clase actual\n");
        for(ExpressionNode e : arguments) {
            e.generateCode();
            writer.write(CodeGenerator.SWAP+" ; Bajo la referencia del CIR\n");
        }
        writer.write(CodeGenerator.DUP+" ; Duplica la referencia al CIR\n");
        writer.write(CodeGenerator.LOADREF+ " 0 ; Cargo la referencia a la VT en el CIR\n");
        writer.write(CodeGenerator.LOADREF+ " "+reference.getOffset()+" ; Cargo la referencia a la VT\n");
        writer.write(CodeGenerator.CALL+" ; Llama al metodo "+reference.getName()+ "\n");
    }
    private void generateNonVoidMethodCode() throws Exception {
        writer.write(CodeGenerator.LOAD+ " 3 ; Carga el CIR de la clase actual\n");
        writer.write(CodeGenerator.RMEM1+ " ; Reserva espacio para el valor de retorno\n");
        writer.write(CodeGenerator.SWAP+" ; Bajo la referencia del CIR\n");
        for(ExpressionNode e : arguments) {
            e.generateCode();
            writer.write(CodeGenerator.SWAP+" ; Bajo la referencia del CIR\n");
        }
        writer.write(CodeGenerator.DUP+" ; Duplica la referencia al CIR\n");
        writer.write(CodeGenerator.LOADREF+ " 0 ; Cargo la referencia a la VT en el CIR\n");
        writer.write(CodeGenerator.LOADREF+ " "+reference.getOffset()+" ; Cargo la referencia a la VT\n");
        writer.write(CodeGenerator.CALL+" ; Llama al metodo "+reference.getName()+ "\n");
    }
}
