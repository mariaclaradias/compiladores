/**
 * @author Jorge Oliveira
 * @author Maria Clara Dias
 * @author Pedro Pimenta
 * 
 * Class ErrorHandler
 * 
 * Handles the error messages.
 */

public class ErrorHandler {

    public static String INVALID_CHARACTER = "caractere invalido";
    public static String INVALID_LEXEME = "lexema nao identificado";
    public static String END_OF_FILE = "fim de arquivo nao esperado";
   
    public static void print(String message, long line, String lexeme) {

        String messageLexeme = lexeme == null ? "." : " [" + lexeme + "].";
        System.out.println(line + ": " + message + messageLexeme);
        System.exit(0);
    }
}