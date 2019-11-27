import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
  static BufferedReader file;

  public static void main(String[] args) {
    try{
      System.setProperty("file.encoding", "UTF-8");
      String fileName = args[0];
      LexicalAnalyzer LA = new LexicalAnalyzer(new BufferedReader(new FileReader(fileName)));
      SyntaxAnalyzer SA = new SyntaxAnalyzer(LA);
      SA.parser();
    }catch (Exception e){
        System.err.println("ERRO: Não foi possível ler o arquivo.");
    }
  }

  // public static void readFile() {
  //   BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
  //   String name = "";

  //   try {
  //     System.out.print("Type the name of the file: ");
  //     name = input.readLine();

  //     if (name.length() > 0 && name.charAt(name.length() - 2) != '.' && name.charAt(name.length() - 1) != 'l') {
  //       System.err.println("Invalid name! Check the name and the extension of the file");
  //       readFile();
  //     } else {
  //       System.out.println("File found!");
  //       file = new BufferedReader(new FileReader(name));

  //       LexicalAnalyzer LA = new LexicalAnalyzer(file);
  //       SyntaxAnalyzer SA = new SyntaxAnalyzer(LA);
  //       SA.parser();
  //       LA.getSymbolTable().showSymbolsTable();

  //     }
  //   } catch (Exception e) {
  //     System.err.println("File not found");
  //     readFile();
  //   }
  // }
}