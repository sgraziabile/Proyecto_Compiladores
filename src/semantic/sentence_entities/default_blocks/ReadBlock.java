package semantic.sentence_entities.default_blocks;

import code_generator.CodeGenerator;
import semantic.sentence_entities.Block;

import static main.MainModule.writer;

public class ReadBlock extends Block {

    public ReadBlock() {
        super();
    }

    public void generateCode() throws Exception{
        writer.write(CodeGenerator.READ + " ; Lee el proximo byte del stream \n");
        writer.write(CodeGenerator.STORE + " 3 ; Guarda el valor de retorno \n");
    }
}
