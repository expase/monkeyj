package com.github.monkeyj;


import com.github.monkeyj.ast.*;
import com.github.monkeyj.value.*;

import static com.github.monkeyj.value.BooleanObject.FALSE;
import static com.github.monkeyj.value.BooleanObject.TRUE;
import static com.github.monkeyj.value.ErrorObject.error;
import static com.github.monkeyj.value.NullObject.NULL;

public class Interpreter implements NodeVisitor {
    private Context context = new Context();

    public IObject visit(BooleanNode node) {
        return node.getValue() ? TRUE : FALSE;
    }

    public IObject visit(IntegerLiteral node) {
        return new IntegerObject(node.getValue());
    }

    public IObject visit(PrefixExpression prefixExpr) {
        IObject rightValue = prefixExpr.getRight().accept(this);

        return isError(rightValue) ? rightValue : visitPrefixExpression(prefixExpr.getOperator(), rightValue);
    }

    private IObject visitPrefixExpression(String operator, IObject rightValue) {
        Operator opr = Operator.getOperator(operator);
        return opr != null ? opr.visit(rightValue) : error("unknown operator: %s %s", operator, rightValue.getType());
    }

    public IObject visit(InfixExpression infixExpr) {
        IObject leftValue = infixExpr.getLeft().accept(this);
        IObject rightValue = infixExpr.getRight().accept(this);
        if(isError(leftValue)) return leftValue;
        if(isError(rightValue)) return rightValue;
        return visitInfixExpression(infixExpr.getOperator(), leftValue, rightValue);
    }

    private IObject visitInfixExpression(String operator, IObject leftValue, IObject rightValue) {
        if(!leftValue.getType().equals(rightValue.getType())) {
            return error("type mismatch: %s %s %s", leftValue.getType(), operator, rightValue.getType());
        }
        Operator opr = Operator.getOperator(operator);
        return opr != null ? opr.visit(leftValue, rightValue) : error("unknown operator: %s %s %s", leftValue.getType(), operator, rightValue.getType());
    }

    public IObject visit(IfExpression ifExpr) {
        IObject condition = ifExpr.getCondition().accept(this);
        if (isError(condition)) return condition;

        if(isTruthy(condition)) {
            return ifExpr.getConsequence().accept(this);
        } else if (ifExpr.getAlternative() != null) {
            return ifExpr.getAlternative().accept(this);
        } else {
            return NULL;
        }

    }

    public IObject visit(LetStatement statement) {
        IObject value = statement.getValue().accept(this);
        if(isError(value)) return value;
        context.set(statement.getName().getValue(), value);
        return NULL;
    }

    public IObject visit(FunctionLiteral fi) {
        return new Function(fi.getParameters(), context, fi.getBody());
    }

    public IObject visit(Identifier identifier) {
        IObject result = context.get(identifier.getValue());
        return result == null ? error("identifier not found : %s", identifier.getValue()) : result;
    }
    public IObject visit(ReturnStatement statement) {
        IObject value = statement.getReturnValue().accept(this);
        return isError(value) ? value : new ReturnValueObject(value);
    }

    private boolean isTruthy(IObject condition) {
        if(condition == NULL) return  false;
        if(condition == TRUE) return true;
        if(condition == FALSE) return  false;

        return true;
    }

    private boolean isError(IObject object) {
        return object != null && object instanceof ErrorObject;
    }

}
