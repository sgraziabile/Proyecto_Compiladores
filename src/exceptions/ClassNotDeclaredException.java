package exceptions;

public class ClassNotDeclaredException extends Exception {
  private int errorLine;
  private String lexeme;

  public ClassNotDeclaredException(int errorLine, String lexeme) {
    this.errorLine = errorLine;
    this.lexeme = lexeme;
  }
  public String getMessage() {
    String message = "Error Semantico en linea " + errorLine + ":";
    message += " La clase " + lexeme + " no ha sido declarada \n";
    message += "[Error:" + lexeme + "|" + errorLine + "]";
    return message;
  }
}
