package semantic.expression_entities;

import code_generator.CodeGenerator;
import entities.Token;
import exceptions.CannotResolveMethodException;
import exceptions.PrimitiveTypeCallException;
import exceptions.StaticReferenceException;
import semantic.declared_entities.*;
import semantic.declared_entities.Class;

import java.util.ArrayList;

import static main.MainModule.symbolTable;
import static main.MainModule.writer;

public class ChainedMethodCallNode extends Chained {
    protected ArrayList<ExpressionNode> args;
    protected Type type;
    protected Method reference;

    public ChainedMethodCallNode(Token name) {
        this.id = name;
        this.args = new ArrayList<>();
    }
    public ChainedMethodCallNode(Token name, Chained next) {
        this.id = name;
        this.chained = next;
        this.args = new ArrayList<>();
    }
    public ChainedMethodCallNode() {
        this.args = new ArrayList<>();
    }
    public ArrayList<ExpressionNode> getArgs() {
        return args;
    }
    public void setArguments(ArrayList<ExpressionNode> args) {
        this.args = args;
    }
    public void addArgument(ExpressionNode arg) {
        this.args.add(arg);
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
            if(chainedType instanceof ReferenceType) {
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
        Method method = classRef.getMethod(id.getLexeme());
        if(method != null) {
            if(method.getVisibility().equals("public")) {
                checkParameters(method);
                reference = method;
            } else {
                throw new CannotResolveMethodException(id);
            }
        } else {
            throw new CannotResolveMethodException(id);
        }
    }
    private void checkParameters(Method method) throws Exception {
        if(args.size() != method.getParameterList().size()) {
            throw new CannotResolveMethodException(id);
        }
        for(int i = 0; i < args.size(); i++) {
            Type actualType = args.get(i).typeCheck();
            Type expectedType = method.getParameterList().get(i).getType();
            if(!actualType.conformsTo(expectedType)) {
                throw new CannotResolveMethodException(id);
            }
        }
    }
    public boolean isAssignable() {
        if(chained == null) {
            return false;
        } else {
            return chained.isAssignable();
        }
    }
    public boolean canBeCalled() {
        if(chained == null) {
            return true;
        } else {
            return chained.canBeCalled();
        }
    }
    public void generateCode() throws Exception {
        if(reference.getModifier().equals("static")) {
            generateStaticMethodCode();
        } else {
            generateDynamicMethodCode();
        }
    }
    private void generateStaticMethodCode() throws Exception{
        for(ExpressionNode e : args) {
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
        for(ExpressionNode e : args) {
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
        for(ExpressionNode e : args) {
            e.generateCode();
            writer.write(CodeGenerator.SWAP+" ; Bajo la referencia del CIR\n");
        }
        writer.write(CodeGenerator.DUP+" ; Duplica la referencia al CIR\n");
        writer.write(CodeGenerator.LOADREF+ " 0 ; Cargo la referencia a la VT en el CIR\n");
        writer.write(CodeGenerator.LOADREF+ " "+reference.getOffset()+" ; Cargo la referencia a la VT\n");
        writer.write(CodeGenerator.CALL+" ; Llama al metodo "+reference.getName()+ "\n");
    }
    public String toString() {
        return id.getLexeme() + args.toString()+  (chained == null ? " " : chained.toString());
    }
}
