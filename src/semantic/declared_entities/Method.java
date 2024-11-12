package semantic.declared_entities;

import code_generator.CodeGenerator;
import entities.Token;
import exceptions.CantResolveSymbolException;
import exceptions.InvalidConstructorNameException;
import semantic.sentence_entities.Block;

import java.util.ArrayList;
import java.util.Hashtable;

import static main.MainModule.*;

public class Method extends ClassMember {
    private Hashtable<String, Parameter> parameterHash;
    private ArrayList<Parameter> parameterList;
    private boolean isConsolidated = false;
    private boolean isChecked = false;
    private Block mainBlock;
    private String label;
    private int offset;
    private Class myClass;

    public Method(Token name, Type returnType, String modifier, String visibility) {
        super(name, returnType, modifier,visibility);
        this.parameterHash = new Hashtable<>();
        this.parameterList = new ArrayList<>();
        mainBlock = new Block();
    }
    public Method() {
        this.parameterHash = new Hashtable<>();
        this.parameterList = new ArrayList<>();
        mainBlock = new Block();
    }
    public void setMyClass(Class myClass) {
        this.myClass = myClass;
    }
    public Class getMyClass() {
        return myClass;
    }
    public void setLabel() {
        if(this.getType().getName().equals("constructor")) {
            label = "lblConstructor@"+myClass.getName();
        } else {
            if(label == null)
                label = "lblMet"+id.getLexeme()+"@"+myClass.getName();
        }
    }
    public String getLabel() {
        return label;
    }
    public void setOffset(int offset) {
        this.offset = offset;
        setParamsOffset();
    }
    public int getOffset() {
        return offset;
    }
    private void setParamsOffset() {
        if(modifier.equals("static")) {
            setStaticMethodParamsOffset();
        } else {
            setDynamicMethodParamsOffset();
        }
    }
    private void setDynamicMethodParamsOffset() {
        int i = 0;
        int offset;
        for (Parameter p : parameterList) {
            offset = parameterList.size() - i + 3;
            p.setOffset(offset);
            i++;
        }
    }
    private void setStaticMethodParamsOffset() {
        int i = 0;
        int offset;
        for (Parameter p : parameterList) {
            offset = parameterList.size() - i + 2;
            p.setOffset(offset);
            i++;
        }
    }
    public void setMainBlock(Block mainBlock) {
        this.mainBlock = mainBlock;
    }
    public Block getMainBlock() {
        return mainBlock;
    }
    public void addParameter(String name, Parameter parameter) {
        parameterHash.put(name,parameter);
        parameterList.add(parameter);
    }
    public void addParameters(ArrayList<Parameter> paramsList) {
        for (Parameter p : paramsList) {
            parameterHash.put(p.getId().getLexeme(),p);
            parameterList.add(p);
        }
    }
    public Type getReturnType() {
        return type;
    }
    public boolean isConsolidated() {
        return isConsolidated;
    }
    public ArrayList<Parameter> getParameterList() {
        return parameterList;
    }
    public Hashtable<String, Parameter> getParameterHash() {
        return parameterHash;
    }
    public Parameter getParameter(String id) {
        return parameterHash.get(id);
    }
    public void print() {
        System.out.println("Method: " + id.getLexeme() + " " + type.getName() + " " + modifier + " " + visibility);
        System.out.println("Declared in: "+myClass.getName()+ " Offset: " + offset+" Label: "+label);
    }
    public void checkDeclaration(Class myClass) throws Exception {
        if(!symbolTable.isMainDeclared())
            checkMain();
        if(!myClass.isConstructorDeclared())
            checkIfConstructor(myClass);
        if(!type.getName().equals("void") && !type.getName().equals("int") && !type.getName().equals("boolean") && !type.getName().equals("float") && !type.getName().equals("char") && !type.getName().equals("constructor")){
                if(symbolTable.getClass(type.getName()) == null) {
                    throw new CantResolveSymbolException(id.getLineNumber(), type.getName());
                }
        }
        for (Parameter p : parameterList) {
            p.checkDeclaration();
        }
    }
    public void checkSentences() throws Exception {
        if(mainBlock != null) {
            symbolTable.setCurrentBlock(mainBlock);
            mainBlock.checkSentence();
            mainBlock.setLocalVarOffsets();
        }
    }
    public void consolidate() {
        isConsolidated = true;
    }
    public void checkMain() throws Exception {
        if (id.getLexeme().equals("main")  && modifier.equals("static") && visibility.equals("public") && type.getName().equals("void") && parameterList.isEmpty()) {
            symbolTable.setMainDeclared();
        }
    }
    public void generateCode() throws Exception {
        if(type.getName().equals("constructor")) {
            codeGenerator.generateConstructorCode(label);
            writer.write(CodeGenerator.STOREFP+" ; Almacena el tope de la pila en el registro \n");
            if(mainBlock != null) {
                mainBlock.generateCode();
                writer.write(CodeGenerator.STOREFP+" ; Almacena el tope de la pila en el registro \n");
                writer.write(CodeGenerator.RET+ " "+parameterList.size()+" ; Libera el espacio de los parametros de "+getName() +"\n");
            }
        } else {
            codeGenerator.generateMethodCode(label);
            if(mainBlock != null) {
                mainBlock.generateCode();
                writer.write(CodeGenerator.STOREFP+" ; Almacena el tope de la pila en el registro \n");
                writer.write(CodeGenerator.RET+ " "+parameterList.size()+" ; Libera el espacio de los parametros de "+getName() +"\n");
            }
        }

    }

    private void checkIfConstructor(Class myClass) throws Exception {
        if(type.getName().equals("constructor"))
            if (id.getLexeme().equals(myClass.getName())) {
                myClass.setConstructorDeclared();
            }
            else throw new InvalidConstructorNameException(id.getLineNumber(), id.getLexeme());
    }
    public void setChecked() {
        isChecked = true;
    }
    public boolean isChecked() {
        return isChecked;
    }
}
