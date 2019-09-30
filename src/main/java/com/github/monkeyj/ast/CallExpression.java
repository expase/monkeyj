package com.github.monkeyj.ast;

import com.github.monkeyj.Context;
import com.github.monkeyj.Token;
import com.github.monkeyj.Utils;
import com.github.monkeyj.value.IObject;

import java.util.ArrayList;
import java.util.List;

public class CallExpression extends Expression {
    private Expression function;
    private List<Expression> arguments = new ArrayList<>();

    public CallExpression(Token token,Expression function) {
        super(token);
        this.function = function;
    }

    @Override
    public void expressionNode() {

    }

    public String tokenLiteral() {
        return token.getLiteral();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();

        buf.append(function.toString());
        buf.append("(");
        buf.append(Utils.strJoin(arguments, ", "));
        buf.append(")");

        return buf.toString();
    }

    public Expression getFunction() {
        return function;
    }

    public void setFunction(Expression function) {
        this.function = function;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }

    public IObject accept(NodeVisitor visitor, Context context) {
        return visitor.visit(this, context);
    }
}
