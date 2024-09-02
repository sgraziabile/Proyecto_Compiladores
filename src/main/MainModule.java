package main;

import entities.KeywordHandler;
import entities.Token;
import lexical.LexicalAnalyzer;
import sourcemanager.SourceManagerImpl;

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
            ArrayList<Token> tokenList = new ArrayList<>();
            boolean success = true;
            try {
                sourceManager.open(filePath);
                LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceManager, keywordHandler);
                Token nextToken;
                do{
                    nextToken = lexicalAnalyzer.nextToken();
                    tokenList.add(nextToken);
                } while(!nextToken.getTokenClass().equals("EOF"));
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch(Exception e) {
                System.out.println(e.getMessage());
                success = false;
            }
            if(success) {
                for (Token token : tokenList) {
                    String tokenResult = "(" + token.getTokenClass() + "," + token.getLexeme() + "," + token.getLineNumber() + ")";
                    System.out.println(tokenResult);
                }
                System.out.println("[SinErrores]");
            }
            try {
                sourceManager.close();
            } catch (Exception e) {
                System.out.println("Error closing file.");
            }
        }
    }
}