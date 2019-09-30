package com.github.monkeyj.ast;

import com.github.monkeyj.Token;
import com.github.monkeyj.value.IObject;

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

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }

    public IObject accept(NodeVisitor visitor) {
        return visitor.visit(this);
    }
}
