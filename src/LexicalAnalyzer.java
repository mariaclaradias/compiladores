import java.io.BufferedReader;
import java.util.*;

class LexicalAnalyzer {
    
    private BufferedReader reader;
    private boolean endOfFile;
    private int currentLine;
    
    private String lexeme;
    private SymbolTable table;
    private Symbol symbol;
    private boolean shouldReturnCharacter;
    private char currentCharacter;
    private int nextState;
    private final List<Character> ALLOWED_CHARACTERS = new ArrayList<Character>(Arrays.asList('\n', ',', '.', '_', ' ', ';', '&', ':', '(', ')', '[', ']', '{', '}', '-', '+', '\'', '"', '/', '!', '?', '>', '<', '='));

    public LexicalAnalyzer(BufferedReader reader){
        this.reader = reader;
        this.endOfFile = false;
        this.currentLine = 1;
        this.table = new SymbolTable();
        this.shouldReturnCharacter = false;
        this.nextState = 0;
    }

    private void checkReturn(){
        try {
            if (this.shouldReturnCharacter){
                this.shouldReturnCharacter = false;
            } else {
                this.currentCharacter = (char)reader.read();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    private Symbol AFD(){
        this.lexeme = "";

        while (this.nextState != 12){
            switch (this.nextState) {
                case 0:
                    state_00();                    
                    break;
                case 1:
                    state_01();                    
                    break;
                case 2:
                    state_02();                    
                    break;
                case 3:
                    state_03();                    
                    break;
                case 4:
                    state_04();                    
                    break;
                case 5:
                    state_05();                    
                    break;
                case 6:
                    state_06();                    
                    break;
                case 7:
                    state_07();                    
                    break;
                case 8:
                    state_08();                    
                    break;
                case 9:
                    state_09();                    
                    break;
                case 10:
                    state_10();                    
                    break;
                case 11:
                    state_11();                    
                    break;
                case 12:
                    state_12();                    
                    break;
                default:
                    break;
            }

        }

        ();
        return this.symbol;
    }

    private void state_00(){
        this.lexeme = "";

        checkReturn();

        if (Character.isDigit(this.currentCharacter)){
            this.nextState = 4;
        } else if (Character.isLetter(this.currentCharacter)){
            this.nextState = 1;
        } else if (this.currentCharacter == '_'){
            this.nextState = 2;
        } else if (this.currentCharacter == '<' || this.currentCharacter == '>' || this.currentCharacter == '='){
            this.nextState = 5;
        } else if (this.currentCharacter == '/'){
            this.nextState = 6;
        } else if (this.currentCharacter == ' '){
            this.nextState = 0;
        } else if (this.currentCharacter == '\n'){
            this.currentLine++;
        } else if (this.currentCharacter == null) {
            this.endOfFile = true;
        }

        this.lexeme += this.currentCharacter;
    }

    private void state_01(){
        this.lexeme = "";

        checkReturn();

        if (Character.isDigit(this.currentCharacter)) {
            this.lexeme += this.currentCharacter;
        } else if (Character.isLetter(this.currentCharacter)) {

        }

        this.lexeme += this.currentCharacter;
    }

    private void state_02(){
        checkReturn();

        if (currentCharacter == '_'){
            this.nextState = 2;
        } else if (Character.isLetterOrDigit(this.currentCharacter)){
            this.nextState = 3;
        }

        this.lexeme += this.currentCharacter;
    }

    private void state_03(){
        checkReturn();

        if (Character.isLetterOrDigit(this.currentCharacter) || currentCharacter == '_'){
            this.nextState = 3;
        } else {
            this.nextState = 12;
            shouldReturnCharacter = true;
        }

        this.lexeme += this.currentCharacter;
    }

    private void state_04(){
        checkReturn();

        if (Character.isDigit(this.currentCharacter)){
            this.nextState = 4;
        } else if (Character.isLetter(this.currentCharacter)){
            this.nextState = 1;
        }

        this.lexeme += this.currentCharacter;
    }

    private void state_05(){
        checkReturn();
        
        this.nextState = 12;

        if ((this.currentCharacter != '=')){
          this.shouldReturnCharacter = true;
        }

        this.lexeme += this.currentCharacter;
    }
    
    private void state_06(){
        checkReturn();
   
        if (this.currentCharacter != '*'){
            this.shouldReturnCharacter = true;
            this.nextState = 12;
        } else {
            this.nextState = 7;
        }

        this.lexeme += this.currentCharacter;
    }

    private void state_07(){
        checkReturn();
   
        if (this.currentCharacter != '*'){
            this.nextState = 7;
        } else if (this.currentCharacter == '*'){
            this.nextState = 8;
        }

        this.lexeme += this.currentCharacter;
    }

    private void state_08(){
        checkReturn();
   
        if (this.currentCharacter != '*' && this.currentCharacter != '/'){
            this.nextState = 7;
        } else if (this.currentCharacter == '*'){
            this.nextState = 8;
        } else if(this.currentCharacter == '/'){
            this.nextState = 0;
        }

        this.lexeme += this.currentCharacter;
    }

    private void state_09(){
        checkReturn();
   
        if (this.currentCharacter == '\''){
            this.nextState = 10;
        }

        this.lexeme += this.currentCharacter;
    }

    private void state_10(){
        checkReturn();
   
        if (this.currentCharacter == '\''){
            this.nextState = 11;
        } else if (ALLOWED_CHARACTERS.contains(this.currentCharacter) || 
                Character.isLetterOrDigit(this.currentCharacter)){
            this.nextState = 10;

        }
        this.lexeme += this.currentCharacter;
    }

    private void state_11(){
        checkReturn();
   
        if (this.currentCharacter == '\''){
            this.nextState = 12;
        } else if (ALLOWED_CHARACTERS.contains(this.currentCharacter) || 
                Character.isLetterOrDigit(this.currentCharacter)){
            this.nextState = 10;
        }
        this.lexeme += this.currentCharacter;
    }

    

}