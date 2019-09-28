package com.github.monkeyj.ast;


import com.github.monkeyj.value.IObject;
import com.github.monkeyj.value.ReturnValueObject;

import static com.github.monkeyj.Utils.hasItem;

public class Program extends ListStatement {


    public String tokenLiteral() {
        if (hasItem(statements)) {
            return statements.get(0).tokenLiteral();
        }
        return "";
    }

    public IObject accept(NodeVisitor visitor) {
        IObject result = null;
        for(Statement stmt : statements) {
            result = stmt.accept(visitor);
            switch (result.getType()) {
                case "return" : return ((ReturnValueObject)result).getValue();
                case "error" : return result;
            }
        }
        return result;
    }

}
