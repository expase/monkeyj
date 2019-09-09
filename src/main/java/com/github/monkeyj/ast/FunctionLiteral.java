package com.github.monkeyj.ast;

import com.github.monkeyj.Token;
import com.github.monkeyj.Utils;

import java.util.ArrayList;
import java.util.List;

public class FunctionLiteral extends Expression {
    private List<Identifier> parameters = new ArrayList<>();
    private BlockStatement body;

    public FunctionLiteral(Token token) {
        super(token);
    }

    @Override
    public void expressionNode() {

    }

    public String tokenLiteral() {
        return token.getLiteral();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(token.getLiteral());
        buf.append("(");
        buf.append(Utils.strJoin(parameters, ", "));
        buf.append(")");
        buf.append(body.toString());
        return buf.toString();
    }

    public List<Identifier> getParameters() {
        return parameters;
    }

    public void setParameters(List<Identifier> parameters) {
        this.parameters = parameters;
    }

    public BlockStatement getBody() {
        return body;
    }

    public void setBody(BlockStatement body) {
        this.body = body;
    }
}
