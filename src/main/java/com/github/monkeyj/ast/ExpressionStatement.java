package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public class ExpressionStatement extends Statement {
    private Expression expression;

    public ExpressionStatement(Token token) {
        super(token);
    }
    public Expression getExpression() {
        return expression;
    }

    public void statementNode() {

    }
    public String toString() {
        if (expression != null) {
            return expression.toString();
        }
        return "";
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
