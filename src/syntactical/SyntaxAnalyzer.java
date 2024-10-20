package syntactical;

import entities.PrimerosHandler;
import entities.Token;
import exceptions.AlreadyDeclaredException;
import exceptions.InvalidConstructorNameException;
import exceptions.SyntaxException;
import lexical.LexicalAnalyzer;
import semantic.SymbolTable;
import semantic.declared_entities.*;
import semantic.declared_entities.Class;
import semantic.expression_entities.*;
import semantic.sentence_entities.*;

import java.util.ArrayList;
import java.util.List;

public class SyntaxAnalyzer {
    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;
    private PrimerosHandler primerosHandler;
    public SymbolTable symbolTable;
    private static final String public_visibility = "public";
    private static final String private_visibility = "private";
    private static final String static_modifier = "static";
    private static final String dynamic_modifier = "dynamic";


    public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer, PrimerosHandler primerosHandler,SymbolTable symbolTable) throws Exception {
        this.primerosHandler = primerosHandler;
        this.lexicalAnalyzer = lexicalAnalyzer;
        this.symbolTable = symbolTable;
        currentToken = lexicalAnalyzer.nextToken();
        Init();
        symbolTable.checkDeclarations();
    }

    private void match(String tokenName) throws Exception {
        String tokenClass = currentToken.getTokenClass();
        if (tokenName.equals(tokenClass)) {
            currentToken = lexicalAnalyzer.nextToken();
        } else {
            String lexeme = currentToken.getLexeme();
            throw new SyntaxException(List.of(tokenName), tokenClass, Integer.toString(currentToken.getLineNumber()),lexeme);
        }
    }
    public void Init() throws Exception {
        ClassList();
        match("$");
    }
    private void ClassList() throws Exception {
        if (currentToken.getTokenClass().equals("keyword_class")) {
            Class();
            ClassList();
        }
        else if(currentToken.getTokenClass().equals("keyword_abstract")) {
            AbstractClass();
            ClassList();
        }
        else {
            //vacio
        }
    }
    private void Class() throws Exception {
        match("keyword_class");
        Token className = currentToken;
        match("idClase");
        Class c = new Class(className);
        symbolTable.setCurrentClass(c);
        Token superclass = OptionalInheritance();
        c.setSuperclass(superclass);
        match("llaveAbre");
        MemberList();
        match("llaveCierra");
        if(symbolTable.getClass(c.getName()) == null) {
            symbolTable.insertClass(c);
        }
        else {
            throw new AlreadyDeclaredException("class",c.getId().getLineNumber(),c.getName());
        }
    }
    private void AbstractClass() throws Exception {
        match("keyword_abstract");
        match("keyword_class");
        Token className = currentToken;
        match("idClase");
        Class c = new Class(className);
        symbolTable.setCurrentClass(c);
        Token superclass = OptionalInheritance();
        c.setSuperclass(superclass);
        match("llaveAbre");
        AbstractMemberList();
        match("llaveCierra");
        symbolTable.insertClass(c);
    }
    private Token OptionalInheritance() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_extends")) {
            Token superclass = null;
            match("keyword_extends");
            superclass = currentToken;
            match("idClase");
            return superclass;
        }
        else {
            return new Token("idClase", "Object", currentToken.getLineNumber());
        }
    }
    private void MemberList() throws Exception {
        ClassMember classMember = null;
        if(currentToken.getTokenClass().equals("keyword_static")) {
            match("keyword_static");
            classMember = MetAtrInit();
            classMember.setModifier(static_modifier);
            classMember.setVisibility(public_visibility);
        }
        else if(currentToken.getTokenClass().equals("keyword_private")) {
            match("keyword_private");
            classMember = MetAtrCons();
            classMember.setVisibility(private_visibility);
        }
        else if(currentToken.getTokenClass().equals("keyword_public")) {
            match("keyword_public");
            classMember = MetAtrCons();
            classMember.setVisibility(public_visibility);
        }
        else if(primerosHandler.MemberType.contains(currentToken.getTokenClass())) {
            classMember = MetAtrInit();
            classMember.setModifier(dynamic_modifier);
            classMember.setVisibility(public_visibility);
        }
        else {
            //vacio
        }
    }
    private ClassMember MetAtrInit() throws Exception {
        Type type = MemberType();
        Token id = currentToken;
        match("idMetVar");
        ClassMember classMember = MetAtr(id);
        classMember.setId(id);
        classMember.setType(type);
        MemberList();
        return classMember;
    }
    private ClassMember MetAtrCons() throws Exception {
        ClassMember classMember = null;
        if(currentToken.getTokenClass().equals("keyword_static")) {
            String modifier = StaticOptional();
            classMember = MetAtrInit();
            classMember.setModifier(modifier);
        }
        else if(primerosHandler.PrimitiveType.contains(currentToken.getTokenClass()) || currentToken.getTokenClass().equals("keyword_void")) {
            classMember = MetAtrInit();
            classMember.setModifier(dynamic_modifier);
        }
        else if(currentToken.getTokenClass().equals("idClase")) {
            Type type = new ReferenceType(currentToken.getLexeme());
            Token id = currentToken;
            match("idClase");
            if(currentToken.getTokenClass().equals("parentesisAbre")) {
                type = new PrimitiveType("constructor");
            }
            classMember = MetAtrCons2(id);
            classMember.setType(type);
            classMember.setModifier(dynamic_modifier);
        }
        else {
            throw new SyntaxException(List.of("keyword_static", "tipo primitivo", "idClase"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
        return classMember;
    }
    private ClassMember MetAtrCons2(Token id) throws Exception {
        ClassMember classMember;
        if(currentToken.getTokenClass().equals("parentesisAbre")) {
            classMember = new Method();
            classMember.setId(id);
            symbolTable.setCurrentMethod((Method) classMember);
            FormalArguments();
            Block();
            if(symbolTable.getCurrentClass().getMethod(id.getLexeme()) == null) {
                symbolTable.getCurrentClass().addMethod((Method) classMember);
            }
            else {
                throw new AlreadyDeclaredException("constructor",id.getLineNumber(),id.getLexeme());
            }
            MemberList();
        }
        else if(currentToken.getTokenClass().equals("idMetVar")) {
            Token idMethod = currentToken;
            match("idMetVar");
            classMember = MetAtr(idMethod);
            MemberList();
        }
        else {
            throw new SyntaxException(List.of("(", "idMetVar"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
        return classMember;
    }
    private ClassMember MetAtr(Token id) throws Exception {
        ClassMember member;
        if(currentToken.getTokenClass().equals("puntoYComa")) {
            member = new Attribute();
            member.setId(id);
            if(symbolTable.getCurrentClass().getAttribute(id.getLexeme()) == null) {
                symbolTable.getCurrentClass().addAttribute((Attribute) member);
            }
            else {
                throw new AlreadyDeclaredException("attribute",id.getLineNumber(),id.getLexeme());
            }
            match("puntoYComa");
        }
        else if(currentToken.getTokenClass().equals("parentesisAbre")) {
            member = new Method();
            member.setId(id);
            symbolTable.setCurrentMethod((Method) member);
            FormalArguments();
            Block();
            if(symbolTable.getCurrentClass().getMethod(id.getLexeme()) == null) {
                symbolTable.getCurrentClass().addMethod((Method) member);
            }
            else {
                throw new AlreadyDeclaredException("method",id.getLineNumber(),id.getLexeme());
            }
        }
        else if(currentToken.getTokenClass().equals("opAsign")) {
            member = new Attribute();
            member.setId(id);
            if (symbolTable.getCurrentClass().getAttribute(id.getLexeme()) == null) {
                symbolTable.getCurrentClass().addAttribute((Attribute) member);
            }
            else {
                throw new AlreadyDeclaredException("attribute",id.getLineNumber(),id.getLexeme());
            }
            AttributeInit();
        }
        else {
            throw new SyntaxException(List.of(";", "("), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
        return member;
    }
    private void AbstractMemberList() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_static")) {
            match("keyword_static");
            MetAtrInitAbstract();
        }
        else if(currentToken.getTokenClass().equals("keyword_private")) {
            match("keyword_private");
            StaticOrAbstract();
        }
        else if(currentToken.getTokenClass().equals("keyword_public")) {
            match("keyword_public");
            MetAtrConsAbstract();
        }
        else if(primerosHandler.MemberType.contains(currentToken.getTokenClass())) {
            MetAtrInitAbstract();
        }
        else if(currentToken.getTokenClass().equals("keyword_abstract")) {
            AbstractMethod();
        }
        else {
            //vacio
        }
    }
    private void StaticOrAbstract() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_static")) {
            StaticOptional();
            MetAtrInitAbstract();
        }
        else if(currentToken.getTokenClass().equals("keyword_abstract")) {
            AbstractMethod();
        }
        else if(primerosHandler.MemberType.contains(currentToken.getTokenClass())) {
            MetAtrInitAbstract();
        }
        else {
            throw new SyntaxException(List.of("static", "abstract", "tipo"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void MetAtrInitAbstract() throws Exception {
        MemberType();
        Token id = currentToken;
        match("idMetVar");
        MetAtr(id);
        AbstractMemberList();
    }
    private void MetAtrConsAbstract() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_static")) {
            StaticOptional();
            MetAtrInitAbstract();
        }
        else if(currentToken.getTokenClass().equals("keyword_abstract")) {
            AbstractMethod();
        }
        else if(primerosHandler.PrimitiveType.contains(currentToken.getTokenClass()) || currentToken.getTokenClass().equals("keyword_void")) {
            MetAtrInitAbstract();
        }
        else if(currentToken.getTokenClass().equals("idClase")) {
            match("idClase");
            MetAtrConsAbstract2();
        }
    }
    private void MetAtrConsAbstract2() throws Exception {
        if(currentToken.getTokenClass().equals("parentesisAbre")) {
            FormalArguments();
            Block();
            AbstractMemberList();
        }
        else if(currentToken.getTokenClass().equals("idMetVar")) {
            Token id = currentToken;
            match("idMetVar");
            MetAtr(id);
            AbstractMemberList();
        }
        else {
            throw new SyntaxException(List.of("(", "idMetVar"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void AbstractMethod() throws Exception {
        match("keyword_abstract");
        MemberType();
        match("idMetVar");
        FormalArguments();
        match("puntoYComa");
        AbstractMemberList();
    }

    private void AttributeInit() throws Exception {
        match("opAsign");
        CompoundExpression();
        match("puntoYComa");
    }
    private Type MemberType() throws Exception {
        Type type = null;
        if(currentToken.getTokenClass().equals("keyword_void")) {
            type = new PrimitiveType("void");
            match("keyword_void");
        }
        else if(primerosHandler.Type.contains(currentToken.getTokenClass())) {
            type = Type();
        }
        else {
            throw new SyntaxException(List.of("tipo"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
        return type;
    }
    private Type Type() throws Exception {
        Type type = null;
        if(primerosHandler.PrimitiveType.contains(currentToken.getTokenClass())) {
            type = PrimitiveType();
        }
        else if(currentToken.getTokenClass().equals("idClase")) {
            type = new ReferenceType(currentToken.getLexeme());
            match("idClase");
        }
        else {
            String lexeme = currentToken.getLexeme();
            throw new SyntaxException(List.of("tipo primitivo"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),lexeme);
        }
        return type;
    }
    private Type PrimitiveType() throws Exception {
        Type type = null;
        if(currentToken.getTokenClass().equals("keyword_boolean")) {
            type = new PrimitiveType("boolean");
            match("keyword_boolean");
        }
        else if(currentToken.getTokenClass().equals("keyword_char")) {
            type = new PrimitiveType("char");
            match("keyword_char");
        }
        else if(currentToken.getTokenClass().equals("keyword_int")) {
            type = new PrimitiveType("int");
            match("keyword_int");
        }
        else if(currentToken.getTokenClass().equals("keyword_float")) {
            type = new PrimitiveType("float");
            match("keyword_float");
        }
        else {
            String lexeme = currentToken.getLexeme();
            throw new SyntaxException(List.of("tipo primitivo"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),lexeme);
        }
        return type;
    }
    private String StaticOptional() throws Exception {
        String modifier = null;
        if(currentToken.getTokenClass().equals("keyword_static")) {
            modifier = static_modifier;
            match("keyword_static");
        }
        else {
            modifier = dynamic_modifier;
        }
        return modifier;
    }
    private void FormalArguments() throws Exception {
        match("parentesisAbre");
        FormalArgsListOptional();
        match("parentesisCierra");
    }
    private void FormalArgsListOptional() throws Exception {
        if(primerosHandler.Type.contains(currentToken.getTokenClass())) {
            FormalArgsList();
        }
        else {
            //vacio
        }
    }
    private void FormalArgsList() throws Exception {
        Parameter param = FormalArg();
        if(symbolTable.getCurrentMethod().getParameter(param.getId().getLexeme()) == null) {
            symbolTable.getCurrentMethod().addParameter(param.getId().getLexeme(),param);
        }
        else {
            throw new AlreadyDeclaredException("parameter",param.getId().getLineNumber(),param.getId().getLexeme());
        }
        FormalArgsList2();
    }
    private void FormalArgsList2() throws Exception {
        if(currentToken.getTokenClass().equals("coma")) {
            match("coma");
            FormalArgsList();
        }
        else {
            //vacio
        }
    }
    private Parameter FormalArg() throws Exception {
        Parameter parameter;
        Type paramType= Type();
        Token paramName = currentToken;
        match("idMetVar");
        parameter = new Parameter(paramName,paramType);
        return parameter;
    }
    private Block Block() throws Exception {
        match("llaveAbre");
        Block block = new Block();
        symbolTable.setCurrentBlock(block);
        ArrayList<SentenceNode> sentenceList = new ArrayList<>();
        SentenceList(sentenceList);
        match("llaveCierra");
        block.addSentenceList(sentenceList);
        return block;
    }
    private void SentenceList(ArrayList<SentenceNode> sentenceList) throws Exception {
        if(primerosHandler.Sentence.contains(currentToken.getTokenClass())) {
            SentenceNode sentence = Sentence();
            if(sentence != null)
                sentenceList.add(sentence);
            SentenceList(sentenceList);
        }
        else {
            //vacio
        }
    }
    private SentenceNode Sentence() throws Exception {
        SentenceNode sentence = null;
        if(currentToken.getTokenClass().equals("puntoYComa")) {
            match("puntoYComa");
        }
        else if(primerosHandler.Expression.contains(currentToken.getTokenClass())) {
            sentence = Asign_Call();
            match("puntoYComa");
        }
        else if(currentToken.getTokenClass().equals("keyword_var")) {
            sentence = LocalVar();
            match("puntoYComa");
        }
        else if(currentToken.getTokenClass().equals("keyword_return")) {
            sentence = Return();
            match("puntoYComa");
        }
        else if(currentToken.getTokenClass().equals("keyword_switch")) {
            sentence = Switch();
        }
        else if(currentToken.getTokenClass().equals("keyword_break")) {
            sentence = Break();
            match("puntoYComa");
        }
        else if(currentToken.getTokenClass().equals("keyword_while")) {
            sentence = While();
        }
        else if(currentToken.getTokenClass().equals("llaveAbre")) {
            sentence = Block();
        }
        else if(currentToken.getTokenClass().equals("keyword_if")) {
            sentence = If();
        }
        else if(currentToken.getTokenClass().equals("keyword_for")) {
            sentence = For();
        }
        else {
            String lexeme = currentToken.getLexeme();
            throw new SyntaxException(List.of("inicio sentencia"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),lexeme);
        }
        return sentence;
    }
    private LocalVarNode LocalVar() throws Exception {
        LocalVarNode localVar;
        match("keyword_var");
        Token varId = currentToken;
        match("idMetVar");
        Token assignOp = currentToken;
        match("opAsign");
        CompoundExpNode assignmentExp = CompoundExpression();
        localVar = new LocalVarNode(varId,assignOp,assignmentExp);
        return localVar;
    }
    private ReturnNode Return() throws Exception {
        ReturnNode returnNode = new ReturnNode();
        match("keyword_return");
        ExpressionNode returnExpression = OptionalExpression();
        if(returnExpression != null) {
            returnNode.setReturnExpression(returnExpression);
        }
        return returnNode;
    }
    private ExpressionNode OptionalExpression() throws Exception {
        ExpressionNode expression = null;
        if(primerosHandler.Expression.contains(currentToken.getTokenClass())) {
            expression = Expression();
        }
        else {
            //vacio
        }
        return expression;
    }
    private SwitchNode Switch() throws Exception {
        SwitchNode switchNode = new SwitchNode();
        match("keyword_switch");
        match("parentesisAbre");
        ExpressionNode switchExpression = Expression();
        match("parentesisCierra");
        match("llaveAbre");
        ArrayList<CaseNode> caseList = new ArrayList<>();
        SwitchSentenceList(caseList);
        match("llaveCierra");
        switchNode.setExpression(switchExpression);
        switchNode.setCases(caseList);
        for(CaseNode caseNode : caseList) {
            if(caseNode.getCaseValue() == null) {
                switchNode.setDefaultCase(caseNode);
            }
        }
        return switchNode;
    }
    private void SwitchSentenceList(ArrayList<CaseNode> caseNodes) throws Exception {
        if(currentToken.getTokenClass().equals("keyword_case") || currentToken.getTokenClass().equals("keyword_default")) {
            CaseNode caseNode = CaseList();
            if(caseNode != null)
                caseNodes.add(caseNode);
        }
        else {
            //vacio
        }
    }
    private CaseNode CaseList() throws Exception {
        CaseNode caseNode = null;
        if(currentToken.getTokenClass().equals("keyword_case")) {
            match("keyword_case");
            PrimitiveLiteralNode caseValue = PrimitiveLiteral();
            match("dosPuntos");
            SentenceNode caseSentence = OptionalSentence();
            caseNode = new CaseNode(caseValue,caseSentence);
        }
        else if(currentToken.getTokenClass().equals("keyword_default")) {
            match("keyword_default");
            match("dosPuntos");
            SentenceNode defaultSentence = Sentence();
            caseNode = new CaseNode();
            caseNode.setCaseBody(defaultSentence);
        }
        else {
            //vacio
        }
        return caseNode;
    }
    private SentenceNode OptionalSentence() throws Exception {
        SentenceNode sentence = null;
        if(primerosHandler.Sentence.contains(currentToken.getTokenClass())) {
            sentence = Sentence();
        }
        else {
            //vacio
        }
        return sentence;
    }
    private BreakNode Break() throws Exception {
        BreakNode breakNode;
        Token breakToken = currentToken;
        match("keyword_break");
        breakNode = new BreakNode(breakToken);
        return breakNode;
    }
    private WhileNode While() throws Exception {
        WhileNode whileNode = new WhileNode();
        match("keyword_while");
        match("parentesisAbre");
        ExpressionNode condition = Expression();
        match("parentesisCierra");
        SentenceNode whileBody = Sentence();
        whileNode.setCondition(condition);
        whileNode.setBody(whileBody);
        return whileNode;
    }
    private ForNode For() throws Exception {
        ForNode forNode = new ForNode();
        match("keyword_for");
        match("parentesisAbre");
        ForCases();
        match("parentesisCierra");
        Sentence();
        return forNode;
    }
    private void ForCases() throws Exception {
        if(primerosHandler.Type.contains(currentToken.getTokenClass())) {
            Type();
            match("idMetVar");
            For1();
        }
        else if(currentToken.getTokenClass().equals("keyword_var")) {
            For2();
        }
        else {
            throw new SyntaxException(List.of("tipo primitivo", "keyword_var"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void For1() throws Exception {
        if(currentToken.getTokenClass().equals("opAsign") || currentToken.getTokenClass().equals("opSumaAsign") || currentToken.getTokenClass().equals("opMenosAsign")) {
            AssignmentOperator();
            Operand();
            match("puntoYComa");
            CompoundExpression();
            match("puntoYComa");
            Expression();
        }
        else if(currentToken.getTokenClass().equals("dosPuntos")) {
            match("dosPuntos");
            Access();
        }
        else {
            throw new SyntaxException(List.of("asignacion" ,":"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void For2() throws Exception {
        match("keyword_var");
        match("idMetVar");
        AssignmentOperator();
        Operand();
        match("puntoYComa");
        CompoundExpression();
        match("puntoYComa");
        Expression();
    }
    private IfNode If() throws Exception {
        IfNode ifNode = new IfNode();
        match("keyword_if");
        match("parentesisAbre");
        Expression();
        match("parentesisCierra");
        Sentence();
        ElseOptional();
        return ifNode;
    }
    private void ElseOptional() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_else")) {
            match("keyword_else");
            Sentence();
        }
        else {
            //vacio
        }
    }
    private SentenceNode Asign_Call() throws Exception {
        ExpressionNode expression = Expression();
        SentenceNode sentence;
        if(expression instanceof AssignmentExpNode) {
            sentence = new AssignmentNode(expression);
        }
        else {
            sentence = new CallNode(expression);
        }
        return sentence;
    }
    private ExpressionNode Expression() throws Exception {
        ExpressionNode expression = null;
        expression = CompoundExpression();
        AssignmentExpNode assignmentExpNode =  Expression2();
        if(assignmentExpNode != null) {
            assignmentExpNode.setLeftExp(expression);
            expression = assignmentExpNode;
        }
        return expression;
    }
    private AssignmentExpNode Expression2() throws Exception {
        AssignmentExpNode assignmentExpNode = null;
        if(currentToken.getTokenClass().equals("opAsign") || currentToken.getTokenClass().equals("opSumaAsign") || currentToken.getTokenClass().equals("opMenosAsign")) {
            assignmentExpNode = new AssignmentExpNode();
            Token operator = AssignmentOperator();
            CompoundExpNode rightExp = CompoundExpression();
            assignmentExpNode.setOperator(operator);
            assignmentExpNode.setRightExp(rightExp);
        }
        else {
            //vacio
        }
        return assignmentExpNode;
    }
    private Token AssignmentOperator() throws Exception {
        Token assignmentOp = currentToken;
        if(currentToken.getTokenClass().equals("opAsign")) {
            match("opAsign");
        }
        else if(currentToken.getTokenClass().equals("opSumaAsign")) {
            match("opSumaAsign");
        }
        else if(currentToken.getTokenClass().equals("opMenosAsign")) {
            match("opMenosAsign");
        }
        else {
            throw new SyntaxException(List.of("operador de asignacion"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
        return assignmentOp;
    }
    private CompoundExpNode CompoundExpression() throws Exception {
        CompoundExpNode compoundExpNode = new BasicExpNode();   //MODIFICAR
        BasicExpression();
        CompoundExpression2();
        return compoundExpNode;
    }
    private void CompoundExpression2() throws Exception {
        if(primerosHandler.BinaryOperator.contains(currentToken.getTokenClass())) {
            BinaryOperator();
            BasicExpression();
            CompoundExpression2();
        }
        else {
            //vacio
        }
    }
    private void BasicExpression() throws Exception {
        if(primerosHandler.UnaryOperator.contains(currentToken.getTokenClass())) {
            UnaryOperator();
            Operand();
        }
        else if(primerosHandler.Literal.contains(currentToken.getTokenClass()) || primerosHandler.Access.contains(currentToken.getTokenClass())) {
            Operand();
        }
        else {
            throw new SyntaxException(List.of("operador unario", "operando"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void BinaryOperator() throws Exception {
        if(currentToken.getTokenClass().equals("opSuma")) {
            match("opSuma");
        }
        else if(currentToken.getTokenClass().equals("opMenos")) {
            match("opMenos");
        }
        else if(currentToken.getTokenClass().equals("opMult")) {
            match("opMult");
        }
        else if(currentToken.getTokenClass().equals("opDiv")) {
            match("opDiv");
        }
        else if(currentToken.getTokenClass().equals("opMod")) {
            match("opMod");
        }
        else if(currentToken.getTokenClass().equals("opMenor")) {
            match("opMenor");
        }
        else if(currentToken.getTokenClass().equals("opMenorIgual")) {
            match("opMenorIgual");
        }
        else if(currentToken.getTokenClass().equals("opMayor")) {
            match("opMayor");
        }
        else if(currentToken.getTokenClass().equals("opMayorIgual")) {
            match("opMayorIgual");
        }
        else if(currentToken.getTokenClass().equals("opIgualdad")) {
            match("opIgualdad");
        }
        else if(currentToken.getTokenClass().equals("opDistinto")) {
            match("opDistinto");
        }
        else if(currentToken.getTokenClass().equals("opAnd")) {
            match("opAnd");
        }
        else if(currentToken.getTokenClass().equals("opOr")) {
            match("opOr");
        }
        else {
            throw new SyntaxException(List.of("operador binario"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void UnaryOperator() throws Exception {
        if(currentToken.getTokenClass().equals("opNot")) {
            match("opNot");
        }
        else if(currentToken.getTokenClass().equals("opMenos")) {
            match("opMenos");
        }
        else if(currentToken.getTokenClass().equals("opSuma")) {
            match("opSuma");
        }
        else {
            throw new SyntaxException(List.of("operador unario"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void Operand() throws Exception {
        if(primerosHandler.Literal.contains(currentToken.getTokenClass())) {
            Literal();
        }
        else if(primerosHandler.Access.contains(currentToken.getTokenClass())) {
            Access();
        }
        else {
            throw new SyntaxException(List.of("literal", "acceso"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void Literal() throws Exception {
        if(primerosHandler.PrimitiveLiteral.contains(currentToken.getTokenClass())) {
            PrimitiveLiteral();
        }
        else if(primerosHandler.ObjectLiteral.contains(currentToken.getTokenClass())) {
            ObjectLiteral();
        }
        else {
            throw new SyntaxException(List.of("literal"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private PrimitiveLiteralNode PrimitiveLiteral() throws Exception {
        PrimitiveLiteralNode primitiveLiteral = null;
        if(currentToken.getTokenClass().equals("keyword_true")) {
            match("keyword_true");
        }
        else if(currentToken.getTokenClass().equals("keyword_false")) {
            match("keyword_false");
        }
        else if(currentToken.getTokenClass().equals("intLiteral")) {
            match("intLiteral");
        }
        else if(currentToken.getTokenClass().equals("charLiteral")) {
            match("charLiteral");
        }
        else if(currentToken.getTokenClass().equals("floatLiteral")) {
            match("floatLiteral");
        }
        else {
            throw new SyntaxException(List.of("literal primitivo"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
        return primitiveLiteral;
    }
    private void ObjectLiteral() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_null")) {
            match("keyword_null");
        }
        else if(currentToken.getTokenClass().equals("stringLiteral")) {
            match("stringLiteral");
        }
        else {
            throw new SyntaxException(List.of("literal objeto"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void Access() throws Exception {
        if(primerosHandler.Primary.contains(currentToken.getTokenClass())) {
            Primary();
            OptionalChain();
        }
        else {
            throw new SyntaxException(List.of("acceso"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void Primary() throws Exception{
        if(currentToken.getTokenClass().equals("idMetVar")) {
            match("idMetVar");
            VarMetAccess();
        }
        else if(currentToken.getTokenClass().equals("keyword_this")) {
            ThisAccess();
        }
        else if(currentToken.getTokenClass().equals("keyword_new")) {
            ConstructorAccess();
        }
        else if(currentToken.getTokenClass().equals("idClase")) {
            StaticMethodAccess();
        }
        else if(currentToken.getTokenClass().equals("parentesisAbre")) {
            ParenthesizedExpression();
        }
        else {
            throw new SyntaxException(List.of("idMetVar", "this", "new", "idClase", "("), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),currentToken.getLexeme());
        }
    }
    private void ThisAccess() throws Exception {
        match("keyword_this");
    }
    private void VarMetAccess() throws Exception {
        if(currentToken.getTokenClass().equals("parentesisAbre")) {
            ActualArgs();
        }
        else {
            //vacio
        }
    }
    private void ActualArgs() throws Exception {
        match("parentesisAbre");
        ExpsListOptional();
        match("parentesisCierra");
    }
    private void ExpsListOptional() throws Exception {
        if(primerosHandler.Expression.contains(currentToken.getTokenClass())) {
            ExpsList();
        }
        else {
            //vacio
        }
    }
    private void ExpsList() throws Exception {
        Expression();
        ExpsList2();
    }
    private void ExpsList2() throws Exception {
        if(currentToken.getTokenClass().equals("coma")) {
            match("coma");
            ExpsList();
        }
        else {
            //vacio
        }
    }
    private void ConstructorAccess() throws Exception {
        match("keyword_new");
        match("idClase");
        ActualArgs();
    }
    private void StaticMethodAccess() throws Exception {
        match("idClase");
        match("punto");
        match("idMetVar");
        ActualArgs();
    }
    private void ParenthesizedExpression() throws Exception {
        match("parentesisAbre");
        Expression();
        match("parentesisCierra");
    }
    private void OptionalChain() throws Exception {
        if(currentToken.getTokenClass().equals("punto")) {
            match("punto");
            match("idMetVar");
            VarMetChain();
        }
        else {
            //vacio
        }
    }
    private void VarMetChain() throws Exception {
        if(currentToken.getTokenClass().equals("punto")) {
            OptionalChain();
        }
        else if(currentToken.getTokenClass().equals("parentesisAbre")) {
            ActualArgs();
            OptionalChain();
        }
        else {
            //vacio
        }
    }
}
