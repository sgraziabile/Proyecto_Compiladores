package main;

import Exceptions.LexicalException;
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
                while(!nextToken.getTokenClass().equals("EOF")) {
                    System.out.println(nextToken.getLexeme());
                    nextToken = lexicalAnalyzer.nextToken();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch(LexicalException le) {
                System.out.println(le.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}