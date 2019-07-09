package com.github.monkeyj;

import java.util.HashMap;
import java.util.Map;

public enum TokenType {
    ILLEGAL,
    EOF,

    IDENT,
    INT,

    ASSIGN,
    PLUS,
    MINUS,
    BANG,
    ASTERISK,
    SLASH,

    EQ,
    NOT_EQ,

    LT,
    GT,

    COMMA,
    SEMICOLON,

    LPAREN,
    RPAREN,

    LBRACE,
    RBRACE,

    FUNCTION,
    LET,
    TRUE,
    FALSE,
    IF,
    ELSE,
    RETURN;

    static Map<String,TokenType> keywords = new HashMap<String, TokenType>();
    static {
        keywords.put("fn", FUNCTION);
        keywords.put("let", LET);
        keywords.put("true", TRUE);
        keywords.put("false", FALSE);
        keywords.put("if", IF);
        keywords.put("else", ELSE);
        keywords.put("return", RETURN);
    }

    public static TokenType lookupIdent(String ident) {
        return keywords.getOrDefault(ident, IDENT);
    }
}

