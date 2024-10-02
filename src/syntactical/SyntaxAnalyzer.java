package syntactical;

import entities.PrimerosHandler;
import entities.Token;
import exceptions.AlreadyDeclaredException;
import exceptions.SyntaxException;
import lexical.LexicalAnalyzer;
import semantic.SymbolTable;
import semantic.declared_entities.*;
import semantic.declared_entities.Class;

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
        for(Class c: symbolTable.getClasses().values()) {
            if(c.getSuperclass() != null) {
                System.out.println(c.getName() + " extends " + c.getSuperclass().getLexeme());
            }
            for(Attribute a: c.getAttributes().values()) {
                a.print();
            }
            for(Method m: c.getMethods().values()) {
                m.print();
                for(Parameter p: m.getParameterList()) {
                    p.print();
                }
            }
            System.out.println();
        }

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
            type = new PrimitiveType("void");       //tipo void?
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
        symbolTable.getCurrentMethod().addParameter(param.getId().getLexeme(),param);
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
    private void Block() throws Exception {
        match("llaveAbre");
        SentenceList();
        match("llaveCierra");
    }
    private void SentenceList() throws Exception {
        if(primerosHandler.Sentence.contains(currentToken.getTokenClass())) {
            Sentence();
            SentenceList();
        }
        else {
            //vacio
        }
    }
    private void Sentence() throws Exception {
        if(currentToken.getTokenClass().equals("puntoYComa")) {
            match("puntoYComa");
        }
        else if(primerosHandler.Expression.contains(currentToken.getTokenClass())) {
            Asign_Call();
            match("puntoYComa");
        }
        else if(currentToken.getTokenClass().equals("keyword_var")) {
            LocalVar();
            match("puntoYComa");
        }
        else if(currentToken.getTokenClass().equals("keyword_return")) {
            Return();
            match("puntoYComa");
        }
        else if(currentToken.getTokenClass().equals("keyword_switch")) {
            Switch();
        }
        else if(currentToken.getTokenClass().equals("keyword_break")) {
            Break();
            match("puntoYComa");
        }
        else if(currentToken.getTokenClass().equals("keyword_while")) {
            While();
        }
        else if(currentToken.getTokenClass().equals("llaveAbre")) {
            Block();
        }
        else if(currentToken.getTokenClass().equals("keyword_if")) {
            If();
        }
        else if(currentToken.getTokenClass().equals("keyword_for")) {
            For();
        }
        else {
            String lexeme = currentToken.getLexeme();
            throw new SyntaxException(List.of("inicio sentencia"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()),lexeme);
        }
    }
    private void LocalVar() throws Exception {
        match("keyword_var");
        match("idMetVar");
        match("opAsign");
        CompoundExpression();
    }
    private void Return() throws Exception {
        match("keyword_return");
        OptionalExpression();
    }
    private void OptionalExpression() throws Exception {
        if(primerosHandler.Expression.contains(currentToken.getTokenClass())) {
            Expression();
        }
        else {
            //vacio
        }
    }
    private void Switch() throws Exception {
        match("keyword_switch");
        match("parentesisAbre");
        Expression();
        match("parentesisCierra");
        match("llaveAbre");
        SwitchSentenceList();
        match("llaveCierra");
    }
    private void SwitchSentenceList() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_case") || currentToken.getTokenClass().equals("keyword_default")) {
            CaseList();
            SwitchSentenceList();
        }
        else {
            //vacio
        }
    }
    private void CaseList() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_case")) {
            match("keyword_case");
            PrimitiveLiteral();
            match("dosPuntos");
            OptionalSentence();
        }
        else if(currentToken.getTokenClass().equals("keyword_default")) {
            match("keyword_default");
            match("dosPuntos");
            Sentence();
        }
        else {
            //vacio
        }
    }
    private void OptionalSentence() throws Exception {
        if(primerosHandler.Sentence.contains(currentToken.getTokenClass())) {
            Sentence();
        }
        else {
            //vacio
        }
    }
    private void Break() throws Exception {
        match("keyword_break");
    }
    private void While() throws Exception {
        match("keyword_while");
        match("parentesisAbre");
        Expression();
        match("parentesisCierra");
        Sentence();
    }
    private void For() throws Exception {
        match("keyword_for");
        match("parentesisAbre");
        ForCases();
        match("parentesisCierra");
        Sentence();
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
    private void If() throws Exception {
        match("keyword_if");
        match("parentesisAbre");
        Expression();
        match("parentesisCierra");
        Sentence();
        ElseOptional();
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
    private void Asign_Call() throws Exception {
        Expression();
    }
    private void Expression() throws Exception {
        CompoundExpression();
        Expression2();
    }
    private void Expression2() throws Exception {
        if(currentToken.getTokenClass().equals("opAsign") || currentToken.getTokenClass().equals("opSumaAsign") || currentToken.getTokenClass().equals("opMenosAsign")) {
            AssignmentOperator();
            CompoundExpression();
        }
        else {
            //vacio
        }
    }
    private void AssignmentOperator() throws Exception {
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
    }
    private void CompoundExpression() throws Exception {
        BasicExpression();
        CompoundExpression2();
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
    private void PrimitiveLiteral() throws Exception {
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
