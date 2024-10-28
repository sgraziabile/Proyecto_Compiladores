package semantic.sentence_entities;

import entities.Token;

import static main.MainModule.symbolTable;

public class BreakNode extends SentenceNode {
    protected Token breakToken;

    public BreakNode(Token breakToken) {
        this.breakToken = breakToken;
    }
    public void checkSentence() throws Exception {
        if(!isInsideLoop()) {
            throw new Exception("Break statement outside loop");
        }
    }
    public boolean isInsideLoop() {
        System.out.println("BreakNode isInsideLoop");
        boolean insideLoop = false;
        Block sentenceBlock = this.getParentBlock().getParentBlock();
        if(sentenceBlock != null) {
            while (sentenceBlock != null) {
                for (SentenceNode sentence : sentenceBlock.getSentenceList()) {
                    if(sentence instanceof WhileNode) {
                        SentenceNode whileBody = ((WhileNode) sentence).getBody();
                        insideLoop = isInsideBlock(whileBody);
                    }
                    else if(sentence instanceof IfNode) {
                        SentenceNode thenBody = ((IfNode) sentence).getThenBody();
                        insideLoop = isInsideBlock(thenBody);
                        if(!insideLoop && sentence instanceof IfWithElseNode) {
                            SentenceNode elseBody = ((IfWithElseNode) sentence).getElseBody();
                            insideLoop = isInsideBlock(elseBody);
                        }
                    }
                }
                sentenceBlock = sentenceBlock.getParentBlock();
            }
        }
        return insideLoop;
    }
    private boolean isInsideBlock(SentenceNode block) {
        boolean insideBlock = false;
        for(SentenceNode sentence : ((Block)block).getSentenceList()) {
            if(sentence == this) {
                insideBlock = true;
                break;
            }
        }
        return insideBlock;
    }
    public String toString() {
        return "BreakNode";
    }


}
