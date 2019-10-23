package com.github.monkeyj.ast;

import com.github.monkeyj.Context;
import com.github.monkeyj.Token;
import com.github.monkeyj.value.IObject;

public class StringLiteral extends Expression {
    private String value;

    public StringLiteral(Token token, String value) {
        super(token);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void expressionNode() {

    }

    @Override
    public IObject accept(NodeVisitor visitor, Context context) {
        return visitor.visit(this, context);
    }

}
