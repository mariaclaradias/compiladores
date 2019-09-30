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
    private final List<Character> ALLOWED_CHARACTERS = new ArrayList<Character>(Arrays.asList('\n', ',', '.', '_', ' ',
            ';', '&', ':', '(', ')', '[', ']', '{', '}', '-', '+', '\'', '"', '/', '!', '?', '>', '<', '='));

    public LexicalAnalyzer(BufferedReader reader) {
        this.reader = reader;
        this.endOfFile = false;
        this.currentLine = 1;
        this.table = new SymbolTable();
        this.shouldReturnCharacter = false;
        this.nextState = 0;
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    private void checkReturn() {
        try {
            if (this.shouldReturnCharacter) {
                this.shouldReturnCharacter = false;
            } else {
                this.currentCharacter = (char) reader.read();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void updateLexeme() {
        this.lexeme += this.currentCharacter;
    }

    private void AFD() {
        this.lexeme = "";

        while (this.nextState != 12) {
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
            case 13:
                state_13();
                break;
            case 14:
                state_14();
                break;
            default:
                break;
            }
        }

        this.symbol = new Symbol();
    }

    private void state_00() {
        this.lexeme = "";

        checkReturn();

        if (Character.isDigit(this.currentCharacter)) {
            this.nextState = 4;
        } else if (Character.isLetter(this.currentCharacter)) {
            this.nextState = 1;
        }

        if (ALLOWED_CHARACTERS.contains(this.currentCharacter)) {
            if (this.currentCharacter == '_') {
                this.nextState = 2;
            } else if (this.currentCharacter == '<' || this.currentCharacter == '>' || this.currentCharacter == '=') {
                this.nextState = 5;
            } else if (this.currentCharacter == '/') {
                this.nextState = 6;
            } else if (this.currentCharacter == ' ') {
                this.nextState = 0;
            } else if (this.currentCharacter == '\n') {
                this.currentLine++;
            } else if (this.currentCharacter == '\'') {
                this.nextState = 9;
            } else if (this.currentCharacter == '!') {
                this.nextState = 12;
            } else {
                this.nextState = 15;
            }
        }
        updateLexeme();
    }

    private void state_01() {
        this.lexeme = "";

        checkReturn();

        if (Character.isDigit(this.currentCharacter)) {
            updateLexeme();
        } else if (Character.isLetter(this.currentCharacter)) {

        }
    }

    private void state_02() {
        checkReturn();

        if (currentCharacter == '_') {
            this.nextState = 2;
        } else if (Character.isLetterOrDigit(this.currentCharacter)) {
            this.nextState = 3;
        }
        updateLexeme();
    }

    private void state_03() {
        checkReturn();

        if (Character.isLetterOrDigit(this.currentCharacter) || currentCharacter == '_') {
            this.nextState = 3;
        } else {
            shouldReturnCharacter = true;
            this.nextState = 15;
        }
        updateLexeme();
    }

    private void state_04() {
        checkReturn();

        if (Character.isDigit(this.currentCharacter)) {
            this.nextState = 4;
        } else if (Character.isLetter(this.currentCharacter)) {
            if (this.currentCharacter == 'h' || this.currentCharacter == 'H') {
                this.nextState = 13;
            } else {
                this.nextState = 1;
            }
        }
        updateLexeme();
    }

    private void state_05() {
        checkReturn();

        this.nextState = 15;

        if ((this.currentCharacter != '=')) {
            this.shouldReturnCharacter = true;
        }
        updateLexeme();
    }

    private void state_06() {
        checkReturn();

        if (this.currentCharacter != '*') {
            this.shouldReturnCharacter = true;
            this.nextState = 15;
        } else {
            this.nextState = 7;
        }
        updateLexeme();
    }

    private void state_07() {
        checkReturn();

        if (this.currentCharacter != '*') {
            this.nextState = 7;
        } else if (this.currentCharacter == '*') {
            this.nextState = 8;
        }
        updateLexeme();
    }

    private void state_08() {
        checkReturn();

        if (this.currentCharacter != '*' && this.currentCharacter != '/') {
            this.nextState = 7;
        } else if (this.currentCharacter == '*') {
            this.nextState = 8;
        } else if (this.currentCharacter == '/') {
            this.nextState = 0;
        }
        updateLexeme();
    }

    private void state_09() {
        checkReturn();

        if (this.currentCharacter == '\'') {
            this.nextState = 10;
        }
        updateLexeme();
    }

    private void state_10() {
        checkReturn();

        if (this.currentCharacter == '\'') {
            this.nextState = 11;
        } else if (ALLOWED_CHARACTERS.contains(this.currentCharacter)
                || Character.isLetterOrDigit(this.currentCharacter)) {
            this.nextState = 10;

        }
        updateLexeme();
    }

    private void state_11() {
        checkReturn();

        if (this.currentCharacter == '\'') {
            this.nextState = 15;
        } else if (ALLOWED_CHARACTERS.contains(this.currentCharacter)
                || Character.isLetterOrDigit(this.currentCharacter)) {
            this.nextState = 10;
        }
        updateLexeme();
    }

    private void state_12() {
        checkReturn();

        if (this.currentCharacter == '=') {
            this.nextState = 15;
        }
        updateLexeme();
    }

    private void state_13() {
        checkReturn();

        if (Character.isDigit(this.currentCharacter) || this.currentCharacter > 'A' && this.currentCharacter < 'F') {
            this.nextState = 14;
        }
        updateLexeme();
    }

    private void state_14() {
        checkReturn();

        if (Character.isDigit(this.currentCharacter) || this.currentCharacter > 'A' && this.currentCharacter < 'F') {
            this.nextState = 15;
        }
        updateLexeme();
    }

}