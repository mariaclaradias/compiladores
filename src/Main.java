public class Main {
  public static void main(String[] args) {
    SymbolTable tabela = new SymbolTable();

    tabela.insertID("idade", (byte) 1);
    tabela.insertID("nome", (byte) 3);

    tabela.showSymbolsTable();

    Symbol simbolo = tabela.searchLexeme("nome");
    System.out
        .println("Token: " + simbolo.getToken() + ", Lexema: " + simbolo.getLexeme() + ", tipo: " + simbolo.getType());
  }
}