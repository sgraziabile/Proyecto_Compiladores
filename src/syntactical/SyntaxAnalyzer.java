package syntactical;

import entities.PrimerosHandler;
import entities.Token;
import exceptions.SyntaxException;
import lexical.LexicalAnalyzer;

import java.util.List;

public class SyntaxAnalyzer {
    LexicalAnalyzer lexicalAnalyzer;
    Token currentToken;
    PrimerosHandler primerosHandler;

    public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer, PrimerosHandler primerosHandler) throws Exception {
        this.primerosHandler = primerosHandler;
        this.lexicalAnalyzer = lexicalAnalyzer;
        currentToken = lexicalAnalyzer.nextToken();
        Init();
    }

    private void match(String tokenName) throws Exception {
        String tokenClass = currentToken.getTokenClass();
        if (tokenName.equals(tokenClass)) {
            currentToken = lexicalAnalyzer.nextToken();
        } else {
            throw new SyntaxException(List.of(tokenName), tokenClass, Integer.toString(currentToken.getLineNumber()));
        }
    }
    public void Init() throws Exception {
        ClassList();
        match("$");
    }
    private void ClassList() throws Exception {
        //primeros de Clase = {class}
        if (currentToken.getTokenClass().equals("keyword_class")) {
            Class();
            ClassList();
        }
        else {
            //no hago nada porque es vacio
        }
    }
    private void Class() throws Exception {
        match("keyword_class");
        match("idClase");
        OptionalInheritance();
        match("llaveAbre");
        MemberList();
        match("llaveCierra");
    }
    private void OptionalInheritance() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_extends")) {
            match("keyword_extends");
            match("idClase");
        }
        else {
            //vacio
        }
    }
    private void MemberList() throws Exception {
        //primeros de ListaMiembros = {static,boolean,char,int,void}
        if(currentToken.getTokenClass().equals("keyword_static")) {
            StaticOptional();
            MemberType();
            match("idMetVar");
            MetAtr();
            MemberList();
        }
        else if(primerosHandler.MemberType.contains(currentToken.getTokenClass())) {
            MemberType();
            match("idMetVar");
            MetAtr();
            MemberList();
        }
        else if(currentToken.getTokenClass().equals("keyword_public")) {    //constructor
            match("keyword_public");
            match("idClase");
            FormalArguments();
            Block();
            MemberList();
        }
        else {
            //vacio
        }
    }
    private void MetAtr() throws Exception {
        if(currentToken.getTokenClass().equals("puntoYComa")) {
            match("puntoYComa");
        }
        else if(currentToken.getTokenClass().equals("parentesisAbre")) {
            FormalArguments();
            Block();
        }
        else {
            throw new SyntaxException(List.of(";", "("), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()));
        }
    }
    private void MemberType() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_void")) {
            match("keyword_void");
        }
        else if(primerosHandler.Type.contains(currentToken.getTokenClass())) {
            Type();
        }
        else {
            //vacio
        }
    }
    private void Type() throws Exception {
        if(primerosHandler.PrimitiveType.contains(currentToken.getTokenClass())) {
            PrimitiveType();
        }
        else if(currentToken.getTokenClass().equals("idClase")) {
            match("idClase");
        }
        else {
            throw new SyntaxException(List.of("tipo primitivo"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()));
        }
    }
    private void PrimitiveType() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_boolean")) {
            match("keyword_boolean");
        }
        else if(currentToken.getTokenClass().equals("keyword_char")) {
            match("keyword_char");
        }
        else if(currentToken.getTokenClass().equals("keyword_int")) {
            match("keyword_int");
        }
        else if(currentToken.getTokenClass().equals("keyword_float")) {
            match("keyword_float");
        }
        else {
            throw new SyntaxException(List.of("tipo primitivo"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()));
        }
    }
    private void StaticOptional() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_static")) {
            match("keyword_static");
        }
        else {
            //vacio
        }
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
        FormalArg();
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
    private void FormalArg() throws Exception {
        Type();
        match("idMetVar");
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
        else if(currentToken.getTokenClass().equals("parentesisAbre")) {
            Block();
        }
        else if(currentToken.getTokenClass().equals("keyword_if")) {
            If();
        }
        else {
            throw new SyntaxException(List.of("inicio sentencia"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()));
        }
    }
    private void LocalVar() throws Exception {
        match("keyword_var");
        match("idMetVar");
        match("opAsign");
        CompoundExpression();
    }
    private void CompoundExpression() throws Exception {
        //TODO
    }
    private void Return() throws Exception {
        match("keyword_return");
        OptionalExpression();
    }
    private void OptionalExpression() throws Exception {
       //TODO
    }
    private void Switch() throws Exception {
        match("keyword_switch");
        match("parentesisAbre");
        Expression();
        match("parentesisCierra");
        match("llaveAbre");
        CaseList();
        match("llaveCierra");
    }
    private void CaseList() throws Exception {
        //TODO
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

    }
}
