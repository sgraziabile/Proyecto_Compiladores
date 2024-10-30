package semantic;

import entities.Token;
import exceptions.CyclicInheritanceException;
import exceptions.MainNotDeclaredException;
import semantic.declared_entities.*;
import semantic.declared_entities.Class;
import semantic.sentence_entities.*;

import java.util.ArrayList;
import java.util.Hashtable;

public class SymbolTable {
    boolean isConsolidated = false;
    private Class currentClass;
    private Method currentMethod;
    private Block currentBlock;
    private Hashtable<String,Class> classHash;
    private ArrayList<Class> classList;
    private boolean mainDeclared = false;


    public SymbolTable() {
        this.currentClass = null;
        this.currentMethod = null;
        this.classHash = new Hashtable<>();
        this.classList = new ArrayList<>();
        try {
            initBaseClasses();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } }
    public void setCurrentClass(Class c) {
        currentClass = c;
    }
    public void setCurrentMethod(Method method) {
        currentMethod = method;
    }
    public void setCurrentBlock(Block block) {
        currentBlock = block;
    }
    public void insertClass(Class c) throws Exception {
        if(c.getSuperclass() == null) {
            classHash.put(c.getName(), c);
            classList.add(c);
        }
        else if(!c.getName().equals(c.getSuperclass().getLexeme())) {
            classHash.put(c.getName(), c);
            classList.add(c);
        }
        else
            throw new CyclicInheritanceException(c.getId().getLineNumber(),c.getName());
    }
    public void printClasses() {
        for (Class c : classHash.values()) {
            System.out.println(c.getName() + " extends " + c.getSuperclass().getLexeme());
        }
    }
    public Class getCurrentClass() {
        return currentClass;
    }
    public Method getCurrentMethod() {
        return currentMethod;
    }
    public Block getCurrentBlock() {
        return currentBlock;
    }
    public Class getClass(String id) {
        return classHash.get(id);
    }
    public Hashtable<String,Class> getClasses() {
        return classHash;
    }
    public void checkDeclarations() throws Exception {
        for(Class c : classHash.values()) {
            if (!c.getName().equals("Object") && !c.getName().equals("System") && !c.getName().equals("String")) {
                c.checkDeclaration();
            }
        }
        checkMain();
        consolidate();
    }
    public void consolidate() throws Exception {
        for(Class c : classHash.values()) {
            if(!c.isConsolidated()) {
                c.consolidate();
            }
        }
        isConsolidated = true;
    }
    public void checkSentences() throws Exception {
        for(Class c : classList) {
            if (!(c.getName().equals("Object") || c.getName().equals("System") || c.getName().equals("String"))) {
                setCurrentClass(c);
                for (Method m : c.getMethodList()) {
                    setCurrentMethod(m);
                    if (!m.isChecked()) {
                        m.checkSentences();
                        m.setChecked();
                    }
                }
            }
        }
    }
    public boolean isConsolidated() {
        return isConsolidated;
    }
    public boolean isMainDeclared() {
        return mainDeclared;
    }
    public void checkMain() throws MainNotDeclaredException {
        if (!mainDeclared) {
            throw new MainNotDeclaredException();
        }
    }
    public void setMainDeclared() {
        mainDeclared = true;
    }
    private void initBaseClasses() throws Exception{
        Token objectClass = initObjectClass();
        initSystemClass(objectClass);
        initStringClass(objectClass);
    }
    private Token initObjectClass() throws Exception {
        Class objectClass = new Class(new Token("idClase", "Object", 0));
        objectClass.setConsolidated();
        Method debugPrint = new Method(new Token("idMetVar","debugPrint",0),new PrimitiveType("void"),"static","public");
        Parameter param = new Parameter(new Token("idMetVar","i",0),new PrimitiveType("int"));
        debugPrint.addParameter("i",param);
        objectClass.checkConstructor();
        objectClass.addMethod(debugPrint);
        insertClass(objectClass);
        return objectClass.getId();
    }
    private void initSystemClass(Token objectClass) throws Exception {
        Class systemClass = new Class(new Token("idClase", "System", 0));
        systemClass.checkConstructor();
        addSystemMethods(systemClass);
        systemClass.setSuperclass(objectClass);
        insertClass(systemClass);
    }
    private void initStringClass(Token objectClass) throws Exception{
        Class stringClass = new Class(new Token("idClase", "String", 0));
        stringClass.setSuperclass(objectClass);
        stringClass.checkConstructor();
        insertClass(stringClass);
    }
    private void addSystemMethods(Class systemClass) {
        String modifier = "static";
        String visibility = "public";
        // 1. static int read()
        Method readMethod = new Method(new Token("idMetVar", "read", 0), new PrimitiveType("int"), modifier, visibility);
        systemClass.addMethod(readMethod);
        // 2. static void printB(boolean b)
        Method printBMethod = new Method(new Token("idMetVar", "printB", 0), new PrimitiveType("void"), modifier, visibility);
        Parameter param1 = new Parameter(new Token("idMetVar","b",0),new PrimitiveType("boolean"));
        printBMethod.addParameter("b",param1);
        systemClass.addMethod(printBMethod);
        // 3. static void printC(char c)
        Method printCMethod = new Method(new Token("idMetVar", "printC", 0), new PrimitiveType("void"), modifier, visibility);
        param1 = new Parameter(new Token("idMetVar","c",0),new PrimitiveType("char"));
        printCMethod.addParameter("c",param1);
        systemClass.addMethod(printCMethod);
        // 4. static void printI(int i)
        Method printIMethod = new Method(new Token("idMetVar", "printI", 0), new PrimitiveType("void"), modifier, visibility);
        param1 = new Parameter(new Token("idMetVar","i",0),new PrimitiveType("int"));
        printIMethod.addParameter("i",param1);
        systemClass.addMethod(printIMethod);
        // 5. static void printS(String s)
        Method printSMethod = new Method(new Token("idMetVar", "printS", 0), new PrimitiveType("void"), modifier, visibility);
        param1 = new Parameter(new Token("idMetVar","s",0),new ReferenceType("String"));
        printSMethod.addParameter("s",param1);
        systemClass.addMethod(printSMethod);
        // 6. static void println()
        Method printlnMethod = new Method(new Token("idMetVar", "println", 0), new PrimitiveType("void"), modifier, visibility);
        systemClass.addMethod(printlnMethod);
        // 7. static void printBln(boolean b)
        Method printBlnMethod = new Method(new Token("idMetVar", "printBln", 0), new PrimitiveType("void"), modifier, visibility);
        systemClass.addMethod(printBlnMethod);
        param1 = new Parameter(new Token("idMetVar","b",0),new PrimitiveType("boolean"));
        printBlnMethod.addParameter("b",param1);
        // 8. static void printCln(char c)
        Method printClnMethod = new Method(new Token("idMetVar", "printCln", 0), new PrimitiveType("void"), modifier, visibility);
        systemClass.addMethod(printClnMethod);
        param1 = new Parameter(new Token("idMetVar","c",0),new PrimitiveType("char"));
        printClnMethod.addParameter("c",param1);
        // 9. static void printIln(int i)
        Method printIlnMethod = new Method(new Token("idMetVar", "printIln", 0), new PrimitiveType("void"), modifier, visibility);
        param1 = new Parameter(new Token("idMetVar","i",0),new PrimitiveType("int"));
        printIlnMethod.addParameter("i",param1);
        systemClass.addMethod(printIlnMethod);
        // 10. static void printSln(String s)
        Method printSlnMethod = new Method(new Token("idMetVar", "printSln", 0), new PrimitiveType("void"), modifier, visibility);
        param1 = new Parameter(new Token("idMetVar","s",0),new ReferenceType("String"));
        printSlnMethod.addParameter("s",param1);
        systemClass.addMethod(printSlnMethod);
    }
    public void printBlocks() {
        for(Class c : classHash.values()) {
            for(Method m : c.getMethods().values()) {
                Block mainBlock = m.getMainBlock();
                if(mainBlock != null) {
                    System.out.println(m.getName()+" "+mainBlock.getSentenceList().size());
                    for(SentenceNode s: mainBlock.getSentenceList()) {
                        System.out.println(s.toString());
                        if(s instanceof Block) {
                            Block b = (Block) s;
                            System.out.println(" "+b.getSentenceList().size());
                            printBlock(b);
                        }
                        else if(s instanceof WhileNode) {
                            WhileNode w = (WhileNode) s;
                            printBlock((Block) w.getBody());
                        }
                        else if(s instanceof IfNode) {
                            IfNode i = (IfNode) s;
                            if(i.getCondition() != null)
                                System.out.println("    "+i.getCondition().toString());
                            if(i.getThenBody() != null)
                                printBlock((Block) i.getThenBody());
                            if(s instanceof IfWithElseNode) {
                                IfWithElseNode i2 = (IfWithElseNode) s;
                                if(i2.getElseBody() != null)
                                    printBlock((Block) i2.getElseBody());
                            }
                        }
                        else if(s instanceof SwitchNode) {
                            SwitchNode sw = (SwitchNode) s;
                            for(CaseNode cn : sw.getCases()) {
                                System.out.println("    "+cn.toString());
                            }
                        }
                        else if(s instanceof LocalVarNode) {
                            LocalVarNode l = (LocalVarNode) s;
                            System.out.println(" " +l.toString());
                        }
                        else if(s instanceof AssignmentNode) {
                            AssignmentNode a = (AssignmentNode) s;
                            System.out.print("      "+a.getAssignmentExp().getLeftExp().toString());
                            System.out.print(" "+ a.getAssignmentExp().getOperator().getLexeme());
                            System.out.print(" "+ a.getAssignmentExp().getRightExp().toString());
                            System.out.println();
                        }
                    }
                }
            }
        }
    }
    private void printBlock(Block b) {
        for(SentenceNode s: b.getSentenceList()) {
            System.out.println("    "+s.toString());
            if(s instanceof Block) {
                Block b2 = (Block) s;
                System.out.println("    "+b2.getSentenceList().size());
                printBlock(b2);
            }
            else if (s instanceof WhileNode) {
                WhileNode w = (WhileNode) s;
                printBlock((Block) w.getBody());
            }
            else if(s instanceof IfNode) {
                IfNode i = (IfNode) s;
                if(i.getCondition() != null)
                    System.out.println("    "+i.getCondition().toString());
                printBlock((Block) i.getThenBody());
                if(s instanceof IfWithElseNode) {
                    IfWithElseNode i2 = (IfWithElseNode) s;
                    printBlock((Block) i2.getElseBody());
                }
            }
            else if(s instanceof SwitchNode) {
                SwitchNode sw = (SwitchNode) s;
                for(CaseNode c : sw.getCases()) {
                    c.toString();
                }
            }
            else if(s instanceof LocalVarNode) {
                LocalVarNode l = (LocalVarNode) s;
                System.out.println("        "+l.toString());
            }
            else if(s instanceof AssignmentNode) {
                AssignmentNode a = (AssignmentNode) s;
                System.out.print("      "+a.getAssignmentExp().getLeftExp().toString());
                System.out.print("  "+ a.getAssignmentExp().getOperator().getLexeme());
                System.out.print("  "+ a.getAssignmentExp().getRightExp().toString());
                System.out.println();
            }
        }
    }


}
