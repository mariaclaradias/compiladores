import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {
  static BufferedReader file;

  public static void main(String[] args) {
    readFile();
    // SymbolTable table = new SymbolTable();

    // table.insertID("age", (byte) 1);
    // table.insertID("name", (byte) 3);

    // table.showSymbolsTable();

    // Symbol sym = tabela.searchLexeme("id");
    // System.out
    // .println("Token: " + sym.getToken() + ", Lexeme: " + sym.getLexeme()
    // + ", Type: " + sym.getType());
  }

  public static void readFile() {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String name = "";

    try {
      System.out.print("Type the name of the file: ");
      name = input.readLine();

      if (name.length() > 0 && name.charAt(name.length() - 2) != '.' && name.charAt(name.length() - 1) != 'l') {
        System.err.println("Invalid name! Check the name and the extension of the file");
        readFile();
      } else {
        System.out.println("File found!");
        file = new BufferedReader(new FileReader(name));

        LexicalAnalyzer LA = new LexicalAnalyzer(file);
        LA.AFD();
        System.out.println("Token: " + LA.getToken() + " " + LA.getLexeme());
        
      }
    } catch (Exception e) {
      System.err.println("File not found");
      readFile();
    }
  }
}