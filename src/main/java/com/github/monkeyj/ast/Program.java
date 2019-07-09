package com.github.monkeyj.ast;

import java.util.ArrayList;
import java.util.List;

import static com.github.monkeyj.Utils.hasItem;

public class Program {

    private List<Statement> statements = new ArrayList<Statement>();
    private List<String> errors = new ArrayList<String>();

    public String tokenLiteral() {
        if (hasItem(statements)) {
            return statements.get(0).tokenLiteral();
        }
        return "";
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        for(Statement stmt : statements) {
            buf.append(stmt.toString());
        }
        return buf.toString();
    }

    public void addStatement(Statement stmt) {
        statements.add(stmt);
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error) {
        errors.add(error);
    }
}
