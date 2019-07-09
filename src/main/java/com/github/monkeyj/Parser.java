package com.github.monkeyj;

import com.github.monkeyj.ast.Identifier;
import com.github.monkeyj.ast.LetStatement;
import com.github.monkeyj.ast.Program;
import com.github.monkeyj.ast.Statement;

import static com.github.monkeyj.TokenType.*;

public class Parser {
    private Lexer lexer;
    private Token curToken;
    private Token peekToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();
    }

    private void nextToken() {
        curToken = peekToken;
        peekToken = lexer.nextToken();
    }

    public Program parseProgram() {
        Program program = new Program();

        while (curToken.getType() != EOF) {
            Statement stmt = parseStatement();
            if(stmt != null) {
                program.addStatement(stmt);
            }
            nextToken();
        }

        return program;
    }

    private Statement parseStatement() {
        switch (curToken.getType()) {
            case LET:
                return parseLetStatement();
            default:
                return null;
        }
    }

    private LetStatement parseLetStatement() {
        LetStatement stmt = new LetStatement();

        if(!expectPeek(IDENT)) {
            return null;
        }

        stmt.setName(new Identifier(curToken, curToken.getLiteral()));

        if (!expectPeek(ASSIGN)) {
            return null;
        }

        while(!curTokenIs(SEMICOLON)) {
            nextToken();
        }

        return stmt;
    }

    private boolean curTokenIs(TokenType type) {
        return curToken.getType() == type;
    }

    private boolean peekTokenIs(TokenType type) {
        return peekToken.getType() == type;
    }

    private boolean expectPeek(TokenType type) {
        if (peekTokenIs(type)) {
            nextToken();
            return true;
        }
        return false;
    }
}
