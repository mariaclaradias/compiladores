/**
 * @author Jorge Oliveira
 * @author Maria Clara Dias
 * @author Pedro Pimenta
 * 
 * Class LexicalAnalyzer
 * 
 * Handles the identification of language tokens.
 */

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
    private int finalState;
    private byte type;
    private final List<Character> ALLOWED_CHARACTERS = new ArrayList<Character>(
            Arrays.asList('\n', ',', '.', '_', ' ', ';', '&', ':', '(', ')', '[', ']', '{', '}', '-', '+', '\'', '"',
                    '*', '/', '!', '?', '>', '<', '=', '\t', '\r'));
    private final List<Character> IGNORED_CHARACTERS = new ArrayList<Character>(Arrays.asList('\n', '\t', ' ', '\r'));

    public LexicalAnalyzer(BufferedReader reader) {
        this.reader = reader;
        this.endOfFile = false;
        this.currentLine = 1;
        this.table = new SymbolTable();
        this.shouldReturnCharacter = false;
        this.nextState = 0;
        this.type = 0;
        this.finalState = 14;
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public byte getToken() {
        return this.symbol.getToken();
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public SymbolTable getSymbolTable() {
        return this.table;
    }

    public int getCurrentLine() {
        return this.currentLine;
    }

    public boolean isEndOfFile() {
        return this.endOfFile;
    }

    private void checkError() {
        if (this.ALLOWED_CHARACTERS.contains(this.currentCharacter)
                || Character.isLetterOrDigit(this.currentCharacter)) {
            return;
        } else if ((int) this.currentCharacter == 65535) {            
            this.nextState = this.finalState;
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_CHARACTER, this.currentLine, ""+this.currentCharacter);
        }
    }

    private void checkLexeme() {
        if (!this.endOfFile) {
            if (this.table.searchLexeme(lexeme) == null) {

                if (this.lexeme.charAt(0) == '\'' || Character.isDigit(this.lexeme.charAt(0))) {
                    this.symbol = table.insertValue(this.lexeme, this.type);
                } else {
                    this.symbol = table.insertID(this.lexeme, this.type);
                }
            } else if (this.lexeme.equals("false") || this.lexeme.equals("true")) {
                this.type = Symbol.TYPE_BOOLEAN;
                this.symbol = table.insertValue(this.lexeme, this.type);
            } else {
                this.symbol = this.table.searchLexeme(lexeme);
            }
            // System.exit(0);
        } else {
            this.symbol = new Symbol(SymbolTable.EOF, "EOF");
        }
    }

    private void checkReturn() {
        try {
            if (this.shouldReturnCharacter) {
                this.shouldReturnCharacter = false;
            } else {
                this.currentCharacter = (char) reader.read();
            }
        } catch (Exception e) {
            ErrorHandler.print(ErrorHandler.INVALID_CHARACTER, this.currentLine, this.lexeme);
        }
        checkError();
    }

    private void updateLexeme() {
        this.lexeme += this.currentCharacter;
    }

    public void AFD() {
        this.lexeme = "";

        while (this.nextState != this.finalState) {
            switch (this.nextState) {
            case 0: state_00();
                break;
            case 1: state_01();
                break;
            case 2: state_02();
                break;
            case 3: state_03();
                break;
            case 4: state_04();
                break;
            case 5: state_05();
                break;
            case 6: state_06();
                break;
            case 7: state_07();
                break;
            case 8: state_08();
                break;
            case 9: state_09();
                break;
            case 10: state_10();
                break;
            case 11: state_11();
                break;
            case 12: state_12();
                break;
            case 13: state_13();
                break;
            default:
                break;
            }
        }

        this.nextState = 0;
        checkLexeme();
        if ((int)this.currentCharacter == 65535){
            this.endOfFile = true;
        }
    }

    private void state_00() {
        this.lexeme = "";

        checkReturn();

        if (Character.isDigit(this.currentCharacter) && this.currentCharacter != '0') {
            this.nextState = 7;
        } else if (Character.isLetter(this.currentCharacter)) {
            this.nextState = 1;
        } else if (this.currentCharacter == '0') {
            this.nextState = 2;
        } else if (this.currentCharacter == '\'') {
            this.nextState = 3;
        } else if (this.currentCharacter == '<' || this.currentCharacter == '>' || this.currentCharacter == '=') {
            this.nextState = 9;
        } else if (this.currentCharacter == '/') {
            this.nextState = 4;
        } else if (this.currentCharacter == '!') {
            this.nextState = 12;
        } else if (this.currentCharacter == '_') {
            this.nextState = 13;
        } else if (this.IGNORED_CHARACTERS.contains(this.currentCharacter)) {
            if (this.currentCharacter == '\r') {
                this.currentLine++;
            }
            this.nextState = 0;
        } else {
            this.nextState = this.finalState;
        }
        updateLexeme();
    }

    private void state_01() {
        checkReturn();

        if (Character.isLetterOrDigit(this.currentCharacter) || this.currentCharacter == '_') {
            this.nextState = 1;
            updateLexeme();
        } else {
            this.shouldReturnCharacter = true;
            this.nextState = this.finalState;
        }

    }

    private void state_02() {
        checkReturn();

        if (Character.isDigit(this.currentCharacter)) {
            this.nextState = 7;
            updateLexeme();
        } else if (Character.isLetter(this.currentCharacter)) {
            if (this.currentCharacter == 'h' || this.currentCharacter == 'H') {
                this.nextState = 10;
                updateLexeme();
            }
        } else if (this.ALLOWED_CHARACTERS.contains(this.currentCharacter)) {
            this.type = Symbol.TYPE_INTEGER;
            this.shouldReturnCharacter = true;
            this.nextState = this.finalState;
        }
    }

    private void state_03() {
        checkReturn();

        if (this.currentCharacter == '\'') {
            this.nextState = 8;
        } else if ((int)this.currentCharacter == 65535) {
            ErrorHandler.print(ErrorHandler.END_OF_FILE, this.currentLine, "");
        } else {
            this.nextState = 3;
        }
        updateLexeme();
    }

    private void state_04() {
        checkReturn();

        if (this.currentCharacter == '*') {
            this.nextState = 5;
            updateLexeme();
        } else {
            this.shouldReturnCharacter = true;
            this.nextState = this.finalState;
        }
    }

    private void state_05() {
        checkReturn();

        if (this.currentCharacter == '*') {
            this.nextState = 6;
        } else if ((int)this.currentCharacter == 65535) {
            ErrorHandler.print(ErrorHandler.END_OF_FILE, this.currentLine, "");
        } else if (this.currentCharacter == '\n'){
            this.currentLine++;
            this.nextState = 5;
        } else {
            this.nextState = 5;
        }
        updateLexeme();
    }

    private void state_06() {
        checkReturn();

        if (this.currentCharacter == '*') {
            this.nextState = 6;
        } else if (this.currentCharacter == '/') {
            this.nextState = 0;
        } else if (this.currentCharacter == '\n'){
            this.currentLine++;
            this.nextState = 5;
        } else {
            this.nextState = 5;
        }
        updateLexeme();
    }

    private void state_07() {
        checkReturn();

        if (Character.isDigit(this.currentCharacter)) {
            this.nextState = 7;
            updateLexeme();
        } else if (Character.isLetter(this.currentCharacter) || this.currentCharacter == '_') {
            this.endOfFile = true;
        } else {
            this.type = Symbol.TYPE_INTEGER;
            this.shouldReturnCharacter = true;
            this.nextState = this.finalState;
        }
    }

    private void state_08() {
        checkReturn();

        if (this.currentCharacter == '\'') {
            this.nextState = 3;
            updateLexeme();
        } else {
            this.type = Symbol.TYPE_STRING;
            this.shouldReturnCharacter = true;
            this.nextState = this.finalState;
        }
    }

    private void state_09() {
        checkReturn();

        if (this.currentCharacter == '=') {
            updateLexeme();
            this.nextState = this.finalState;
        } else {
            this.shouldReturnCharacter = true;
            this.nextState = this.finalState;
        }
    }

    private void state_10() {
        checkReturn();

        if ((this.currentCharacter >= '0' && this.currentCharacter <= '9')
                || (this.currentCharacter >= 'a' && this.currentCharacter <= 'f')
                || (this.currentCharacter >= 'A' && this.currentCharacter <= 'F')) {
            this.nextState = 11;
        }
        updateLexeme();
    }

    private void state_11() {
        checkReturn();

        if ((this.currentCharacter >= '0' && this.currentCharacter <= '9')
                || (this.currentCharacter >= 'a' && this.currentCharacter <= 'f')
                || (this.currentCharacter >= 'A' && this.currentCharacter <= 'F')) {
            this.type = Symbol.TYPE_BYTE;
            this.nextState = this.finalState;
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, this.getCurrentLine(), this.getLexeme());
        }
    }

    private void state_12() {
        checkReturn();

        if (this.currentCharacter == '=') {
            this.nextState = this.finalState;
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_CHARACTER, this.currentLine, this.lexeme);
        }
    }

    private void state_13() {
        checkReturn();

        if (Character.isLetterOrDigit(this.currentCharacter)) {
            this.nextState = 1;
        } else if (this.currentCharacter == '_') {
            this.nextState = 13;
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_CHARACTER, this.currentLine, this.lexeme);
        }
    }

}