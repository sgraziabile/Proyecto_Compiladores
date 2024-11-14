package semantic.sentence_entities.default_blocks;

import code_generator.CodeGenerator;
import semantic.sentence_entities.Block;

import static main.MainModule.writer;

public class PrintBBlock extends Block {

    public PrintBBlock() {
        super();
    }

    public void generateCode() throws Exception{
        writer.write(CodeGenerator.LOAD+ " 3    ; Apila el parametro\n");
        writer.write(CodeGenerator.BPRINT+ "    ; Imprime el entero en el tope de la pila\n");
    }
}
