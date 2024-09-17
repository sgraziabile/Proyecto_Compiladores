package main;

import entities.KeywordHandler;
import entities.PrimerosHandler;
import entities.Token;
import lexical.LexicalAnalyzer;
import sourcemanager.SourceManagerImpl;
import syntactical.SyntaxAnalyzer;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainModule {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("No file path provided.");
        } else {
            String filePath = args[0];
            SourceManagerImpl sourceManager = new SourceManagerImpl();
            KeywordHandler keywordHandler = new KeywordHandler();
            PrimerosHandler primerosHandler = new PrimerosHandler();
            boolean success = true;
            try {
                sourceManager.open(filePath);
                LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceManager, keywordHandler);
                SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer, primerosHandler);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch(Exception e) {
                success = false;
                System.out.println(e.getMessage());
            }
            if(success) {
                System.out.println("Compiled with no errors");
            }
            try {
                sourceManager.close();
            } catch (Exception e) {
                System.out.println("Error closing file");
            }
        }
    }
}