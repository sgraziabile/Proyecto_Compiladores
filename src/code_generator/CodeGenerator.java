package code_generator;

import static main.MainModule.writer;

public class CodeGenerator {
    public static String LOADFP = "LOADFP";
    public static String LOADSP = "LOADSP";
    public static String STOREFP = "STOREFP";
    public static String PUSH = "PUSH";
    public static String CALL = "CALL";
    public static String RET = "RET";
    public static String LOAD = "LOAD";
    public static String IPRINT = "IPRINT";
    public static String DUP = "DUP";
    public static String ADD = "ADD";
    public static String STORE = "STORE";
    public static String RMEM = "RMEM";
    public static String RMEM1 = "RMEM 1";
    public static String SWAP = "SWAP";
    public static String LOADREF = "LOADREF";

    public void generateMain(String label) throws Exception {
        writer.write("PUSH " + label + "\n");
        writer.write(CALL+"\n");
    }

    public void setPrimitives() throws Exception {
            writer.write("simple_heap_init: RET 0\t; Retorna inmediatamente \n");
            writer.write("\n");
            writer.write("simple_malloc: LOADFP\t; Inicialización unidad\n");
            writer.write("LOADSP \n");
            writer.write("STOREFP ; Finaliza inicialización del RA\n");
            writer.write("LOADHL\t; hl");
            writer.write("DUP\t; hl\n");
            writer.write("PUSH 1\t; 1\n");
            writer.write("ADD\t; hl+1\n");
            writer.write("STORE 4 ; Guarda el resultado (un puntero a la primer celda de la región de memoria)\n");
            writer.write("LOAD 3\t; Carga la cantidad de celdas a alojar (parámetro que debe ser positivo)\n");
            writer.write("ADD\n");
            writer.write("STOREHL ; Mueve el heap limit (hl). Expande el heap\n");
            writer.write("STOREFP\n");
            writer.write("RET 1\t; Retorna eliminando el parámetro\n");
    }
    public void generateConstructorCode(String label) throws Exception{
        writer.write(label +":" +LOADFP + " ; Apila el valor del registro\n");
        writer.write(LOADSP + " ; Apila el valor en el registro sp\n");
        writer.write(STOREFP + " ; Almacena el tope de la pila en el registro\n");
    }
    public void generateMethodCode(String label) throws Exception {
        writer.write(label +":" +LOADFP + " ; Apila el valor del registro\n");
        writer.write(LOADSP + " ; Apila el valor en el registro sp\n");
        writer.write(STOREFP + " ; Almacena el tope de la pila en el registro\n");
    }

}
