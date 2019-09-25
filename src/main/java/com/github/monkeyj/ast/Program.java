package com.github.monkeyj.ast;

import com.github.monkeyj.value.IObject;

import java.util.ArrayList;
import java.util.List;

import static com.github.monkeyj.Utils.hasItem;

public class Program {

    private List<Statement> statements = new ArrayList<Statement>();


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

    public IObject accept(NodeVisitor visitor) {
        IObject result = null;
        for(Statement stmt : statements) {
            result = stmt.accept(visitor);

        }
        return result;
    }

}
