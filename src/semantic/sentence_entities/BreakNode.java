package semantic.sentence_entities;

import entities.Token;
import exceptions.InvalidBreakException;

import static main.MainModule.symbolTable;

public class BreakNode extends SentenceNode {
    protected Token breakToken;

    public BreakNode(Token breakToken) {
        this.breakToken = breakToken;
    }
    public void checkSentence() throws Exception {
        if(!isInsideLoop()) {
            throw new InvalidBreakException(breakToken.getLineNumber());
        }
    }
    public boolean isInsideLoop() {
        return isBrekable;
    }
    /*public boolean isInsideLoop() {
        boolean insideLoop = false;
        Block sentenceBlock = this.getParentBlock().getParentBlock();
        while (sentenceBlock != null) {
            System.out.println(sentenceBlock.getSentenceList().size());
            for (SentenceNode sentence : sentenceBlock.getSentenceList()) {
                if (sentence instanceof WhileNode) {
                    SentenceNode whileBody = ((WhileNode) sentence).getBody();
                    insideLoop = isInsideBlock(whileBody);
                } else if (sentence instanceof SwitchNode) {
                    for(CaseNode caseNode : ((SwitchNode) sentence).getCases()) {
                        if(isInsideBlock(caseNode.getCaseBody())) {
                            insideLoop = true;
                            break;
                        }
                    }
                }
            }
            sentenceBlock = sentenceBlock.getParentBlock();
            if(sentenceBlock == symbolTable.getCurrentBlock()) {
                break;
            }
        }
        return insideLoop;
    }*/
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
