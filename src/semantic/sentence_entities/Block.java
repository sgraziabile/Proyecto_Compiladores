package semantic.sentence_entities;

import java.util.ArrayList;
import java.util.Hashtable;

public class Block extends SentenceNode {
    private ArrayList<SentenceNode> sentenceList;

    public Block() {
        sentenceList = new ArrayList<>();
        parentBlock = null;
    }
    public Block(Block parentBlock) {
        sentenceList = new ArrayList<>();
        this.parentBlock = parentBlock;
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
    public void checkSentences() throws Exception {
        for(SentenceNode s : sentenceList) {
            s.checkSentences();
        }
    }
    public String toString() {
        return "Block";
    }

}
