package com.github.monkeyj.ast;

import com.github.monkeyj.Context;
import com.github.monkeyj.Token;
import com.github.monkeyj.value.IObject;

public class BooleanNode extends Expression {
    private boolean value;

    public BooleanNode(Token token, boolean value) {
        super(token);
        this.value = value;
    }
    @Override
    public void expressionNode() {

    }

    public boolean getValue() {
        return value;
    }
    public String tokenLiteral() {
        return token.getLiteral();
    }

    public String toString() {
        return token.getLiteral();
    }

    public IObject accept(NodeVisitor visitor, Context context) {
        return visitor.visit(this, context);
    }
}
