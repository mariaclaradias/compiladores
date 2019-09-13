import java.util.HashMap;

/**
 * @author Jorge Oliveira
 * @author Maria Clara Dias
 * @author Pedro Pimenta
 * 
 * SymbolTable
 * {@summary} Table containing all the Symbols, IDs and Addresses.
 */

public class SymbolTable {
    
    public final static enum Table {
        ID,
        CONST,
        INTEGER,
        BYTE,
        STRING,
        WHILE,
        IF,
        ELSE,
        AND,
        OR,
        NOT,
        EQUAL,
        EQUAL_TO,
        OPEN_PAR,
        CLOSE_PAR,
        LESS,
        GREATER,
        NOT_EQUAL,
        GREATER_EQUAL,
        LESS_EQUAL,
        COMMA,
        ADD,
        SUB,
        MULT,
        DIV,
        SEMICOLON,
        BEGIN,
        END,
        THEN,
        READLN,
        MAIN,
        WRITE,
        WRITELN,
        TRUE,
        FALSE,
        BOOLEAN;
    }
    
    public HashMap<String, Symbol> hashTable = new HashMap<>();
    
    public SymbolTable() {
        hashTable.put("id", new Symbol(Table.ID, "id"));
        hashTable.put("const", new Symbol(Table.CONST, "const"));
        hashTable.put("integer", new Symbol(Table.INTEGER, "integer"));
        hashTable.put("byte", new Symbol(Table.BYTE, "byte"));
        hashTable.put("string", new Symbol(Table.STRING, "string"));
        hashTable.put("while", new Symbol(Table.WHILE, "while"));
        hashTable.put("if", new Symbol(Table.IF, "if"));
        hashTable.put("else", new Symbol(Table.ELSE, "else"));
        hashTable.put("and", new Symbol(Table.AND, "and"));
        hashTable.put("or", new Symbol(Table.OR, "or"));
        hashTable.put("not", new Symbol(Table.NOT, "not"));
        hashTable.put("=", new Symbol(Table.EQUAL, "="));
        hashTable.put("==", new Symbol(Table.EQUAL_TO, "=="));
        hashTable.put("(", new Symbol(Table.OPEN_PAR, "("));
        hashTable.put(")", new Symbol(Table.CLOSE_PAR, ")"));
        hashTable.put("<", new Symbol(Table.LESS, "<"));
        hashTable.put(">", new Symbol(Table.GREATER, ">"));
        hashTable.put("!=", new Symbol(Table.NOT_EQUAL, "!="));
        hashTable.put(">=", new Symbol(Table.GREATER_EQUAL, ">="));
        hashTable.put("<=", new Symbol(Table.LESS_EQUAL, "<="));
        hashTable.put(",", new Symbol(Table.COMMA, ","));
        hashTable.put("+", new Symbol(Table.ADD, "+"));
        hashTable.put("-", new Symbol(Table.SUB, "-"));
        hashTable.put("*", new Symbol(Table.MULT, "*"));
        hashTable.put("/", new Symbol(Table.DIV, "/"));
        hashTable.put(";", new Symbol(Table.SEMICOLON, ";"));
        hashTable.put("begin", new Symbol(Table.BEGIN, "begin"));
        hashTable.put("end", new Symbol(Table.END, "end"));
        hashTable.put("then", new Symbol(Table.THEN, "then"));
        hashTable.put("readln", new Symbol(Table.READLN, "readln"));
        hashTable.put("main", new Symbol(Table.MAIN, "main"));
        hashTable.put("write", new Symbol(Table.WRITE, "write"));
        hashTable.put("writeln", new Symbol(Table.WRITELN, "writeln"));
        hashTable.put("true", new Symbol(Table.TRUE, "true"));
        hashTable.put("false", new Symbol(Table.FALSE, "false"));
        hashTable.put("boolean", new Symbol(Table.BOOLEAN, "boolean"));
    }
}