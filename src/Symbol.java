/**
 * @author Jorge Oliveira
 * @author Maria Clara Dias
 * @author Pedro Pimenta
 * 
 * Class Symbol
 * {@summary} Basic structure and features of a Symbol.
 */

public class Symbol {

    private byte token;
    private byte type;
    private byte category;
    private int size;
    private int address;
    private String lexeme;

    public static final enum Category {
        NONE, 
        CONST;
    }

    public static final enum Type {
        NONE,
        INTEGER,
        BYTE,
        STRING,
        BOOL;
    }
    
    public byte getToken() {
        return token;
    }
    
    public byte getCategory(){
        return category;
    }

    public void setCategory(byte category){
        this.category = category;
    }

    public byte getType(){
        return type;
    }

    public void setType(byte type){
        this.type = type;
    }

    public void setToken(byte token) {
        this.token = token;
    }

    public int getSize() {
        return size;
    }

    public void setSize(byte size){
        this.size = size;
    }

    public int getAddress(){
        return address;
    }

    public void setAddress(int address){
        this.address = type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public Symbol(){

    }

    public Symbol(byte token, String lexeme){
        this.token = token;
        this.lexeme = lexeme;
    }

    public Symbol(byte token, String lexeme, byte type, int size){
        this.token = token;
        this.lexeme = lexeme;
        this.type = type;
        this.category = Category.NONE;
        this.size = size;
    }
}