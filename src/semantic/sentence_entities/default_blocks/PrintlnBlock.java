package semantic.sentence_entities.default_blocks;

import code_generator.CodeGenerator;
import semantic.sentence_entities.Block;

import static main.MainModule.writer;

public class PrintlnBlock extends Block {
    public PrintlnBlock() {
        super();
    }
    public void generateCode() throws Exception{
        writer.write(CodeGenerator.PRNLN + "    ; Imprime un salto de linea\n");
    }
}
