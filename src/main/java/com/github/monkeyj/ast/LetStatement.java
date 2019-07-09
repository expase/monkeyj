package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public class LetStatement extends Statement {
    private Identifier name;
    private Expression value;

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    public String tokenLiteral() {
        return token.getLiteral();
    }

    public void statementNode() {

    }
}
