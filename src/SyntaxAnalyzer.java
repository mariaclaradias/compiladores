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
    private boolean flagPositive;
    private boolean flagNegative;
    private boolean flagBoolean;
    private boolean flagEquals;

    public SyntaxAnalyzer(LexicalAnalyzer LA) {
        this.lexicalAnalyzer = LA;
        lexicalAnalyzer.AFD();
    }

    private void tokenMatch(byte expectedToken) {
        if (lexicalAnalyzer.getToken() == expectedToken) {
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
        while (lexicalAnalyzer.getToken() == SymbolTable.CONST || lexicalAnalyzer.getToken() == SymbolTable.ID
                || lexicalAnalyzer.getToken() == SymbolTable.BOOLEAN || lexicalAnalyzer.getToken() == SymbolTable.BYTE
                || lexicalAnalyzer.getToken() == SymbolTable.STRING
                || lexicalAnalyzer.getToken() == SymbolTable.INTEGER) {
            instruction();
        }

        tokenMatch(SymbolTable.MAIN);

        do {
            command();
        } while (lexicalAnalyzer.getToken() == SymbolTable.WHILE || lexicalAnalyzer.getToken() == SymbolTable.IF
                || lexicalAnalyzer.getToken() == SymbolTable.READLN || lexicalAnalyzer.getToken() == SymbolTable.WRITE
                || lexicalAnalyzer.getToken() == SymbolTable.WRITELN || lexicalAnalyzer.getToken() == SymbolTable.ID);

        tokenMatch(SymbolTable.END);
    }

    private void instruction() {        
        Symbol id;
        // TYPE id
        if (lexicalAnalyzer.getToken() == SymbolTable.INTEGER || lexicalAnalyzer.getToken() == SymbolTable.BOOLEAN
                || lexicalAnalyzer.getToken() == SymbolTable.BYTE || lexicalAnalyzer.getToken() == SymbolTable.STRING) {
            byte currentType = types();
            tokenMatch(SymbolTable.ID);
            id = this.symbol;
            // Regra 25
            if (this.symbol.getClassType() != -1) {
                ErrorHandler.print(ErrorHandler.DECLARED_ID, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            } else {
                this.symbol.setClassType(Symbol.CLASS_CONST);
                this.symbol.setType(currentType);
            }

            // TYPE id = [(+|-)] VALUE
            if (lexicalAnalyzer.getToken() == SymbolTable.EQUAL) {
                tokenMatch(SymbolTable.EQUAL);
                // Regra 9
                this.flagPositive = false;
                this.flagNegative = false;
                if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                    tokenMatch(SymbolTable.ADD);
                    // Regra 10
                    this.flagPositive = true;
                } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                    tokenMatch(SymbolTable.SUB);
                    // Regra 11
                    this.flagNegative = true;
                }
                
                tokenMatch(SymbolTable.VALUE);
                // Regra 27
                if (this.flagNegative == true) {
                    if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                    this.symbol.getType() != Symbol.TYPE_BYTE) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
                    this.symbol.setType(Symbol.TYPE_INTEGER);
                }
                if (this.flagPositive == true) {
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
            while (lexicalAnalyzer.getToken() == SymbolTable.COMMA) {
                tokenMatch(SymbolTable.COMMA);
                tokenMatch(SymbolTable.ID);
                id = this.symbol;
                
                // Regra 25
                if (this.symbol.getClassType() != -1) {
                    ErrorHandler.print(ErrorHandler.DECLARED_ID, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
                
                // Regra 30
                id.setType(currentType);
                

                if (lexicalAnalyzer.getToken() == SymbolTable.EQUAL) {
                    tokenMatch(SymbolTable.EQUAL);
                    // Regra 9
                    this.flagPositive = false;
                    this.flagNegative = false;
                    if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                        tokenMatch(SymbolTable.ADD);
                        // Regra 10
                        this.flagPositive = true;
                    } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                        tokenMatch(SymbolTable.SUB);
                        // Regra 11
                        this.flagNegative = true;
                    }
                    tokenMatch(SymbolTable.VALUE);
                    // Regra 27
                    if (this.flagNegative == true) {
                        if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                        this.symbol.getType() != Symbol.TYPE_BYTE) {
                            ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                        }
                        this.symbol.setType(Symbol.TYPE_INTEGER);
                    }
                    if (this.flagPositive == true) {
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
        } else if (lexicalAnalyzer.getToken() == SymbolTable.CONST) {
            tokenMatch(SymbolTable.CONST);
            tokenMatch(SymbolTable.ID);            
            id = this.symbol;
            // Regra 25
            if (this.symbol.getClassType() != -1) {
                ErrorHandler.print(ErrorHandler.DECLARED_ID, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }
            tokenMatch(SymbolTable.EQUAL);
            // Regra 9
            this.flagPositive = false;
            this.flagNegative = false;

            if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                tokenMatch(SymbolTable.ADD);
                // Regra 10
                this.flagPositive = true;
            } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(SymbolTable.SUB);
                // Regra 11
                this.flagNegative = true;
            }
            tokenMatch(SymbolTable.VALUE);
            // Regra 27
            if (this.flagNegative == true) {
                if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                this.symbol.getType() != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
                this.symbol.setType(Symbol.TYPE_INTEGER);
            }
            if (this.flagPositive == true) {
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
        } else if (lexicalAnalyzer.getToken() == SymbolTable.ID) {
            tokenMatch(SymbolTable.ID);
            id = this.symbol;
            // Regra 21
            if (this.symbol.getClassType() == -1) {
                ErrorHandler.print(ErrorHandler.ID_NOT_DECLARED, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            }
            tokenMatch(SymbolTable.EQUAL);
            // Regra 9
            this.flagPositive = false;
            this.flagNegative = false;

            if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                tokenMatch(SymbolTable.ADD);
                // Regra 10
                this.flagPositive = true;
            } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(SymbolTable.SUB);
                // Regra 11
                this.flagNegative = true;
            }
            
            tokenMatch(SymbolTable.VALUE);
            // Regra 27
            if (this.flagNegative == true) {
                if (this.symbol.getType() != Symbol.TYPE_INTEGER &&
                this.symbol.getType() != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
                this.symbol.setType(Symbol.TYPE_INTEGER);
            }
            if (this.flagPositive == true) {
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
        if (lexicalAnalyzer.getToken() == SymbolTable.WHILE) {
            loop();
        } else if (lexicalAnalyzer.getToken() == SymbolTable.IF) {
            test();
        } else if (lexicalAnalyzer.getToken() == SymbolTable.READLN) {
            read();
        } else if (lexicalAnalyzer.getToken() == SymbolTable.WRITE
                || lexicalAnalyzer.getToken() == SymbolTable.WRITELN) {
            write();
        } else if (lexicalAnalyzer.getToken() == SymbolTable.ID) {
            tokenMatch(SymbolTable.ID);
            tokenMatch(SymbolTable.EQUAL);
            if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                tokenMatch(SymbolTable.ADD);
            } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(SymbolTable.SUB);
            }
            expression();
            tokenMatch(SymbolTable.SEMICOLON);
        } else if (lexicalAnalyzer.getToken() == SymbolTable.SEMICOLON) {
            tokenMatch(SymbolTable.SEMICOLON);
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, this.lexicalAnalyzer.getCurrentLine(), "");
        }
    }

    private void loop() {
        if (lexicalAnalyzer.getToken() == SymbolTable.WHILE) {
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
        if (lexicalAnalyzer.getToken() == SymbolTable.BEGIN) {
            tokenMatch(SymbolTable.BEGIN);
            while (lexicalAnalyzer.getToken() == SymbolTable.WHILE || lexicalAnalyzer.getToken() == SymbolTable.IF
                    || lexicalAnalyzer.getToken() == SymbolTable.READLN
                    || lexicalAnalyzer.getToken() == SymbolTable.WRITE
                    || lexicalAnalyzer.getToken() == SymbolTable.WRITELN
                    || lexicalAnalyzer.getToken() == SymbolTable.ID) {
                command();
            }
            tokenMatch(SymbolTable.END);
        } else if (lexicalAnalyzer.getToken() == SymbolTable.WHILE || lexicalAnalyzer.getToken() == SymbolTable.IF
                || lexicalAnalyzer.getToken() == SymbolTable.READLN || lexicalAnalyzer.getToken() == SymbolTable.WRITE
                || lexicalAnalyzer.getToken() == SymbolTable.WRITELN || lexicalAnalyzer.getToken() == SymbolTable.ID) {
            command();
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
    }

    private void test() {
        if (lexicalAnalyzer.getToken() == SymbolTable.IF) {
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
            if (lexicalAnalyzer.getToken() == SymbolTable.ELSE) {
                tokenMatch(SymbolTable.ELSE);
                loopCommands();
            }
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
    }

    private void read() {
        if (lexicalAnalyzer.getToken() == SymbolTable.READLN) {
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
        if (lexicalAnalyzer.getToken() == SymbolTable.WRITE) {
            tokenMatch(SymbolTable.WRITE);
            tokenMatch(SymbolTable.OPEN_PAR);
            byte currentType = expression();
            // Regra 20
            if (currentType != Symbol.TYPE_INTEGER &&
                currentType != Symbol.TYPE_STRING &&
                currentType != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
            while (lexicalAnalyzer.getToken() == SymbolTable.COMMA) {
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

        } else if (lexicalAnalyzer.getToken() == SymbolTable.WRITELN) {
            tokenMatch(SymbolTable.WRITELN);
            tokenMatch(SymbolTable.OPEN_PAR);
            byte currentType = expression();
            // Regra 20
            if (currentType != Symbol.TYPE_INTEGER &&
                currentType != Symbol.TYPE_STRING &&
                currentType != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                }
            while (lexicalAnalyzer.getToken() == SymbolTable.COMMA) {
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
        if (lexicalAnalyzer.getToken() == SymbolTable.ADD || lexicalAnalyzer.getToken() == SymbolTable.SUB
                || lexicalAnalyzer.getToken() == SymbolTable.NOT || lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR
                || lexicalAnalyzer.getToken() == SymbolTable.VALUE || lexicalAnalyzer.getToken() == SymbolTable.ID) {
            
            // Regra 16
            currentType = expressionPrecedence_3();
            // Regra 17
            this.flagEquals = false;
            if (lexicalAnalyzer.getToken() == SymbolTable.EQUAL_TO
                    || lexicalAnalyzer.getToken() == SymbolTable.NOT_EQUAL
                    || lexicalAnalyzer.getToken() == SymbolTable.GREATER
                    || lexicalAnalyzer.getToken() == SymbolTable.LESS
                    || lexicalAnalyzer.getToken() == SymbolTable.GREATER_EQUAL
                    || lexicalAnalyzer.getToken() == SymbolTable.LESS_EQUAL) {
                if (lexicalAnalyzer.getToken() == SymbolTable.EQUAL_TO) {
                    // Regra 18
                    this.flagEquals = true;
                }
                tokenMatch(lexicalAnalyzer.getToken());
                byte exp2Type = expressionPrecedence_3();
                // Regra 19
                if ((currentType != Symbol.TYPE_INTEGER && currentType != Symbol.TYPE_BYTE) ||
                    (exp2Type != Symbol.TYPE_INTEGER && exp2Type != Symbol.TYPE_BYTE)) {
                    if (currentType != exp2Type) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
                    if (this.flagEquals == false && currentType != Symbol.TYPE_STRING) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                    }
                }

            }
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
        return currentType;

    }

    private byte expressionPrecedence_3() {
        byte currentType = -1;
        if (lexicalAnalyzer.getToken() == SymbolTable.ADD || lexicalAnalyzer.getToken() == SymbolTable.SUB
            || lexicalAnalyzer.getToken() == SymbolTable.NOT || lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR
            || lexicalAnalyzer.getToken() == SymbolTable.VALUE || lexicalAnalyzer.getToken() == SymbolTable.ID) {
            // Regra 9
            this.flagPositive = false;
            this.flagNegative = false;
            if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                tokenMatch(SymbolTable.ADD);
                // Regra 10
                this.flagPositive = true;
            } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(SymbolTable.SUB);
                // Regra 11
                this.flagNegative = true;
            }
            byte ex1Type = expressionPrecedence_2();
            // Regra 12
            if (this.flagPositive == true || this.flagNegative == true) {
                if (ex1Type != Symbol.TYPE_INTEGER && ex1Type != Symbol.TYPE_BYTE) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
                } else {
                    currentType = Symbol.TYPE_INTEGER;
                }
            } else {
                currentType = ex1Type;
            }
            while (lexicalAnalyzer.getToken() == SymbolTable.ADD || lexicalAnalyzer.getToken() == SymbolTable.SUB
                    || lexicalAnalyzer.getToken() == SymbolTable.OR) {
                // Regra 13
                this.flagBoolean = false;
                this.flagNegative = false;
                this.flagPositive = false;

                if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                    tokenMatch(SymbolTable.ADD);
                    // Regra 10
                    this.flagPositive = true;
                } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                    tokenMatch(SymbolTable.SUB);
                    // Regra 11
                    this.flagNegative = true;
                } else if (lexicalAnalyzer.getToken() == SymbolTable.OR) {
                    tokenMatch(SymbolTable.OR);
                    // Regra 14
                    this.flagBoolean = true;
                }
                byte ex2Type = expressionPrecedence_2();
                // Regra 15
                if (currentType == Symbol.TYPE_INTEGER && (ex2Type != Symbol.TYPE_BYTE && ex2Type != Symbol.TYPE_INTEGER)) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                }
                if (this.flagPositive != true && this.flagNegative != true) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                } else if (ex1Type != ex2Type) {
                    ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                }
                if (ex2Type == Symbol.TYPE_BOOLEAN) {
                    if (this.flagBoolean == false) {
                        ErrorHandler.print(ErrorHandler.INVALID_TYPE, this.lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());              
                    }
                } else if (this.flagPositive == false) {
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
        if (lexicalAnalyzer.getToken() == SymbolTable.NOT || lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR
                || lexicalAnalyzer.getToken() == SymbolTable.VALUE || lexicalAnalyzer.getToken() == SymbolTable.ID) {
            byte e1type = expressionPrecedence_1();
            // Regra 5
            currentType = e1type;
            while (lexicalAnalyzer.getToken() == SymbolTable.MULT || lexicalAnalyzer.getToken() == SymbolTable.DIV
                    || lexicalAnalyzer.getToken() == SymbolTable.AND) {
                if (lexicalAnalyzer.getToken() == SymbolTable.MULT || lexicalAnalyzer.getToken() == SymbolTable.DIV) {
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
                tokenMatch(lexicalAnalyzer.getToken());
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
        if (lexicalAnalyzer.getToken() == SymbolTable.NOT) {
            tokenMatch(SymbolTable.NOT);
            byte e1type = expressionPrecedence_1();
            // Regra 4
            if (e1type != Symbol.TYPE_BOOLEAN) {
                ErrorHandler.print(ErrorHandler.INVALID_TYPE, lexicalAnalyzer.getCurrentLine(), this.symbol.getLexeme());
            } else {
                currentType = e1type;
            }
        } else if (lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR) {
            tokenMatch(SymbolTable.OPEN_PAR);
            byte expType = expression();
            // Regra 3
            currentType = expType;
            tokenMatch(SymbolTable.CLOSE_PAR);
        } else if (lexicalAnalyzer.getToken() == SymbolTable.VALUE) {
            tokenMatch(SymbolTable.VALUE);
            // Regra 2
            currentType = this.symbol.getType();
        } else if (lexicalAnalyzer.getToken() == SymbolTable.ID) {
            tokenMatch(SymbolTable.ID);
            // Regra 1
            currentType = this.symbol.getType();
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
        return currentType;
    }

    private byte types() {
        if (lexicalAnalyzer.getToken() == SymbolTable.INTEGER) {
            tokenMatch(SymbolTable.INTEGER);
            // Regra 31
            return Symbol.TYPE_INTEGER;
        } else if (lexicalAnalyzer.getToken() == SymbolTable.BOOLEAN) {
            tokenMatch(SymbolTable.BOOLEAN);
            // Regra 32
            return Symbol.TYPE_BOOLEAN;
        } else if (lexicalAnalyzer.getToken() == SymbolTable.BYTE) {
            tokenMatch(SymbolTable.BYTE);
            // Regra 33
            return Symbol.TYPE_BYTE;
        } else if (lexicalAnalyzer.getToken() == SymbolTable.STRING) {
            tokenMatch(SymbolTable.STRING);
            // Regra 34
            return Symbol.TYPE_STRING;
        } else {
            ErrorHandler.print(ErrorHandler.INVALID_LEXEME, lexicalAnalyzer.getCurrentLine(), lexicalAnalyzer.getLexeme());
        }
        return -1;
    }

}