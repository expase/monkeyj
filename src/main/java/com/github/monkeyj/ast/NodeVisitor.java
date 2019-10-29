package com.github.monkeyj.ast;

import com.github.monkeyj.Context;
import com.github.monkeyj.value.IObject;

public interface NodeVisitor {
    IObject visit(BooleanNode node, Context context);
    IObject visit(IntegerLiteral node, Context context);

    IObject visit(PrefixExpression prefixExpr, Context context);
    IObject visit(InfixExpression infixExpr, Context context);
    IObject visit(IfExpression ifExpr, Context context);

    IObject visit(ReturnStatement statement, Context context);
    IObject visit(LetStatement statement, Context context);
    IObject visit(Identifier identifier, Context context);

    IObject visit(FunctionLiteral fi, Context context);
    IObject visit(CallExpression call, Context context);

    IObject visit(StringLiteral si,Context context);
    IObject visit(ArrayLiteral array,Context context);
    IObject visit(IndexExpression expr,Context context);
}
