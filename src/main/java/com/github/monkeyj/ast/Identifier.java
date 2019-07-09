package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public class Identifier implements Expression {
    private Token token;
    private String value;

    public Identifier() {

    }

    public Identifier(Token token, String value) {
        this.token = token;
        this.value = value;
    }

    public String tokenLiteral() {
        return token.getLiteral();
    }

    public void expressionNode() {

    }
}
