/**
 * ErrorHandler
 */
public class ErrorHandler {

    public static String INVALID_CHARACTER = "Unexpected character";
    public static String INVALID_LEXEME = "Unexpected lexeme";
    public static String END_OF_FILE = "Unexpected end of file";
    public static String INVALID_TOKEN = "Unexpected token";

    public static void Print(String message, long line, String lexeme){

        
        switch (message) {
            case "INVALID_CHARACTER":
                message = 
                break;
        
            default:
                break;
        }
        
        String menssageLexeme = lexeme == null ? "" : " [" + lexeme + "]";
        System.out.println(line + ":" + message + menssageLexeme);
        System.exit(0);
    }    
}