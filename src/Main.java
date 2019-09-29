import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {
  BufferedReader arquivo;

  public static void main(String[] args) {
    SymbolTable tabela = new SymbolTable();

    tabela.insertID("idade", (byte) 1);
    tabela.insertID("nome", (byte) 3);

    tabela.showSymbolsTable();

    Symbol simbolo = tabela.searchLexeme("esse");
    System.out
        .println("Token: " + simbolo.getToken() + ", Lexema: " + simbolo.getLexeme() + ", tipo: " + simbolo.getType());
  }

  public void leituraArquivo() {
    BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
    String nome = "";

    try {
      System.out.print("Digite o nome do arquivo a ser lido: ");
      nomeArquivo = entrada.readLine();

      if (nome.length() > 0 && nome.charAt(nome.length() - 2) != '.' && nome.charAt(nome.length() - 1) != 'l') {
        System.err.println("Esse arquivo não é válido");
      } else {
        arquivo = new BufferedReader(new FileReader(file));
      }
    } catch (Exception e) {
      System.err.println("Arquivo não encontrado");
      leituraArquivo();
    }
  }
}