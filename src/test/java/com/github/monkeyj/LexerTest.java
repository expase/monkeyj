package com.github.monkeyj;


import org.junit.Test;
import static com.github.monkeyj.TokenType.*;
import static org.junit.Assert.*;

public class LexerTest {
    @Test
    public void testNextToken() {
        String input = "=+(){},;";

        Token[] tokens = new Token[]{
                new Token(ASSIGN, "="),
                new Token(PLUS, "+"),
                new Token(LPAREN, "("),
                new Token(RPAREN, ")"),
                new Token(LBRACE, "{"),
                new Token(RBRACE, "}"),
                new Token(COMMA, ","),
                new Token(SEMICOLON, ";"),
                new Token(EOF, ""),
        };

        Lexer lexer = new Lexer(input);

        for(int i = 0;i< tokens.length; i++) {
            Token token = lexer.nextToken();

            assertEquals(tokens[i].getType(), token.getType());
            assertEquals(tokens[i].getLiteral(), token.getLiteral());
        }

    }


}
