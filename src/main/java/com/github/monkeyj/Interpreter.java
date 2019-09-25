package com.github.monkeyj;


import com.github.monkeyj.ast.*;
import com.github.monkeyj.value.BooleanObject;
import com.github.monkeyj.value.IObject;
import com.github.monkeyj.value.IntegerObject;
import com.github.monkeyj.value.NullObject;

import static com.github.monkeyj.value.BooleanObject.FALSE;
import static com.github.monkeyj.value.BooleanObject.TRUE;

public class Interpreter implements NodeVisitor {

    public IObject visit(BooleanNode node) {
        return node.getValue() ? TRUE : FALSE;
    }

    public IObject visit(IntegerLiteral node) {
        return new IntegerObject(node.getValue());
    }

    public IObject visit(PrefixExpression prefixExpr) {
        IObject rightValue = prefixExpr.getRight().accept(this);

        return visitPrefixExpression(prefixExpr.getOperator(), rightValue);
    }

    private IObject visitPrefixExpression(String operator, IObject rightValue) {
        Operator opr = Operator.getOperator(operator);
        return opr.visit(rightValue);

    }

    public IObject visit(InfixExpression infixExpr) {
        IObject leftValue = infixExpr.getLeft().accept(this);
        IObject rightValue = infixExpr.getRight().accept(this);
        return visitInfixExpression(infixExpr.getOperator(), leftValue, rightValue);
    }

    private IObject visitInfixExpression(String operator, IObject leftValue, IObject rightValue) {
        if (leftValue instanceof IntegerObject && rightValue instanceof IntegerObject) {
            return visitIntegerInfixExpression(operator, leftValue, rightValue);
        }

        return NullObject.NULL;
    }

    private IObject visitIntegerInfixExpression(String operator, IObject left, IObject right) {
        Operator opr = Operator.getOperator(operator);

        return opr.visit(left, right);
    }


}
