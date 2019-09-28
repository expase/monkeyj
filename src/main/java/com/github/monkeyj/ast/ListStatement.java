package com.github.monkeyj.ast;

import com.github.monkeyj.value.IObject;
import com.github.monkeyj.value.ReturnValueObject;

import java.util.ArrayList;
import java.util.List;

public class ListStatement extends Statement {
    protected List<Statement> statements = new ArrayList<>();

    @Override
    public void statementNode() {

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

    public List<Statement> getStatements() {
        return statements;
    }


}
