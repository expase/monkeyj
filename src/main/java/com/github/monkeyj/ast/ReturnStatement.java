package com.github.monkeyj.ast;

import com.github.monkeyj.Context;
import com.github.monkeyj.Token;
import com.github.monkeyj.value.IObject;

public class ReturnStatement extends Statement {
    private Expression returnValue;

    public ReturnStatement(Token token) {
        super(token);
    }

    public Expression getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Expression returnValue) {
        this.returnValue = returnValue;
    }

    public void statementNode() {


    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(tokenLiteral() + " ");
        if (returnValue != null) {
            out.append(returnValue.toString());
        }
        out.append(";");
        return out.toString();
    }

    public IObject accept(NodeVisitor visitor, Context context) {
        return visitor.visit(this,context);
    }

}
