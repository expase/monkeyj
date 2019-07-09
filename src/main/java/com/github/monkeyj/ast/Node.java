package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public abstract class Node {
    protected Token token;

    public Node() {

    }

    public Node(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String tokenLiteral() {
        return token.getLiteral();
    }


}
