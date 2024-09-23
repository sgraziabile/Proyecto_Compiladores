package entities;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrimerosHandler {
    public static final Set<String> PrimitiveType = Set.of("keyword_boolean", "keyword_char", "keyword_int","keyword_float");
    public static final Set<String> Type = Stream.concat(
            PrimitiveType.stream(),
            Stream.of("idClase")
    ).collect(Collectors.toSet());
    public static final Set<String> MemberType = Stream.concat(
            Stream.of("keyword_void"),
            Type.stream()
    ).collect(Collectors.toSet());
    public static final Set<String> Access = Set.of("keyword_this","idMetVar","keyword_new","idClase","parentesisAbre");
    public static final Set<String> ObjectLiteral = Set.of("keyword_null","stringLiteral");
    public static final Set<String> PrimitiveLiteral = Set.of("keyword_true","keyword_false","intLiteral","charLiteral","floatLiteral");
    public static final Set<String> Literal = Stream.concat(
            PrimitiveLiteral.stream(),
            ObjectLiteral.stream()
    ).collect(Collectors.toSet());
    public static final Set<String> Operand = Stream.concat(
            Literal.stream(),
            Access.stream()
    ).collect(Collectors.toSet());
    public static final Set<String> UnaryOperator = Set.of("opNot","opMenos","opSuma");
    public static final Set<String> Expression = Stream.concat(
            Operand.stream(),
            UnaryOperator.stream()
    ).collect(Collectors.toSet());

    public static final Set<String> Sentence = Stream.concat(
            Expression.stream(),
            Set.of("puntoYComa","keyword_if","keyword_return","keyword_switch","keyword_break","keyword_var","keyword_while","llaveAbre","keyword_for").stream()
    ).collect(Collectors.toSet());

    public static final Set<String> BinaryOperator = Set.of("opSuma","opMenos","opMult","opDiv","opMod","opMenor","opMenorIgual","opMayor","opMayorIgual","opIgualdad","opDistinto","opAnd","opOr");

    public static final Set<String> Primary = Set.of("idMetVar","keyword_this","keyword_new","idClase","parentesisAbre");


    public PrimerosHandler() {

    }

}
