/**
 * @author Jorge Oliveira
 * @author Maria Clara Dias
 * @author Pedro Pimenta
 * 
 * Syntax Analyzer
 * 
 * Handles the syntatic behaviour of the language.
 */

public class SyntaxAnalyzer {

    private LexicalAnalyzer lexicalAnalyzer;
    private Symbol symbol;

    public SyntaxAnalyzer(LexicalAnalyzer LA) {
        this.lexicalAnalyzer = LA;
        lexicalAnalyzer.AFD();
    }

    private void tokenMatch(byte expectedToken) {
        if (this.lexicalAnalyzer.getToken() == expectedToken) {
            this.symbol = lexicalAnalyzer.getSymbol();
            lexicalAnalyzer.AFD();
        } else {
            if (lexicalAnalyzer.isEndOfFile()) {
                ErrorHandler.print(ErrorHandler.END_OF_FILE, lexicalAnalyzer.getCurrentLine(), "");
            } else {
                ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
            }
        }
    }


    public void parser() {
        while (this.lexicalAnalyzer.getToken() == SymbolTable.CONST || this.lexicalAnalyzer.getToken() == SymbolTable.ID
                || this.lexicalAnalyzer.getToken() == SymbolTable.BOOLEAN || this.lexicalAnalyzer.getToken() == SymbolTable.BYTE
                || this.lexicalAnalyzer.getToken() == SymbolTable.STRING
                || this.lexicalAnalyzer.getToken() == SymbolTable.INTEGER) {
            instruction();
        }

        tokenMatch(SymbolTable.MAIN);

        do {
            command();
        } while (this.lexicalAnalyzer.getToken() == SymbolTable.WHILE || this.lexicalAnalyzer.getToken() == SymbolTable.IF
                || this.lexicalAnalyzer.getToken() == SymbolTable.READLN || this.lexicalAnalyzer.getToken() == SymbolTable.WRITE
                || this.lexicalAnalyzer.getToken() == SymbolTable.WRITELN || this.lexicalAnalyzer.getToken() == SymbolTable.ID);

        tokenMatch(SymbolTable.END);
    }

