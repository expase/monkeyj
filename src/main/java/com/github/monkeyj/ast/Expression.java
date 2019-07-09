package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public abstract class Expression extends Node {
    public abstract void expressionNode();
    public Expression(Token token) {
        super(token);
    }
}
