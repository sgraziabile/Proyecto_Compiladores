package semantic.expression_entities;

import code_generator.CodeGenerator;
import entities.Token;
import semantic.declared_entities.ReferenceType;
import semantic.declared_entities.Type;

import static main.MainModule.writer;

public class ObjectLiteralNode extends LiteralNode{

    private static String vtLabel = "lblVTString";
    private static String constructorLabel = "lblConstructor@String";
    public ObjectLiteralNode(Token value) {
        super(value);
    }
    public Type typeCheck() throws Exception {
        Type type;
        if(value.getLexeme().equals("null")) {
            type = new ReferenceType("null");
        } else {
            type = new ReferenceType("String");
        }
        return type;
    }
    public void generateCode() throws Exception{
        if(!value.getLexeme().equals("null")) {
            writer.write(CodeGenerator.RMEM1+ " ; Reserva espacio para el CIR generado\n");
            writer.write(CodeGenerator.PUSH + " 2 ; Reserva espacio el CIR String\n");
            writer.write(CodeGenerator.PUSH + " simple_malloc ;  Carga direccion de rutina para alojar el CIR\n");
            writer.write(CodeGenerator.CALL + " ; Llama a la rutina de alojamiento\n");
            writer.write(CodeGenerator.DUP + " ; Duplica la referencia al CIR\n");
            writer.write(CodeGenerator.PUSH + " "+ vtLabel + " ; Carga la direccion de la VT\n");
            writer.write(CodeGenerator.STOREREF+ " 0 ; Guarda la direccion de la VT en el CIR\n");
            writer.write(CodeGenerator.DUP + " ; Duplica la referencia al CIR\n");
            writer.write(CodeGenerator.PUSH + " "+ constructorLabel + " ; Carga la direccion del constructor\n");
            writer.write(CodeGenerator.CALL + " ; Llama al constructor\n");
            generateString();
            accessString();
        }
    }
    private void generateString() throws Exception {
        String str = value.getLexeme();
        str = str.replace("\"", "");
        CodeGenerator.addString(str);
        String label = CodeGenerator.getStringLabel(str);
        writer.write(CodeGenerator.DUP + " ; Duplica la referencia al CIR\n");
        writer.write(CodeGenerator.PUSH + " "+ label + " ; Carga la direccion del string\n");
        writer.write(CodeGenerator.STOREREF + " 1 ; Carga la etiqueta del string "+str+"\n");
    }
    private void accessString() throws Exception {
        writer.write(CodeGenerator.LOADREF + " 1 ; Obtiene el valor del string\n");
    }

}
