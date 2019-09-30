package com.github.monkeyj.ast;

import com.github.monkeyj.Token;
import com.github.monkeyj.value.IObject;

public class LetStatement extends Statement {
    private Identifier name;
    private Expression value;

    public LetStatement(Token token) {
        super(token);
    }
    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    public String tokenLiteral() {
        return token.getLiteral();
    }

    public void statementNode() {

    }
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(tokenLiteral()).append(" ");
        buf.append(name.toString());
        buf.append(" = ");
        if (value != null) {
            buf.append(value.toString());
        }
        buf.append(";");

        return buf.toString();
    }

    public IObject accept(NodeVisitor visitor) {
        return visitor.visit(this);
    }
}
