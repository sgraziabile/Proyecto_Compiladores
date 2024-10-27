package semantic.sentence_entities;

public abstract class SentenceNode {
    protected Block parentBlock;
    protected int line;

    public void setParentBlock(Block parentBlock) {
        this.parentBlock = parentBlock;
    }
    public Block getParentBlock() {
        return parentBlock;
    }
    public void setLine(int line) {
        this.line = line;
    }
    public int getLine() {
        return line;
    }
    public String toString() {
        return "SentenceNode";
    }
    public void checkSentence() throws Exception {

    }
}
