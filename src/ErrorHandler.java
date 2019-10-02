/**
 * ErrorHandler
 */
public class ErrorHandler {

    public static String INVALID_CHARACTER = "Unexpected character";
    public static String INVALID_LEXEME = "Unexpected lexeme";
    public static String END_OF_FILE = "Unexpected end of file";
    public static String INVALID_TOKEN = "Unexpected token";
    public static String READING_ERROR = "Reading error";
    public static String NO_COMMAND = "Expected at least one command in the main function";


    public static void print(String message, long line, String lexeme) {

        String messageLexeme = lexeme == null ? "" : " [" + lexeme + "]";
        System.out.println(line + ":" + message + messageLexeme);
        System.exit(0);
    }
}