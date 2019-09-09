package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public class BooleanNode extends Expression {
    private boolean value;

    public BooleanNode(Token token, boolean value) {
        super(token);
        this.value = value;
    }
    @Override
    public void expressionNode() {

    }

    public String tokenLiteral() {
        return token.getLiteral();
    }

    public String toString() {
        return token.getLiteral();
    }

}
