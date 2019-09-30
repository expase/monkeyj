package com.github.monkeyj.ast;

import com.github.monkeyj.Context;
import com.github.monkeyj.Token;
import com.github.monkeyj.value.IObject;

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

    @Override
    public IObject accept(NodeVisitor visitor, Context context) {
        return expression.accept(visitor, context);
    }
}
