/**
 * SyntaxAnalyzer
 */

import java.io.*;
public class SyntaxAnalyzer {

    private LexicalAnalyzer lexicalAnalyzer;

    private SymbolTable symbolTable;
    private Symbol symbol;

    private BufferedReader reader;

    public SyntaxAnalyzer(){
        this.lexicalAnalyzer = new LexicalAnalyzer(reader);
        this.symbolTable = new SymbolTable();
    }


    private void tokenMatch(byte expectedToken){
        if(symbol.getToken() == expectedToken){
            symbol = lexicalAnalyzer.getSymbol();
        } else {
            if (lexicalAnalyzer.isEndOfFile()) {
                ErrorHandler.
            } else {
                // token n esperado
            }
        }
    }

    private void parser(){
        while(lexicalAnalyzer.getToken() == symbolTable.INTEGER){

        }
    }

    private void instruction(){}

    private void command(){}

    private void loop(){}

    private void loopCommands(){}

    private void test(){}
    
    private void read(){}

    private void write(){}

    private void expression(){}

    private void expressionPrecedence_3(){}

    private void expressionPrecedence_2(){}
    
    private void expressionPrecedence_1(){}

    private void types(){}

}