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
        Operator opr = Operator.getOperator(operator);
        return opr.visit(leftValue, rightValue);
    }

    public IObject visit(IfExpression ifExpr) {
        IObject condition = ifExpr.getCondition().accept(this);
        if(isTruthy(condition)) {
            return ifExpr.getConsequence().accept(this);
        } else if (ifExpr.getAlternative() != null) {
            return ifExpr.getAlternative().accept(this);
        } else {
            return NullObject.NULL;
        }

    }

    private boolean isTruthy(IObject condition) {
        if(condition == NullObject.NULL) return  false;
        if(condition == TRUE) return true;
        if(condition == FALSE) return  false;

        return true;
    }

}
