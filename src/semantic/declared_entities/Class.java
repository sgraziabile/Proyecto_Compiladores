package semantic.declared_entities;

import entities.Token;
import exceptions.ClassNotDeclaredException;
import exceptions.CyclicInheritanceException;
import exceptions.InvalidRedefinitionException;

import java.util.ArrayList;
import java.util.Hashtable;

import static main.MainModule.symbolTable;
import static main.MainModule.writer;


public class Class implements Symbol{
    private Token id;
    private Token superclass;
    private boolean isConsolidated = false;
    private Hashtable<String, Attribute> attributes;
    private ArrayList<Attribute> attributeList;
    private Hashtable<String, Method> methods;
    private ArrayList<Method> methodList;
    private ArrayList<Method> VTable;
    private boolean constructorDeclared = false;
    private Type type;
    private boolean hasMain = false;
    private String vtLabel;

    public Class(Token idClass) {
        id = idClass;
        superclass = null;
        attributes = new Hashtable<>();
        methods = new Hashtable<>();
        attributeList = new ArrayList<>();
        methodList = new ArrayList<>();
        VTable = new ArrayList<>();
        type = new ReferenceType(id.getLexeme());
        vtLabel = "lblVT"+id.getLexeme();
    }
    public void setSuperclass(Token superclass) {
        this.superclass = superclass;
    }
    public void addAttribute(Attribute attribute) throws Exception {
        attributes.put(attribute.getId().getLexeme(), attribute);
        attributeList.add(attribute);
    }
    private void addInheritedAttribute(Attribute attribute) {
        attributes.put(attribute.getId().getLexeme(), attribute);
        attributeList.addFirst(attribute);
    }
    public void addMethod(Method method) {
        methods.put(method.getId().getLexeme(), method);
        methodList.add(method);
        method.setMyClass(this);
        if(method.getId().getLexeme().equals("main")) {
            hasMain = true;
        }
    }
    private void addInheritedMethod(Method method) {
        methods.put(method.getId().getLexeme(), method);
        methodList.addFirst(method);
    }
    private void removeMethod(Method method) {
        methods.remove(method.getId().getLexeme());
        methodList.remove(method);
    }
    public Token getId() {
        return id;
    }
    public String getName() {
        return id.getLexeme();
    }
    public Type getType() {
        return type;
    }
    public Token getSuperclass() {
        return superclass;
    }
    public boolean isConsolidated() {
        return isConsolidated;
    }
    public Attribute getAttribute(String id) {
        return attributes.get(id);
    }
    public ArrayList<Attribute> getAttributeList() {
        return attributeList;
    }
    public Hashtable<String,Attribute> getAttributes() {
        return attributes;
    }
    public Method getMethod(String id) {
        return methods.get(id);
    }
    public Hashtable<String,Method> getMethods() {
        return methods;
    }
    public ArrayList<Method> getMethodList() {
        return methodList;
    }
    public void consolidate() throws Exception {
        if(superclass != null) {
            Class superclass = symbolTable.getClass(this.superclass.getLexeme());
            ArrayList<Method> methodAuxList = new ArrayList<>();
            ArrayList<Attribute> attributeAuxList = new ArrayList<>();
            ArrayList<Parameter> parameterAuxList = new ArrayList<>();
            if(!superclass.isConsolidated()) {
                superclass.consolidate();
            }
            for(Attribute a: superclass.getAttributeList()) {
                Attribute currentAttribute = attributes.get(a.getId().getLexeme());
                if(currentAttribute == null) {
                    attributeAuxList.addFirst(a);
                }
                else {
                    if(checkRedefinedAttribute(a)) {
                        throw new InvalidRedefinitionException(currentAttribute.getId().getLexeme(), currentAttribute.getId().getLineNumber());
                    }
                }
            }
            for(Attribute attribute: attributeAuxList) {
                addInheritedAttribute(attribute);
            }
            for(Method m: superclass.getMethodList()) {
                if (!m.getType().getName().equals("constructor")) {
                    Method currentMethod = methods.get(m.getId().getLexeme());
                    if (currentMethod == null) {
                        methodAuxList.addFirst(m);
                    } else {
                        if (checkRedefinedMethod(m)) {
                            removeMethod(currentMethod);
                            methodAuxList.addFirst(currentMethod);
                        } else {
                            throw new InvalidRedefinitionException(currentMethod.getName(), currentMethod.getId().getLineNumber());
                        }
                    }
                }
            }
            for(Method method: methodAuxList) {
                addInheritedMethod(method);
            }
        }
        setConsolidated();
    }
    public void setConsolidated() {
        isConsolidated = true;
        generateVTable();
    }
    public void generateVTable() {
        System.out.println("Generating VTable for class: " + id.getLexeme());
        for(Method m: methodList) {
            System.out.println("Method: " + m.getId().getLexeme());
            m.setLabel();
            if(m.getModifier().equals("dynamic")) {
                VTable.add(m);
            }
        }
        setAttributeOffsets();
        setMethodOffsets();
    }
    public void generateCode() throws Exception {
        writer.write(vtLabel + ": NOP\n");
        writer.write("\n");
        writer.write(".CODE\n");
        for(Method m: methodList) {
            if(m.getMyClass() == this)
                m.generateCode();
                writer.write("\n");
        }
        writer.write("\n");
    }
    private void setAttributeOffsets() {
        int i = 0;
        int offset;
        for(Attribute a: attributeList) {
            offset = attributeList.size() - i;
            a.setOffset(offset);
            i += 1;
        }
    }
    private void setMethodOffsets() {
        int offset = 0;
        for(Method m: VTable) {
            m.setOffset(offset);
            offset += 1;
        }
    }
    public void checkDeclaration() throws Exception {
        checkSuperclass();
        checkCircularInheritance();
        checkAttributes();
        checkMethods();
        checkConstructor();
    }
    private void checkSuperclass() throws Exception {
        if(superclass != null) {
            if(symbolTable.getClass(superclass.getLexeme()) == null) {
                throw new ClassNotDeclaredException(superclass.getLineNumber(), superclass.getLexeme());
            }
        }
    }
    private void checkAttributes() throws Exception {
        for(Attribute a : attributeList) {
            a.checkDeclaration();
        }
    }
    private void checkMethods() throws Exception {
        for(Method m : methodList) {
            m.checkDeclaration(this);
        }
    }
    private void checkCircularInheritance() throws Exception {
        if (superclass != null) {
            Class currentClass = this;
            if (currentClass.getSuperclass() != null) {
                while (currentClass.getSuperclass() != null) {
                    currentClass = symbolTable.getClass(currentClass.getSuperclass().getLexeme());
                    if (currentClass.getName().equals(this.getName())) {
                        throw new CyclicInheritanceException(this.getId().getLineNumber(), this.getName());
                    }
                }
            }
        }
    }
    public boolean isConstructorDeclared() {
        return constructorDeclared;
    }
    public void checkConstructor() {
        if(!isConstructorDeclared()) {
            Method constructor = new Method();
            constructor.setId(new Token("idMetVar",id.getLexeme() ,0));
            constructor.setType(new PrimitiveType("constructor"));
            constructor.setModifier("dymamic");
            constructor.setVisibility("public");
            addMethod(constructor);
        }
    }
    public void setConstructorDeclared() {
        constructorDeclared = true;
    }
    public boolean hasMain() {
        return hasMain;
    }
    private boolean checkRedefinedMethod(Method m) {
        boolean validRedefinition = true;
        Method currentMethod = getMethod(m.getId().getLexeme());
        if(!currentMethod.getType().getName().equals(m.getType().getName()))
            validRedefinition = false;
        else if(!currentMethod.getVisibility().equals(m.getVisibility()))
            validRedefinition = false;
        else if(!currentMethod.getModifier().equals(m.getModifier()))
            validRedefinition = false;
        else if(currentMethod.getParameterList().size() != m.getParameterList().size())
            validRedefinition = false;
        else {
            validRedefinition = checkEqualParameters(currentMethod, m);
        }
        return validRedefinition;
    }
    private boolean checkRedefinedAttribute(Attribute a) {
        boolean isRedefined = false;
        Attribute currentAttribute = getAttribute(a.getId().getLexeme());
        if(currentAttribute.getName().equals(a.getName()))
            isRedefined = true;
        return isRedefined;
    }
    private boolean checkEqualParameters(Method currentMethod, Method inheritedMethod) {
        boolean validParameters = true;
        if (currentMethod.getParameterList().size() != inheritedMethod.getParameterList().size())
            validParameters = false;
        else {
            for (int i = 0; i < currentMethod.getParameterList().size(); i++) {
                if (!currentMethod.getParameterList().get(i).getType().getName().equals(inheritedMethod.getParameterList().get(i).getType().getName())) {
                    validParameters = false;
                    break;
                }
            }
        }
        return validParameters;
    }
}
