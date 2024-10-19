package semantic.sentence_entities;

import java.util.ArrayList;
import java.util.Hashtable;

public class Block extends SentenceNode {
    private ArrayList<SentenceNode> sentenceList;
    private Block parentBlock;

    public Block() {
        sentenceList = new ArrayList<>();
    }
    public void addSentence(SentenceNode sentence) {
        sentenceList.add(sentence);
    }
    public ArrayList<SentenceNode> getSentenceList() {
        return sentenceList;
    }
    public void setParentBlock(Block parentBlock) {
        this.parentBlock = parentBlock;
    }
    public Block getParentBlock() {
        return parentBlock;
    }

}
