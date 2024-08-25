package main;

import entities.Token;
import lexical.LexicalAnalyzer;
import sourcemanager.SourceManagerImpl;

import java.io.FileNotFoundException;

public class MainModule {
    private SourceManagerImpl sourceManager;
    private LexicalAnalyzer lexicalAnalyzer;

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("No file path provided.");
        } else {
            String filePath = args[0];
            SourceManagerImpl sourceManager = new SourceManagerImpl();
            try {
                sourceManager.open(filePath);
                LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceManager);
                Token nextToken = lexicalAnalyzer.nextToken();
                while(!nextToken.getTokenClass().equals("END_OF_FILE")) {
                    System.out.println(nextToken.getLexeme());
                    nextToken = lexicalAnalyzer.nextToken();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            }
        }
    }
}