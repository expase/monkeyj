package com.github.monkeyj.ast;

import com.github.monkeyj.value.IObject;

public interface NodeVisitor {
    IObject visit(BooleanNode node);
    IObject visit(IntegerLiteral node);

    IObject visit(PrefixExpression prefixExpr);
    IObject visit(InfixExpression infixExpr);
    IObject visit(IfExpression ifExpr);
}
