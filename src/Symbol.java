/**
 * @author Jorge Oliveira
 * @author Maria Clara Dias
 * @author Pedro Pimenta
 * 
 * Class Symbol
 * 
 * Basic structure and features of a Symbol.
 */

public class Symbol {

    private byte token;
    private byte type;
    private byte classType;
    private int address;
    private String lexeme;

    public static final byte CLASS_CONST = 0;
    public static final byte CLASS_VAR = 1;

    public static final byte TYPE_INTEGER = 0;
    public static final byte TYPE_BYTE = 1;
    public static final byte TYPE_STRING = 2;
    public static final byte TYPE_BOOLEAN = 3;

    public Symbol() {

    }

    public Symbol(byte token, String lexeme, byte type) {
        this.token = token;
        this.lexeme = lexeme;
        this.type = type;
        this.classType = -1;
    }

    public Symbol(byte token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
        this.classType = -1;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setToken(byte token) {
        this.token = token;
    }

    public void setAddress(int address) {
        this.address = type;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public byte getToken() {
        return token;
    }

    public byte getType() {
        return type;
    }

    public int getAddress() {
        return address;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setClassType(byte classType) {
        this.classType = classType;
    }

    public byte getClassType() {
        return this.classType;
    }
}