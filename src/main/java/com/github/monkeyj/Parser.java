package com.github.monkeyj;

import com.github.monkeyj.ast.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.github.monkeyj.Parser.Precedence.LOWEST;
import static com.github.monkeyj.TokenType.*;


public class Parser {
    enum Precedence {
        LOWEST,
        EQUALS,		// ==
        LESSGREATER, // > or <
        SUM, 		// +
        PRODUCT,	// *
        PREFIX, 		// -X or !X
        CALL		// function call
    }
    @FunctionalInterface
    interface PrefixExprParser {
        Expression parse();
    }
    @FunctionalInterface
    interface InfixExprParser {
        Expression parse(Expression expression);
    }

    private Lexer lexer;
    private Token curToken;
    private Token peekToken;
    private Map<TokenType, PrefixExprParser> prefixParsers = new HashMap<>();
    private Map<TokenType, InfixExprParser> infixParsers = new HashMap<>();
    private List<String> errors = new ArrayList<>();

    private void registerPrefix(TokenType type,PrefixExprParser fn) {
        prefixParsers.put(type, fn);
    }
    private void registerInfix(TokenType type,InfixExprParser fn) {
        infixParsers.put(type, fn);
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();

        registerPrefix(IDENT, this::parseIdentifier);
        registerPrefix(INT, this::parseIntegerLiteral);

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
            case RETURN:
                return parseReturnStatement();
            default:
                return parseExpressionStatement();
        }
    }

    private ExpressionStatement parseExpressionStatement() {
        ExpressionStatement stmt = new ExpressionStatement(curToken);
        stmt.setExpression(parseExpression(LOWEST));
        if (peekTokenIs(SEMICOLON)) {
            nextToken();
        }
        return stmt;
    }

    private Expression parseIntegerLiteral() {
        IntegerLiteral lit = new IntegerLiteral(curToken, 0);
        Integer value = null;
        try {
            value = Integer.parseInt(curToken.getLiteral());
        }catch (NumberFormatException e) {
            addError(String.format("could not parse %s as integer", curToken.getLiteral()));
            return null;
        }

        lit.setValue(value);
        return lit;
    }

    private Expression parseExpression(Precedence precedence) {
        PrefixExprParser prefix = prefixParsers.get(curToken.getType());
        if(prefix == null) {
            return null;
        }

        Expression leftExpr = prefix.parse();

        return leftExpr;

    }

    private Expression parseIdentifier() {
        return new Identifier(curToken, curToken.getLiteral());
    }

    private ReturnStatement parseReturnStatement() {
        ReturnStatement stmt = new ReturnStatement(curToken);
        nextToken();

        while(!curTokenIs(SEMICOLON)) {
            nextToken();
        }

        return stmt;
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

    public void addError(String error) {
        errors.add(error);
    }
}
