/**
 * @author Jorge Oliveira
 * @author Maria Clara Dias
 * @author Pedro Pimenta
 * 
 *         Class Symbol {@summary} Basic structure and features of a Symbol.
 */

public class Symbol {

    private byte token;
    private byte type;
    private int address;
    private String lexeme;

    public static final byte NO_TYPE = 0;
    public static final byte TYPE_INTEGER = 1;
    public static final byte TYPE_BYTE = 2;
    public static final byte TYPE_STRING = 3;

    public Symbol() {

    }

    public Symbol(byte token, String lexeme, byte type) {
        this.token = token;
        this.lexeme = lexeme;
        this.type = type;
    }

    public Symbol(byte token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
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
}