package com.github.monkeyj.ast;

import com.github.monkeyj.Context;
import com.github.monkeyj.Token;
import com.github.monkeyj.value.ErrorObject;
import com.github.monkeyj.value.IObject;
import com.github.monkeyj.value.ReturnValueObject;

public class BlockStatement extends ListStatement {

    public BlockStatement(Token token) {
        this.token = token;
    }

    public String tokenLiteral() {
        return token.getLiteral();
    }

    public IObject accept(NodeVisitor visitor, Context context) {
        IObject result = null;
        for(Statement stmt : statements) {
            result = stmt.accept(visitor, context);
            if(result != null) {
                if(result instanceof ReturnValueObject || result instanceof ErrorObject) {
                    return result;
                }
            }

        }
        return result;
    }
}
