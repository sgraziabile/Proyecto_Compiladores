package entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PrimerosHandler {
    private Map<String, Set<String>> primeros;

    public PrimerosHandler() {
        primeros = new HashMap<>();
        addPrimeros("ListaMiembros", Set.of("keyword_static", "keyword_boolean", "keyword_char", "keyword_int", "keyword_void"));
        addPrimeros("TipoMiemnros", Set.of("keyword_void","keyword_boolean", "keyword_char", "keyword_int"));
        addPrimeros("Tipo", Set.of("keyword_boolean", "keyword_char", "keyword_int"));
    }
    public void addPrimeros(String noTerminal, Set<String> newPrimeros) {
        this.primeros.putIfAbsent(noTerminal, new HashSet<>());
        Set<String> setOfPrimeros = this.primeros.get(noTerminal);
        setOfPrimeros.addAll(newPrimeros);
    }
    public Set<String> getPrimeros(String noTerminal) {
        return this.primeros.get(noTerminal);
    }

}