    private void instruction() {        
        Symbol id;
        boolean flagNegative=false, flagPositive=false;

        // TYPE id
        if (this.lexicalAnalyzer.getToken() == SymbolTable.INTEGER || this.lexicalAnalyzer.getToken() == SymbolTable.BOOLEAN
                || this.lexicalAnalyzer.getToken() == SymbolTable.BYTE || this.lexicalAnalyzer.getToken() == SymbolTable.STRING) {
            byte currentType = types();
            tokenMatch(SymbolTable.ID);
            id = this.symbol;
            // Regra 32
            if (this.symbol.getClassType() != -1) {
                ErrorHandler.print(ErrorHandler.DECLARED_ID, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            } else {
                this.symbol.setClassType(Symbol.CLASS_VAR);
                this.symbol.setType(currentType);
            }

            // TYPE id = [(+|-)] VALUE
            if (this.lexicalAnalyzer.getToken() == SymbolTable.EQUAL) {
                tokenMatch(SymbolTable.EQUAL);
                // Regra 9
                flagPositive = false;
                flagNegative = false;
                if (this.lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                    tokenMatch(SymbolTable.ADD);
                    // Regra 10
                    flagPositive = true;
                } else if (this.lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                    tokenMatch(SymbolTable.SUB);
                    // Regra 11
                    flagNegative = true;
                }
                
                tokenMatch(SymbolTable.VALUE);
                // Regra 27
                if (flagNegative == true) {
                    if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                    this.symbol.getType() != Symbol.TYPE_BYTE) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
                    this.symbol.setType(Symbol.TYPE_INTEGER);
                }
                if (flagPositive == true) {
                    if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                    this.symbol.getType() != Symbol.TYPE_BYTE) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
                }
                
                // Regra 28
                if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                this.symbol.getType() != Symbol.TYPE_BYTE &&
                this.symbol.getType() != Symbol.TYPE_STRING &&
                this.symbol.getType() != Symbol.TYPE_BOOLEAN) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
                
                // Regra 29
                if (id.getType() != this.symbol.getType()) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }            
            }

            // ...{, id [= [(+|-)] VALUE]}*;
            while (this.lexicalAnalyzer.getToken() == SymbolTable.COMMA) {
                tokenMatch(SymbolTable.COMMA);
                tokenMatch(SymbolTable.ID);
                id = this.symbol;
                
                // Regra 32
                if (this.symbol.getClassType() != -1) {
                    ErrorHandler.print(ErrorHandler.DECLARED_ID, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                } else {
                    this.symbol.setClassType(Symbol.CLASS_VAR);
                    this.symbol.setType(currentType);
                }
                
                // Regra 30
                id.setType(currentType);
                
                if (this.lexicalAnalyzer.getToken() == SymbolTable.EQUAL) {
                    tokenMatch(SymbolTable.EQUAL);
                    // Regra 9
                    flagPositive = false;
                    flagNegative = false;
                    if (this.lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                        tokenMatch(SymbolTable.ADD);
                        // Regra 10
                        flagPositive = true;
                    } else if (this.lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                        tokenMatch(SymbolTable.SUB);
                        // Regra 11
                        flagNegative = true;
                    }
                    tokenMatch(SymbolTable.VALUE);
                    // Regra 27
                    if (flagNegative == true) {
                        if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                        this.symbol.getType() != Symbol.TYPE_BYTE) {
                            ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                        }
                        this.symbol.setType(Symbol.TYPE_INTEGER);
                    }
                    if (flagPositive == true) {
                        if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                        this.symbol.getType() != Symbol.TYPE_BYTE) {
                            ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                        }
                    }
                    
                    // Regra 28
                    if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                    this.symbol.getType() != Symbol.TYPE_BYTE &&
                    this.symbol.getType() != Symbol.TYPE_STRING &&
                    this.symbol.getType() != Symbol.TYPE_BOOLEAN) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
                    
                    // Regra 29
                    if (id.getType() != this.symbol.getType()) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }     
                }
            }
            tokenMatch(SymbolTable.SEMICOLON);

            // CONST id = [(+|-)] VALUE;
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.CONST) {
            tokenMatch(SymbolTable.CONST);
            tokenMatch(SymbolTable.ID);            
            id = this.symbol;
            // Regra 25
            if (this.symbol.getClassType() != -1) {
                ErrorHandler.print(ErrorHandler.DECLARED_ID, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }else{
                this.symbol.setClassType(Symbol.CLASS_CONST);
            }

            tokenMatch(SymbolTable.EQUAL);
            // Regra 9
            flagPositive = false;
            flagNegative = false;

            if (this.lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                tokenMatch(SymbolTable.ADD);
                // Regra 10
                flagPositive = true;
            } else if (this.lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(SymbolTable.SUB);
                // Regra 11
                flagNegative = true;
            }
            tokenMatch(SymbolTable.VALUE);
            // Regra 27
            if (flagNegative == true) {
                if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                this.symbol.getType() != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
                this.symbol.setType(Symbol.TYPE_INTEGER);
            }
            if (flagPositive == true) {
                if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                this.symbol.getType() != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
            }
            
            // Regra 26
            id.setType(this.symbol.getType());
            
            // Regra 28
            if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
            this.symbol.getType() != Symbol.TYPE_BYTE &&
            this.symbol.getType() != Symbol.TYPE_STRING &&
            this.symbol.getType() != Symbol.TYPE_BOOLEAN) {
                ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }
            tokenMatch(SymbolTable.SEMICOLON);
            
        
            // id = [(+|-)] VALUE;
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.ID) {
            tokenMatch(SymbolTable.ID);
            id = this.symbol;
           
            // Regra 21
            if (this.symbol.getClassType() == -1) {
                ErrorHandler.print(ErrorHandler.ID_NOT_DECLARED, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }
            tokenMatch(SymbolTable.EQUAL);
            
            // Regra 33
            if(this.symbol.getClassType() == Symbol.CLASS_CONST){
                ErrorHandler.print(ErrorHandler.INCOMPATIBLE_CLASS, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }

            // Regra 9
            flagPositive = false;
            flagNegative = false;

            if (this.lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                tokenMatch(SymbolTable.ADD);
                // Regra 10
                flagPositive = true;
            } else if (this.lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(SymbolTable.SUB);
                // Regra 11
                flagNegative = true;
            }
            
            tokenMatch(SymbolTable.VALUE);
            // Regra 27
            if (flagNegative) {
                if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                this.symbol.getType() != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
                this.symbol.setType(Symbol.TYPE_INTEGER);
            }
            if (flagPositive) {
                if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                this.symbol.getType() != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
            }
            
            // Regra 28
            if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
            this.symbol.getType() != Symbol.TYPE_BYTE &&
            this.symbol.getType() != Symbol.TYPE_STRING &&
            this.symbol.getType() != Symbol.TYPE_BOOLEAN) {
                ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }
            
            // Regra 29
            if (id.getType() != this.symbol.getType()) {
                ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }    
            tokenMatch(SymbolTable.SEMICOLON);
        }
        else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
    }

    private void command() {
        Symbol id;
        boolean flagPositive=false, flagNegative=false;

        if (this.lexicalAnalyzer.getToken() == SymbolTable.WHILE) {
            loop();
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.IF) {
            test();
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.READLN) {
            read();
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.WRITE
                || this.lexicalAnalyzer.getToken() == SymbolTable.WRITELN) {
            write();
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.ID) {
            tokenMatch(SymbolTable.ID);
            id = this.symbol;

            // Regra 21
            if (this.symbol.getClassType() == -1) {
                ErrorHandler.print(ErrorHandler.ID_NOT_DECLARED, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }
            tokenMatch(SymbolTable.EQUAL);

            // Regra 33
            if(id.getClassType() == Symbol.CLASS_CONST){
                ErrorHandler.print(ErrorHandler.INCOMPATIBLE_CLASS, this.lexicalAnalyzer.getCurrentLine(), id.getLexeme());
            }

            // Regra 9
            flagPositive = false;
            flagNegative = false;
            if (this.lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                tokenMatch(SymbolTable.ADD);
                // Regra 10
                flagPositive = true;
            } else if (this.lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(SymbolTable.SUB);
                // Regra 11
                flagNegative = true;
            }
            byte currentType = expression();

            // Regra 24 
            if(flagPositive || flagNegative){
                if(currentType != Symbol.TYPE_INTEGER && currentType != Symbol.TYPE_BYTE){
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());     
                }
            }

            // Regra 31
            if( id.getType() != currentType){
                if(id.getType() != Symbol.TYPE_INTEGER || currentType != Symbol.TYPE_BYTE){
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), id.getLexeme());  
                }
            }
            tokenMatch(SymbolTable.SEMICOLON);
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.SEMICOLON) {
            tokenMatch(SymbolTable.SEMICOLON);
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, this.lexicalAnalyzer.getCurrentLine(), "");
        }
    }

    private void loop() {
        if (this.lexicalAnalyzer.getToken() == SymbolTable.WHILE) {
            tokenMatch(SymbolTable.WHILE);
            tokenMatch(SymbolTable.OPEN_PAR);
            byte currentType = expression();
            // Regra 23            
            if (currentType != Symbol.TYPE_BOOLEAN) {
                ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }
            tokenMatch(SymbolTable.CLOSE_PAR);
            loopCommands();
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
    }

    private void loopCommands() {
        if (this.lexicalAnalyzer.getToken() == SymbolTable.BEGIN) {
            tokenMatch(SymbolTable.BEGIN);
            while (this.lexicalAnalyzer.getToken() == SymbolTable.WHILE || this.lexicalAnalyzer.getToken() == SymbolTable.IF
                    || this.lexicalAnalyzer.getToken() == SymbolTable.READLN
                    || this.lexicalAnalyzer.getToken() == SymbolTable.WRITE
                    || this.lexicalAnalyzer.getToken() == SymbolTable.WRITELN
                    || this.lexicalAnalyzer.getToken() == SymbolTable.ID) {
                command();
            }
            tokenMatch(SymbolTable.END);
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.WHILE || this.lexicalAnalyzer.getToken() == SymbolTable.IF
                || this.lexicalAnalyzer.getToken() == SymbolTable.READLN || this.lexicalAnalyzer.getToken() == SymbolTable.WRITE
                || this.lexicalAnalyzer.getToken() == SymbolTable.WRITELN || this.lexicalAnalyzer.getToken() == SymbolTable.ID) {
            command();
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
    }

    private void test() {
        if (this.lexicalAnalyzer.getToken() == SymbolTable.IF) {
            tokenMatch(SymbolTable.IF);
            tokenMatch(SymbolTable.OPEN_PAR);
            byte currentType = expression();
            // Regra 23            
            if (currentType != Symbol.TYPE_BOOLEAN) {
                ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }
            tokenMatch(SymbolTable.CLOSE_PAR);
            tokenMatch(SymbolTable.THEN);
            loopCommands();
            if (this.lexicalAnalyzer.getToken() == SymbolTable.ELSE) {
                tokenMatch(SymbolTable.ELSE);
                loopCommands();
            }
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
    }

    private void read() {
        if (this.lexicalAnalyzer.getToken() == SymbolTable.READLN) {
            tokenMatch(SymbolTable.READLN);
            tokenMatch(SymbolTable.OPEN_PAR);
            tokenMatch(SymbolTable.ID);
            // Regra 21
            if (this.symbol.getClassType() == -1) {
                ErrorHandler.print(ErrorHandler.ID_NOT_DECLARED, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }
            // Regra 22
            if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                this.symbol.getType() != Symbol.TYPE_STRING &&
                this.symbol.getType() != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
            tokenMatch(SymbolTable.CLOSE_PAR);
            tokenMatch(SymbolTable.SEMICOLON);
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
    }

    private void write() {
        if (this.lexicalAnalyzer.getToken() == SymbolTable.WRITE) {
            tokenMatch(SymbolTable.WRITE);
            tokenMatch(SymbolTable.OPEN_PAR);
            byte currentType = expression();
            // Regra 20
            if (currentType != Symbol.TYPE_INTEGER &&
                currentType != Symbol.TYPE_STRING &&
                currentType != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
            while (this.lexicalAnalyzer.getToken() == SymbolTable.COMMA) {
                tokenMatch(SymbolTable.COMMA);
                currentType = expression();
                // Regra 20
                if (currentType != Symbol.TYPE_INTEGER &&
                    currentType != Symbol.TYPE_STRING &&
                    currentType != Symbol.TYPE_BYTE) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
            }
            tokenMatch(SymbolTable.CLOSE_PAR);
            tokenMatch(SymbolTable.SEMICOLON);

        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.WRITELN) {
            tokenMatch(SymbolTable.WRITELN);
            tokenMatch(SymbolTable.OPEN_PAR);
            byte currentType = expression();
            // Regra 20
            if (currentType != Symbol.TYPE_INTEGER &&
                currentType != Symbol.TYPE_STRING &&
                currentType != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
            while (this.lexicalAnalyzer.getToken() == SymbolTable.COMMA) {
                tokenMatch(SymbolTable.COMMA);
                currentType = expression();
                // Regra 20
                if (currentType != Symbol.TYPE_INTEGER &&
                    currentType != Symbol.TYPE_STRING &&
                    currentType != Symbol.TYPE_BYTE) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
            }
            tokenMatch(SymbolTable.CLOSE_PAR);
            tokenMatch(SymbolTable.SEMICOLON);
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
    }

    private byte expression() {
        byte currentType = -1;
        boolean flagEquals=false, flagBoolean=false;
        if (this.lexicalAnalyzer.getToken() == SymbolTable.ADD || this.lexicalAnalyzer.getToken() == SymbolTable.SUB
                || this.lexicalAnalyzer.getToken() == SymbolTable.NOT || this.lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR
                || this.lexicalAnalyzer.getToken() == SymbolTable.VALUE || this.lexicalAnalyzer.getToken() == SymbolTable.ID) {
            
            // Regra 16
            currentType = expressionPrecedence_3();
            // Regra 17
            flagEquals = false;
            if (this.lexicalAnalyzer.getToken() == SymbolTable.EQUAL_TO
                    || this.lexicalAnalyzer.getToken() == SymbolTable.NOT_EQUAL
                    || this.lexicalAnalyzer.getToken() == SymbolTable.GREATER
                    || this.lexicalAnalyzer.getToken() == SymbolTable.LESS
                    || this.lexicalAnalyzer.getToken() == SymbolTable.GREATER_EQUAL
                    || this.lexicalAnalyzer.getToken() == SymbolTable.LESS_EQUAL) {
                if (this.lexicalAnalyzer.getToken() == SymbolTable.EQUAL_TO) {
                    // Regra 18
                    flagEquals = true;
                }else{
                    flagBoolean = true;
                }
                tokenMatch(this.lexicalAnalyzer.getToken());
                byte exp2Type = expressionPrecedence_3();
                // Regra 19
                if ((currentType != Symbol.TYPE_INTEGER && currentType != Symbol.TYPE_BYTE) ||
                    (exp2Type != Symbol.TYPE_INTEGER && exp2Type != Symbol.TYPE_BYTE)) {
                    if (currentType != exp2Type) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
                    if (flagEquals == false && currentType != Symbol.TYPE_STRING) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
                }
                if(flagBoolean || flagEquals){
                    currentType = Symbol.TYPE_BOOLEAN;
                }
            }
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
        return currentType;

    }

    private byte expressionPrecedence_3() {
        byte currentType = -1;
        boolean flagPositive=false, flagNegative=false, flagBoolean=false;

        if (this.lexicalAnalyzer.getToken() == SymbolTable.ADD || this.lexicalAnalyzer.getToken() == SymbolTable.SUB
            || this.lexicalAnalyzer.getToken() == SymbolTable.NOT || this.lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR
            || this.lexicalAnalyzer.getToken() == SymbolTable.VALUE || this.lexicalAnalyzer.getToken() == SymbolTable.ID) {
            // Regra 9
            flagPositive = false;
            flagNegative = false;
            if (this.lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                tokenMatch(SymbolTable.ADD);
                // Regra 10
                flagPositive = true;
            } else if (this.lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(SymbolTable.SUB);
                // Regra 11
                flagNegative = true;
            }
            byte ex1Type = expressionPrecedence_2();
            // Regra 12
            if (flagPositive == true || flagNegative == true) {
                if (ex1Type != Symbol.TYPE_INTEGER && ex1Type != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                } else {
                    currentType = Symbol.TYPE_INTEGER;
                }
            } else {
                currentType = ex1Type;
            }
            while (this.lexicalAnalyzer.getToken() == SymbolTable.ADD || this.lexicalAnalyzer.getToken() == SymbolTable.SUB
                    || this.lexicalAnalyzer.getToken() == SymbolTable.OR) {
                // Regra 13
                flagBoolean = false;
                flagNegative = false;
                flagPositive = false;

                if (this.lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                    tokenMatch(SymbolTable.ADD);
                    // Regra 10
                    flagPositive = true;
                } else if (this.lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                    tokenMatch(SymbolTable.SUB);
                    // Regra 11
                    flagNegative = true;
                } else if (this.lexicalAnalyzer.getToken() == SymbolTable.OR) {
                    tokenMatch(SymbolTable.OR);
                    // Regra 14
                    flagBoolean = true;
                }

                byte ex2Type = expressionPrecedence_2();

                // Regra 15
                if (currentType == Symbol.TYPE_INTEGER && (ex2Type != Symbol.TYPE_BYTE && ex2Type != Symbol.TYPE_INTEGER)) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                }
                if (flagPositive != true && flagNegative != true) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                } else if (ex1Type != ex2Type) {
                    if((ex1Type != Symbol.TYPE_INTEGER && ex1Type != Symbol.TYPE_BYTE ) && (ex2Type != Symbol.TYPE_BYTE && ex2Type != Symbol.TYPE_INTEGER)){
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                    }else{
                        currentType = Symbol.TYPE_INTEGER;
                    }
                }

                if (ex2Type == Symbol.TYPE_BOOLEAN) {
                    if (flagBoolean == false) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                    }
                } else if (flagPositive == false) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                }
            }
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
        return currentType;
    }

    private byte expressionPrecedence_2() {
        byte currentType = -1;
        if (this.lexicalAnalyzer.getToken() == SymbolTable.NOT || this.lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR
                || this.lexicalAnalyzer.getToken() == SymbolTable.VALUE || this.lexicalAnalyzer.getToken() == SymbolTable.ID) {
            byte e1type = expressionPrecedence_1();
            // Regra 5
            currentType = e1type;
            while (this.lexicalAnalyzer.getToken() == SymbolTable.MULT || this.lexicalAnalyzer.getToken() == SymbolTable.DIV
                    || this.lexicalAnalyzer.getToken() == SymbolTable.AND) {
                if (this.lexicalAnalyzer.getToken() == SymbolTable.MULT || this.lexicalAnalyzer.getToken() == SymbolTable.DIV) {
                    // Regra 6
                    if (e1type != Symbol.TYPE_INTEGER && e1type != Symbol.TYPE_BYTE) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
                } else {
                    // Regra 7
                    if (e1type != Symbol.TYPE_BOOLEAN) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                    }
                }
                tokenMatch(this.lexicalAnalyzer.getToken());
                byte e2type = expressionPrecedence_1();
                // Regra 8
                if ((e1type == Symbol.TYPE_INTEGER || e1type == Symbol.TYPE_BYTE) && (e2type == Symbol.TYPE_INTEGER || e2type == Symbol.TYPE_BYTE)) {
                    currentType = Symbol.TYPE_INTEGER;
                } else if (e1type != e2type) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                }
            }
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
        return currentType;
    }

    private byte expressionPrecedence_1() {
        byte currentType = -1;
        if (this.lexicalAnalyzer.getToken() == SymbolTable.NOT) {
            tokenMatch(SymbolTable.NOT);
            byte e1type = expressionPrecedence_1();
            // Regra 4
            if (e1type != Symbol.TYPE_BOOLEAN) {
                ErrorHandler.print(ErrorHandler.INVALID_TYPE, lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            } else {
                currentType = e1type;
            }
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR) {
            tokenMatch(SymbolTable.OPEN_PAR);
            byte expType = expression();
            // Regra 3
            currentType = expType;
            tokenMatch(SymbolTable.CLOSE_PAR);
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.VALUE) {
            tokenMatch(SymbolTable.VALUE);
            // Regra 2
            currentType = this.symbol.getType();
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.ID) {
            tokenMatch(SymbolTable.ID);
            // Regra 1
            currentType = this.symbol.getType();
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
        return currentType;
    }

    private byte types() {
        if (this.lexicalAnalyzer.getToken() == SymbolTable.INTEGER) {
            tokenMatch(SymbolTable.INTEGER);
            // Regra 34
            return Symbol.TYPE_INTEGER;
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.BOOLEAN) {
            tokenMatch(SymbolTable.BOOLEAN);
            // Regra 35
            return Symbol.TYPE_BOOLEAN;
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.BYTE) {
            tokenMatch(SymbolTable.BYTE);
            // Regra 36
            return Symbol.TYPE_BYTE;
        } else if (this.lexicalAnalyzer.getToken() == SymbolTable.STRING) {
            tokenMatch(SymbolTable.STRING);
            // Regra 37
            return Symbol.TYPE_STRING;
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
        return -1;
    }

}