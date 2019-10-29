package com.github.monkeyj.ast;

import com.github.monkeyj.Context;
import com.github.monkeyj.Token;
import com.github.monkeyj.Utils;
import com.github.monkeyj.value.IObject;

import java.util.ArrayList;
import java.util.List;

public class ArrayLiteral extends Expression {
    private List<Expression>  elements = new ArrayList<>();

    public ArrayLiteral(Token token) {
        super(token);
    }

    @Override
    public void expressionNode() {

    }

    public void addItem(Expression item) {
        elements.add(item);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[");
        buf.append(Utils.strJoin(elements, ", "));
        buf.append("]");
        return buf.toString();
    }

    public List<Expression> getElements() {
        return elements;
    }

    public void setElements(List<Expression> elements) {
        this.elements = elements;
    }

    @Override
    public IObject accept(NodeVisitor visitor, Context context) {
        return visitor.visit(this,context);
    }
}
