package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public abstract class Statement extends Node {
    public Statement() {

    }
    public Statement(Token token) {
        super(token);
    }
    public abstract void statementNode();
}
