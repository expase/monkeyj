package com.github.monkeyj.ast;

import com.github.monkeyj.Token;
import com.github.monkeyj.value.IObject;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement extends Statement {
    private List<Statement> statements = new ArrayList<>();

    public BlockStatement(Token token) {
        super(token);
    }
    @Override
    public void statementNode() {

    }

    public String tokenLiteral() {
        return token.getLiteral();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();

        for(Statement statement : statements) {
            buf.append(statement.toString());
        }

        return buf.toString();
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    public IObject accept(NodeVisitor visitor) {
        IObject result = null;
        for(Statement stmt : statements) {
            result = stmt.accept(visitor);

        }
        return result;
    }
}
