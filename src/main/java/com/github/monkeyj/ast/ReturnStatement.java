package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public class ReturnStatement extends Statement {
    private Expression returnValue;

    public Expression getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Expression returnValue) {
        this.returnValue = returnValue;
    }

    public void statementNode() {


    }

    public String tokenLiteral() {
        return token.getLiteral();
    }
}
