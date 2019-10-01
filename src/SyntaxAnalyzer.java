
/**
 * SyntaxAnalyzer
 */

import java.io.*;

public class SyntaxAnalyzer {

    private LexicalAnalyzer lexicalAnalyzer;

    private SymbolTable symbolTable;
    private Symbol symbol;

    private BufferedReader reader;

    public SyntaxAnalyzer() {
        this.lexicalAnalyzer = new LexicalAnalyzer(reader);
        this.symbolTable = new SymbolTable();
    }

    private void tokenMatch(byte expectedToken) {
        if (symbol.getToken() == expectedToken) {
            lexicalAnalyzer.AFD();
        } else {
            if (lexicalAnalyzer.isEndOfFile()) {
                // ErrorHandler.
            } else {
                // token n esperado
            }
        }
    }

    private void parser() {
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
        if (lexicalAnalyzer.getToken() == SymbolTable.INTEGER || lexicalAnalyzer.getToken() == SymbolTable.BOOLEAN
                || lexicalAnalyzer.getToken() == SymbolTable.BYTE || lexicalAnalyzer.getToken() == SymbolTable.STRING) {
            types();
            tokenMatch(SymbolTable.ID);

            if (lexicalAnalyzer.getToken() == SymbolTable.EQUAL) {
                tokenMatch(SymbolTable.EQUAL);
                if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                    tokenMatch(SymbolTable.ADD);
                } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                    tokenMatch(SymbolTable.SUB);
                }
                expression();
            }

