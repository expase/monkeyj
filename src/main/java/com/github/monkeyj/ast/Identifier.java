package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public class Identifier extends Expression {

    private String value;



    public Identifier(Token token, String value) {
        super(token);
        this.value = value;
    }

    public String tokenLiteral() {
        return token.getLiteral();
    }

    public void expressionNode() {

    }

    public String toString() {
        return value;
    }
}
