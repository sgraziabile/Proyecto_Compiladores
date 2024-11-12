package main;

import code_generator.CodeGenerator;
import entities.KeywordHandler;
import entities.PrimerosHandler;
import entities.Token;
import lexical.LexicalAnalyzer;
import semantic.SymbolTable;
import sourcemanager.SourceManagerImpl;
import syntactical.SyntaxAnalyzer;

import java.io.*;
import java.util.ArrayList;

public class MainModule {
    public static SymbolTable symbolTable;
    public static BufferedWriter writer;
    public static CodeGenerator codeGenerator;
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("No file path provided.");
        } else {
            String filePath = args[0];
            SourceManagerImpl sourceManager = new SourceManagerImpl();
            KeywordHandler keywordHandler = new KeywordHandler();
            PrimerosHandler primerosHandler = new PrimerosHandler();
            symbolTable = new SymbolTable();
            codeGenerator = new CodeGenerator();
            String outputFilePath = "prueba.out";
            try {
                writer = new BufferedWriter(new FileWriter(outputFilePath));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            boolean success = true;
            try {
                sourceManager.open(filePath);
                LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceManager, keywordHandler);
                SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer, primerosHandler,symbolTable);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch(Exception e) {
                success = false;
                System.out.println(e.getMessage());
            }
            if(success) {
                System.out.println("Compilation successful");
                System.out.println("[SinErrores]");
            }
            try {
                sourceManager.close();
            } catch (Exception e) {
                System.out.println("Error closing file");
            }
        }
    }
}