            while (lexicalAnalyzer.getToken() == SymbolTable.COMMA) {
                tokenMatch(SymbolTable.COMMA);
                tokenMatch(SymbolTable.ID);

                if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                    tokenMatch(SymbolTable.ADD);
                } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                    tokenMatch(SymbolTable.SUB);
                }
                expression();
            }

            tokenMatch(SymbolTable.SEMICOLON);
        } else if (lexicalAnalyzer.getToken() == SymbolTable.CONST) {
            tokenMatch(SymbolTable.ID);
            tokenMatch(SymbolTable.EQUAL);
            if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                tokenMatch(SymbolTable.ADD);
            } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(SymbolTable.SUB);
            }
            expression();
            tokenMatch(SymbolTable.SEMICOLON);
        } else if (lexicalAnalyzer.getToken() == SymbolTable.ID) {
            tokenMatch(SymbolTable.EQUAL);
            if (lexicalAnalyzer.getToken() == SymbolTable.ADD) {
                tokenMatch(SymbolTable.ADD);
            } else if (lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(SymbolTable.SUB);
            }
            expression();
            tokenMatch(SymbolTable.SEMICOLON);
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
        }
    }

    private void loop() {
        if (lexicalAnalyzer.getToken() == SymbolTable.WHILE) {
            tokenMatch(SymbolTable.WHILE);
            tokenMatch(SymbolTable.OPEN_PAR);
            expression();
            tokenMatch(SymbolTable.CLOSE_PAR);
            loopCommands();
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
        }
    }

    private void test() {
        if (lexicalAnalyzer.getToken() == SymbolTable.IF) {
            tokenMatch(SymbolTable.IF);
            tokenMatch(SymbolTable.OPEN_PAR);
            expression();
            tokenMatch(SymbolTable.CLOSE_PAR);
            tokenMatch(SymbolTable.THEN);
            loopCommands();
            if (lexicalAnalyzer.getToken() == SymbolTable.ELSE) {
                tokenMatch(SymbolTable.ELSE);
                loopCommands();
            }
        }
    }

    private void read() {
        if (lexicalAnalyzer.getToken() == SymbolTable.READLN) {
            tokenMatch(SymbolTable.READLN);
            tokenMatch(SymbolTable.OPEN_PAR);
            tokenMatch(SymbolTable.ID);
            tokenMatch(SymbolTable.CLOSE_PAR);
            tokenMatch(SymbolTable.SEMICOLON);
        }
    }

    private void write() {
        if (lexicalAnalyzer.getToken() == SymbolTable.WRITE) {
            tokenMatch(SymbolTable.WRITE);
            tokenMatch(SymbolTable.OPEN_PAR);
            expression();
            while (lexicalAnalyzer.getToken() == SymbolTable.COMMA) {
                tokenMatch(SymbolTable.COMMA);
                expression();
            }
            tokenMatch(SymbolTable.CLOSE_PAR);
            tokenMatch(SymbolTable.SEMICOLON);

        } else if (lexicalAnalyzer.getToken() == SymbolTable.WRITELN) {
            tokenMatch(SymbolTable.WRITELN);
            tokenMatch(SymbolTable.OPEN_PAR);
            expression();
            while (lexicalAnalyzer.getToken() == SymbolTable.COMMA) {
                tokenMatch(SymbolTable.COMMA);
                expression();
            }
            tokenMatch(SymbolTable.CLOSE_PAR);
            tokenMatch(SymbolTable.SEMICOLON);
        }
    }

    private void expression() {
        if (lexicalAnalyzer.getToken() == SymbolTable.ADD || lexicalAnalyzer.getToken() == SymbolTable.SUB
                || lexicalAnalyzer.getToken() == SymbolTable.NOT || lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR
                || lexicalAnalyzer.getToken() == SymbolTable.CONST || lexicalAnalyzer.getToken() == SymbolTable.ID) {
            expressionPrecedence_3();
            if (lexicalAnalyzer.getToken() == SymbolTable.EQUAL_TO
                    || lexicalAnalyzer.getToken() == SymbolTable.NOT_EQUAL
                    || lexicalAnalyzer.getToken() == SymbolTable.GREATER
                    || lexicalAnalyzer.getToken() == SymbolTable.LESS
                    || lexicalAnalyzer.getToken() == SymbolTable.GREATER_EQUAL
                    || lexicalAnalyzer.getToken() == SymbolTable.LESS_EQUAL) {
                tokenMatch(lexicalAnalyzer.getToken());
                expressionPrecedence_3();
            }
        }

    }

    private void expressionPrecedence_3() {
        if (lexicalAnalyzer.getToken() == SymbolTable.ADD || lexicalAnalyzer.getToken() == SymbolTable.SUB
                || lexicalAnalyzer.getToken() == SymbolTable.NOT || lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR
                || lexicalAnalyzer.getToken() == SymbolTable.CONST || lexicalAnalyzer.getToken() == SymbolTable.ID) {
            if (lexicalAnalyzer.getToken() == SymbolTable.ADD || lexicalAnalyzer.getToken() == SymbolTable.SUB) {
                tokenMatch(lexicalAnalyzer.getToken());
            }
            expressionPrecedence_2();
            while (lexicalAnalyzer.getToken() == SymbolTable.ADD || lexicalAnalyzer.getToken() == SymbolTable.SUB
                    || lexicalAnalyzer.getToken() == SymbolTable.OR) {
                tokenMatch(lexicalAnalyzer.getToken());
                expressionPrecedence_2();
            }
        }
    }

    private void expressionPrecedence_2() {
        if (lexicalAnalyzer.getToken() == SymbolTable.NOT || lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR
                || lexicalAnalyzer.getToken() == SymbolTable.CONST || lexicalAnalyzer.getToken() == SymbolTable.ID) {
            expressionPrecedence_1();
            while (lexicalAnalyzer.getToken() == SymbolTable.MULT || lexicalAnalyzer.getToken() == SymbolTable.DIV
                    || lexicalAnalyzer.getToken() == SymbolTable.AND) {
                tokenMatch(lexicalAnalyzer.getToken());
                expressionPrecedence_1();
            }
        }
    }

    private void expressionPrecedence_1() {
        if (lexicalAnalyzer.getToken() == SymbolTable.NOT) {
            tokenMatch(SymbolTable.NOT);
            expressionPrecedence_1();
        } else if (lexicalAnalyzer.getToken() == SymbolTable.OPEN_PAR) {
            tokenMatch(SymbolTable.OPEN_PAR);
            expression();
            tokenMatch(SymbolTable.CLOSE_PAR);
        } else if (lexicalAnalyzer.getToken() == SymbolTable.CONST) {
            tokenMatch(SymbolTable.CLOSE_PAR);
        } else if (lexicalAnalyzer.getToken() == SymbolTable.ID) {
            tokenMatch(SymbolTable.CLOSE_PAR);
        }
    }

    private void types() {
        if (lexicalAnalyzer.getToken() == SymbolTable.INTEGER) {
            tokenMatch(SymbolTable.INTEGER);
        } else if (lexicalAnalyzer.getToken() == SymbolTable.BOOLEAN) {
            tokenMatch(SymbolTable.BOOLEAN);
        } else if (lexicalAnalyzer.getToken() == SymbolTable.BYTE) {
            tokenMatch(SymbolTable.BYTE);
        } else if (lexicalAnalyzer.getToken() == SymbolTable.STRING) {
            tokenMatch(SymbolTable.STRING);
        }
    }

}