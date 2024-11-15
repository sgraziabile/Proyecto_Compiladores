package code_generator;

import static main.MainModule.writer;

public class CodeGenerator {
    public static String LOADFP = "LOADFP";
    public static String LOADSP = "LOADSP";
    public static String STOREFP = "STOREFP";
    public static String PUSH = "PUSH";
    public static String POP = "POP";
    public static String CALL = "CALL";
    public static String RET = "RET";
    public static String LOAD = "LOAD";
    public static String DUP = "DUP";
    public static String DW = "DW";

    public static String ADD = "ADD";
    public static String SUB = "SUB";
    public static String MUL = "MUL";
    public static String DIV = "DIV";
    public static String EQ = "EQ";
    public static String NE = "NE";  // !=
    public static String GT = "GT"; //>
    public static String LT = "LT"; //<
    public static String GE = "GE"; //>=
    public static String LE = "LE"; //<=
    public static String AND = "AND";
    public static String OR = "OR";
    public static String NOT = "NOT";
    public static String NEG = "NEG";
    public static String MOD = "MOD";

    public static String STORE = "STORE";
    public static String RMEM = "RMEM";
    public static String RMEM1 = "RMEM 1";
    public static String SWAP = "SWAP";
    public static String LOADREF = "LOADREF";
    public static String STOREREF = "STOREREF";

    public static String JUMP = "JUMP";
    public static String BF = "BF";
    public static String BT = "BT";
    public static String NOP = "NOP";

    public static String READ = "READ";
    public static String IPRINT = "IPRINT";
    public static String BPRINT = "BPRINT";
    public static String CPRINT = "CPRINT";
    public static String SPRINT = "SPRINT";
    public static String PRNLN = "PRNLN";

    private static int ifLabelCounter = 1;
    private static int whileLabelCounter = 1;
    private static int switchLabelCounter = 1;
    private static int caseLabelCounter;
    private static String currentLoopLabel;
    private static String currentDefaultLabel;

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
            writer.write("LOADHL\t; hl\n");
            writer.write("DUP\n");
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
    public static String generateEndIfLabel() {
        return "lblIfEnd" + ifLabelCounter++;
    }
    public static String generateElseLabel() {
        return "lblElse" + ifLabelCounter;
    }
    public static String generateEndWhileLabel() {
        String endLabel = "lblWhileEnd" + whileLabelCounter++;
        currentLoopLabel = endLabel;
        return endLabel;
    }
    public static String generateWhileLabel() {
        return "lblWhile" + whileLabelCounter;
    }
    public static String generateCaseLabel() {
        String label = "lblSwitchCase" + switchLabelCounter + caseLabelCounter;
        caseLabelCounter++;
        return label;
    }
    public static String generateDefaultLabel() {
        caseLabelCounter = 1;
        String label = "lblSwitchDefault" + switchLabelCounter;
        currentDefaultLabel = label;
        return label;
    }
    public static String generateEndSwitchLabel() {
        String label = "lblSwitchEnd" + switchLabelCounter;
        currentLoopLabel = label;
        return label;
    }
    public static String getCurrentLoopLabel() {
        return currentLoopLabel;
    }
    public static String getCurrentDefaultLabel() {
        return currentDefaultLabel;
    }

}
