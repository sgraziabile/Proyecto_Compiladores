package main;

import Exceptions.LexicalException;
import entities.KeywordHandler;
import entities.Token;
import lexical.LexicalAnalyzer;
import sourcemanager.SourceManagerImpl;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainModule {
    private SourceManagerImpl sourceManager;
    private LexicalAnalyzer lexicalAnalyzer;

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
                Token nextToken = lexicalAnalyzer.nextToken();
                while(!nextToken.getTokenClass().equals("EOF")) {
                    tokenList.add(nextToken);
                    nextToken = lexicalAnalyzer.nextToken();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch(LexicalException le) {
                System.out.println(le.getMessage());
                success = false;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(success) {
                for (Token token : tokenList) {
                    String tokenResult = "(" + token.getTokenClass() + "," + token.getLexeme() + "," + token.getLineNumber() + ")";
                    System.out.println(tokenResult);
                }
            }
        }
    }
}