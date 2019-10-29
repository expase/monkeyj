package com.github.monkeyj.ast;

import com.github.monkeyj.Context;
import com.github.monkeyj.Token;
import com.github.monkeyj.value.IObject;


public class IndexExpression extends Expression  {
    private Expression left;
    private Expression index;

    public IndexExpression(Token token) {
        super(token);
    }
    @Override
    public void expressionNode() {

    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = index;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("(");
        buf.append(left.toString());
        buf.append("[");
        buf.append(index.toString());
        buf.append("])");
        return buf.toString();
    }

    @Override
    public IObject accept(NodeVisitor visitor, Context context) {
        return visitor.visit(this, context);
    }
}
