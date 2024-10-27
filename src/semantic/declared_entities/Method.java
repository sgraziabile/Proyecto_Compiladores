package semantic.declared_entities;

import entities.Token;
import exceptions.CantResolveSymbolException;
import exceptions.InvalidConstructorNameException;
import semantic.sentence_entities.Block;

import java.util.ArrayList;
import java.util.Hashtable;

import static main.MainModule.symbolTable;

public class Method extends ClassMember {
    private Hashtable<String, Parameter> parameterHash;
    private ArrayList<Parameter> parameterList;
    private boolean isConsolidated = false;
    private Block mainBlock;

    public Method(Token name, Type returnType, String modifier, String visibility) {
        super(name, returnType, modifier,visibility);
        this.parameterHash = new Hashtable<>();
        this.parameterList = new ArrayList<>();
    }
    public Method() {
        this.parameterHash = new Hashtable<>();
        this.parameterList = new ArrayList<>();
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
    private void checkIfConstructor(Class myClass) throws Exception {
        if(type.getName().equals("constructor"))
            if (id.getLexeme().equals(myClass.getName())) {
                myClass.setConstructorDeclared();
            }
            else throw new InvalidConstructorNameException(id.getLineNumber(), id.getLexeme());
    }
}
