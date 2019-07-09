package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public class PrefixExpression extends Expression {
    private String operator;
    private Expression right;

    public PrefixExpression(Token token, String operator) {
        super(token);
        this.operator = operator;
    }
    @Override
    public void expressionNode() {

    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("(");
        out.append(operator);
        out.append(right.toString());
        out.append(")");
        return out.toString();
    }
}
