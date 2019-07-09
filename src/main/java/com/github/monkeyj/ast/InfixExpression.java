package com.github.monkeyj.ast;

import com.github.monkeyj.Token;

public class InfixExpression extends Expression {
    private Expression left;
    private String operator;
    private Expression right;

    public InfixExpression(Token token, Expression left, String operator) {
        super(token);
        this.left = left;
        this.operator = operator;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
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

    @Override
    public void expressionNode() {

    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(");
        buf.append(left.toString());
        buf.append(" ").append(operator).append(" ");
        buf.append(right.toString());
        buf.append(")");
        return buf.toString();
    }
}
