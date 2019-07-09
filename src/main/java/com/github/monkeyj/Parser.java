package com.github.monkeyj;

import com.github.monkeyj.ast.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static com.github.monkeyj.Parser.Precedence.*;
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
    private Map<TokenType, Precedence> precedences = Stream.of(
            new Object[][]{
                    {EQ, EQUALS},
                    {NOT_EQ, EQUALS},
                    {LT, LESSGREATER},
                    {GT, LESSGREATER},
                    {PLUS, SUM},
                    {MINUS, SUM},
                    {SLASH, PRODUCT},
                    {ASTERISK, PRODUCT},
                    {LPAREN, CALL}
            }
    ).collect(Collectors.toMap(data -> (TokenType) data[0], data -> (Precedence) data[1]));

    public List<String> getErrors() {
        return errors;
    }

    private void registerPrefix(TokenType type, PrefixExprParser fn) {
        prefixParsers.put(type, fn);
    }
    private void registerInfix(TokenType type,InfixExprParser fn) {
        infixParsers.put(type, fn);
    }
    private Precedence peekPrecedence() {
        return precedences.getOrDefault(peekToken.getType(), LOWEST);
    }

    private Precedence curPrecedence() {
        return precedences.getOrDefault(curToken.getType(), LOWEST);
    }

    private void noPrefixParserError(TokenType type) {
        addError(String.format("no prefix parser function for %s found", type.name()));
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();

        registerPrefix(IDENT, this::parseIdentifier);
        registerPrefix(INT, this::parseIntegerLiteral);
        registerPrefix(BANG, this::parsePrefixExpression);
        registerPrefix(MINUS, this::parsePrefixExpression);

        registerInfix(PLUS, this::parseInfixExpression);
        registerInfix(MINUS, this::parseInfixExpression);
        registerInfix(SLASH, this::parseInfixExpression);
        registerInfix(ASTERISK, this::parseInfixExpression);
        registerInfix(EQ, this::parseInfixExpression);
        registerInfix(NOT_EQ, this::parseInfixExpression);
        registerInfix(LT, this::parseInfixExpression);
        registerInfix(GT, this::parseInfixExpression);
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

    private Expression parseInfixExpression(Expression left) {
        InfixExpression expression = new InfixExpression(curToken, left, curToken.getLiteral());

        Precedence precedence = curPrecedence();
        nextToken();
        expression.setRight(parseExpression(precedence));

        return expression;
    }

    private Expression parsePrefixExpression() {
        PrefixExpression expression = new PrefixExpression(curToken, curToken.getLiteral());
        nextToken();
        expression.setRight(parseExpression(PREFIX));
        return expression;
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
            noPrefixParserError(curToken.getType());
            return null;
        }

        Expression leftExpr = prefix.parse();

        while (!peekTokenIs(SEMICOLON) && precedence.compareTo(peekPrecedence()) < 0) {
            InfixExprParser infixParser = infixParsers.get(peekToken.getType());
            if(infixParser == null) {
                return leftExpr;
            }
            nextToken();
            leftExpr = infixParser.parse(leftExpr);
        }
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
