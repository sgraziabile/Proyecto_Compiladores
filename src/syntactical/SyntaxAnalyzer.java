package syntactical;

import entities.PrimerosHandler;
import entities.Token;
import exceptions.SyntaxException;
import lexical.LexicalAnalyzer;

import java.lang.reflect.Array;
import java.util.List;

public class SyntaxAnalyzer {
    LexicalAnalyzer lexicalAnalyzer;
    Token currentToken;
    PrimerosHandler primerosHandler;

    public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer, PrimerosHandler primerosHandler) throws Exception {
        this.primerosHandler = primerosHandler;
        this.lexicalAnalyzer = lexicalAnalyzer;
        currentToken = lexicalAnalyzer.nextToken();
        Inicial();
    }

    private void match(String tokenName) throws Exception {
        String tokenClass = currentToken.getTokenClass();
        if (tokenName.equals(tokenClass)) {
            currentToken = lexicalAnalyzer.nextToken();
        } else {
            throw new SyntaxException(List.of(tokenName), tokenClass, Integer.toString(currentToken.getLineNumber()));
        }
    }
    public void Inicial() throws Exception {
        ListaClases();
        match("$");
    }
    private void ListaClases() throws Exception {
        //primeros de Clase = {class}
        if (currentToken.getTokenClass().equals("keyword_class")) {
            Clase();
            ListaClases();
        }
        else {
            //no hago nada porque es vacio
        }
    }
    private void Clase() throws Exception {
        match("keyword_class");
        match("idClase");
        HerenciaOpcional();
        match("llaveAbre");
        ListaMiembros();
        match("llaveCierra");
    }
    private void HerenciaOpcional() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_extends")) {
            match("keyword_extends");
            match("idClase");
        }
        else {
            //vacio
        }
    }
    private void ListaMiembros() throws Exception {
        //primeros de ListaMiembros = {static,boolean,char,int,void}
        if(currentToken.getTokenClass().equals("static")) {
            EstaticoOpcional();
            TipoMiembro();
            match("idMetVar");
            MetAtr();
            ListaMiembros();
        }
        else if(currentToken.getTokenClass().equals("keyword_boolean") || currentToken.getTokenClass().equals("keyword_char") || currentToken.getTokenClass().equals("keyword_int") || currentToken.getTokenClass().equals("keyword_void")) {
            TipoMiembro();
            match("idMetVar");
            MetAtr();
            ListaMiembros();
        }
        else if(currentToken.getTokenClass().equals("keyword_public")) {
            match("keyword_public");
            match("idClase");
            ArgsFormales();
            Bloque();
            ListaMiembros();
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
            ArgsFormales();
            Bloque();
        }
        else {
            throw new SyntaxException(List.of(";", "("), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()));
        }
    }
    private void TipoMiembro() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_void")) {
            match("keyword_void");
        }
        else if(currentToken.getTokenClass().equals("keyword_boolean") || currentToken.getTokenClass().equals("keyword_char") || currentToken.getTokenClass().equals("keyword_int") || currentToken.getTokenClass().equals("idClase")) {
            Tipo();
        }
        else {
            //vacio
        }
    }
    private void Tipo() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_boolean") || currentToken.getTokenClass().equals("keyword_char") || currentToken.getTokenClass().equals("keyword_int")) {
            TipoPrimitivo();
        }
        else if(currentToken.getTokenClass().equals("idClase")) {
            match("idClase");
        }
        else {
            throw new SyntaxException(List.of("tipo primitivo"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()));
        }
    }
    private void TipoPrimitivo() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_boolean")) {
            match("keyword_boolean");
        }
        else if(currentToken.getTokenClass().equals("keyword_char")) {
            match("keyword_char");
        }
        else if(currentToken.getTokenClass().equals("keyword_int")) {
            match("keyword_int");
        }
        else {
            throw new SyntaxException(List.of("tipo primitivo"), currentToken.getTokenClass(), Integer.toString(currentToken.getLineNumber()));
        }
    }
    private void EstaticoOpcional() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_static")) {
            match("keyword_static");
            TipoMiembro();
            match("idMetVar");
            MetAtr();
            ListaMiembros();
        }
        else {
            //vacio
        }
    }
    private void ArgsFormales() throws Exception {
        match("parentesisAbre");
        ListaArgsFormalesOpcional();
        match("parentesisCierra");
    }
    private void ListaArgsFormalesOpcional() throws Exception {
        if(currentToken.getTokenClass().equals("keyword_boolean") || currentToken.getTokenClass().equals("keyword_char") || currentToken.getTokenClass().equals("keyword_int") || currentToken.getTokenClass().equals("idClase")) {
            ListaArgsFormales();
        }
        else {
            //vacio
        }
    }
    private void ListaArgsFormales() throws Exception {
        ArgFormal();
        ListaArgsFormales2();
    }
    private void ListaArgsFormales2() throws Exception {
        if(currentToken.getTokenClass().equals("coma")) {
            match("coma");
            ListaArgsFormales();
        }
        else {
            //vacio
        }
    }
    private void ArgFormal() throws Exception {
        Tipo();
        match("idMetVar");
    }
    private void Bloque() throws Exception {
        match("llaveAbre");
        ListaSentencias();
        match("llaveCierra");
    }
    private void ListaSentencias() throws Exception {
        
    }
}
