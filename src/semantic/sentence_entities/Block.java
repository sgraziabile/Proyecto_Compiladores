package semantic.sentence_entities;

import java.util.ArrayList;
import java.util.Hashtable;

import static main.MainModule.symbolTable;
import static main.MainModule.writer;

public class Block extends SentenceNode {
    private ArrayList<SentenceNode> sentenceList;
    private ArrayList<LocalVarNode> localVars;
    private Hashtable<String,LocalVarNode> localVarsHash;

    public Block() {
        sentenceList = new ArrayList<>();
        parentBlock = null;
        localVars = new ArrayList<>();
        localVarsHash = new Hashtable<>();
    }
    public Block(Block parentBlock) {
        sentenceList = new ArrayList<>();
        this.parentBlock = parentBlock;
        localVars = new ArrayList<>();
        localVarsHash = new Hashtable<>();
    }
    public void addSentence(SentenceNode sentence) {
        sentenceList.add(sentence);
    }
    public void addSentenceList(ArrayList<SentenceNode> sentenceList) {
        this.sentenceList.addAll(sentenceList);
    }
    public ArrayList<SentenceNode> getSentenceList() {
        return sentenceList;
    }
    public Block getParentBlock() {
        return parentBlock;
    }
    public void addLocalVar(LocalVarNode localVar) {
        localVars.add(localVar);
        localVarsHash.put(localVar.getId().getLexeme(),localVar);
    }
    public LocalVarNode getLocalVar(String id) {
        return localVarsHash.get(id);
    }
    public ArrayList<LocalVarNode> getLocalVars() {
        return localVars;
    }
    public void checkSentence() throws Exception {
        for(SentenceNode s : sentenceList) {
            if (s instanceof Block) {
                symbolTable.setCurrentBlock((Block) s);
                s.checkSentence();
                symbolTable.setCurrentBlock(s.getParentBlock());
            } else
                s.checkSentence();
        }
    }
    public void setBreakable() {
        isBrekable = true;
        for(SentenceNode s : sentenceList) {
            s.setBreakable();
        }
    }
    public void generateCode() throws Exception{
        for(SentenceNode s : sentenceList) {
            s.generateCode();
        }
        writer.write("FMEM "+localVars.size()+" ; Libera espacio de variables locales\n");
    }
    public void setLocalVarOffsets() {
        int i = 0;
        int offset;
        for(LocalVarNode l : localVars) {
            offset = i * (-1);
            l.setOffset(offset);
            i += 1;
        }
    }
    public String toString() {
        return "Block";
    }

}
