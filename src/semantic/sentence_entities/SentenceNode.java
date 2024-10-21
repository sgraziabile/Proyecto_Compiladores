package semantic.sentence_entities;

public abstract class SentenceNode {
    protected Block parentBlock;

    public void setParentBlock(Block parentBlock) {
        this.parentBlock = parentBlock;
    }
    public String toString() {
        return "SentenceNode";
    }
}
