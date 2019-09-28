import java.util.HashMap;

/**
 * @author Jorge Oliveira
 * @author Maria Clara Dias
 * @author Pedro Pimenta
 * 
 *         SymbolTable {@summary} Table containing all the Symbols, IDs and
 *         Addresses.
 */

public class SymbolTable {

    public static final byte ID = 0;
    public static final byte CONST = 1;
    public static final byte INTEGER = 2;
    public static final byte BYTE = 3;
    public static final byte STRING = 4;
    public static final byte BOOLEAN = 5;
    public static final byte WHILE = 6;
    public static final byte IF = 7;
    public static final byte ELSE = 8;
    public static final byte THEN = 9;
    public static final byte BEGIN = 10;
    public static final byte END = 11;
    public static final byte AND = 12;
    public static final byte OR = 13;
    public static final byte NOT = 14;
    public static final byte READLN = 15;
    public static final byte WRITE = 16;
    public static final byte WRITELN = 17;
    public static final byte TRUE = 18;
    public static final byte FALSE = 19;
    public static final byte MAIN = 20;
    public static final byte OPEN_PAR = 21;
    public static final byte CLOSE_PAR = 22;
    public static final byte LESS = 23;
    public static final byte GREATER = 24;
    public static final byte EQUAL_TO = 25;
    public static final byte NOT_EQUAL = 26;
    public static final byte GREATER_EQUAL = 27;
    public static final byte LESS_EQUAL = 28;
    public static final byte COMMA = 29;
    public static final byte ADD = 30;
    public static final byte SUB = 31;
    public static final byte EQUAL = 32;
    public static final byte MULT = 33;
    public static final byte DIV = 34;
    public static final byte SEMICOLON = 35;
    public static final byte APOSTROPHE = 36;

    public HashMap<String, Symbol> hashTable = new HashMap<>();

    public SymbolTable() {

        hashTable.put("id", new Symbol(ID, "id"));
        hashTable.put("const", new Symbol(CONST, "const"));
        hashTable.put("integer", new Symbol(INTEGER, "integer"));
        hashTable.put("byte", new Symbol(BYTE, "byte"));
        hashTable.put("string", new Symbol(STRING, "string"));
        hashTable.put("boolean", new Symbol(BOOLEAN, "boolean"));
        hashTable.put("while", new Symbol(WHILE, "while"));
        hashTable.put("if", new Symbol(IF, "if"));
        hashTable.put("else", new Symbol(ELSE, "else"));
        hashTable.put("then", new Symbol(THEN, "then"));
        hashTable.put("begin", new Symbol(BEGIN, "begin"));
        hashTable.put("end", new Symbol(END, "end"));
        hashTable.put("and", new Symbol(AND, "and"));
        hashTable.put("or", new Symbol(OR, "or"));
        hashTable.put("not", new Symbol(NOT, "not"));
        hashTable.put("readln", new Symbol(READLN, "readln"));
        hashTable.put("write", new Symbol(WRITE, "write"));
        hashTable.put("writeln", new Symbol(WRITELN, "writeln"));
        hashTable.put("true", new Symbol(TRUE, "true"));
        hashTable.put("false", new Symbol(FALSE, "false"));
        hashTable.put("main", new Symbol(MAIN, "main"));
        hashTable.put("(", new Symbol(OPEN_PAR, "("));
        hashTable.put(")", new Symbol(CLOSE_PAR, ")"));
        hashTable.put("<", new Symbol(LESS, "<"));
        hashTable.put(">", new Symbol(GREATER, ">"));
        hashTable.put("==", new Symbol(EQUAL_TO, "=="));
        hashTable.put("!=", new Symbol(NOT_EQUAL, "!="));
        hashTable.put(">=", new Symbol(GREATER_EQUAL, ">="));
        hashTable.put("<=", new Symbol(LESS_EQUAL, "<="));
        hashTable.put(",", new Symbol(COMMA, ","));
        hashTable.put("+", new Symbol(ADD, "+"));
        hashTable.put("-", new Symbol(SUB, "-"));
        hashTable.put("=", new Symbol(EQUAL, "="));
        hashTable.put("*", new Symbol(MULT, "*"));
        hashTable.put("/", new Symbol(DIV, "/"));
        hashTable.put(";", new Symbol(SEMICOLON, ";"));
        hashTable.put(";", new Symbol(APOSTROPHE, "''"));
    }

}