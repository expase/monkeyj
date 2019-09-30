package com.github.monkeyj;


import com.github.monkeyj.ast.*;
import com.github.monkeyj.value.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.monkeyj.value.BooleanObject.FALSE;
import static com.github.monkeyj.value.BooleanObject.TRUE;
import static com.github.monkeyj.value.ErrorObject.error;
import static com.github.monkeyj.value.NullObject.NULL;

public class Interpreter implements NodeVisitor {


    public IObject visit(BooleanNode node, Context context) {
        return node.getValue() ? TRUE : FALSE;
    }

    public IObject visit(IntegerLiteral node, Context context) {
        return new IntegerObject(node.getValue());
    }

    public IObject visit(PrefixExpression prefixExpr, Context context) {
        IObject rightValue = prefixExpr.getRight().accept(this, context);

        return isError(rightValue) ? rightValue : visitPrefixExpression(prefixExpr.getOperator(), rightValue);
    }

    private IObject visitPrefixExpression(String operator, IObject rightValue) {
        Operator opr = Operator.getOperator(operator);
        return opr != null ? opr.visit(rightValue) : error("unknown operator: %s %s", operator, rightValue.getType());
    }

    public IObject visit(InfixExpression infixExpr, Context context) {
        IObject leftValue = infixExpr.getLeft().accept(this, context);
        IObject rightValue = infixExpr.getRight().accept(this, context);
        if (isError(leftValue)) return leftValue;
        if (isError(rightValue)) return rightValue;
        return visitInfixExpression(infixExpr.getOperator(), leftValue, rightValue);
    }

    private IObject visitInfixExpression(String operator, IObject leftValue, IObject rightValue) {
        if (!leftValue.getType().equals(rightValue.getType())) {
            return error("type mismatch: %s %s %s", leftValue.getType(), operator, rightValue.getType());
        }
        Operator opr = Operator.getOperator(operator);
        return opr != null ? opr.visit(leftValue, rightValue) : error("unknown operator: %s %s %s", leftValue.getType(), operator, rightValue.getType());
    }

    public IObject visit(IfExpression ifExpr, Context context) {
        IObject condition = ifExpr.getCondition().accept(this, context);
        if (isError(condition)) return condition;

        if (isTruthy(condition)) {
            return ifExpr.getConsequence().accept(this, context);
        } else if (ifExpr.getAlternative() != null) {
            return ifExpr.getAlternative().accept(this, context);
        } else {
            return NULL;
        }

    }

    public IObject visit(LetStatement statement, Context context) {
        IObject value = statement.getValue().accept(this, context);
        if (isError(value)) return value;
        context.set(statement.getName().getValue(), value);
        return NULL;
    }

    public IObject visit(FunctionLiteral fi, Context context) {
        return new Function(fi.getParameters(), context, fi.getBody());
    }

    public IObject visit(Identifier identifier, Context context) {
        IObject result = context.get(identifier.getValue());
        return result == null ? error("identifier not found : %s", identifier.getValue()) : result;
    }

    public IObject visit(ReturnStatement statement, Context context) {
        IObject value = statement.getReturnValue().accept(this, context);
        return isError(value) ? value : new ReturnValueObject(value);
    }

    public IObject visit(CallExpression call, Context context) {
        IObject function = call.getFunction().accept(this, context);
        if (isError(function)) return function;

        List<IObject> argValues = new ArrayList<>();
        for (Expression arg : call.getArguments()) {
            IObject argValue = arg.accept(this, context);
            if (isError(argValue)) {
                return argValue;
            }
            argValues.add(argValue);
        }

        return applyCall(function, argValues);

    }

    private IObject applyCall(IObject fn, List<IObject> args) {
        if (!(fn instanceof Function)) return error("not a function: %s", fn.getType());
        Function function = (Function) fn;
        Context functionContext = extendFunctionContext(function, args);

        IObject callValue = function.getBody().accept(this, functionContext);

        if(callValue instanceof ReturnValueObject) {
            return ((ReturnValueObject)callValue).getValue();
        }

        return callValue;

    }

    private Context extendFunctionContext(Function function, List<IObject> args) {
        Context context = function.getContext().extend();
        int index = 0;
        for(Identifier param : function.getParameters()) {
            context.set(param.getValue(), args.get(index++));
        }

        return context;
    }


    private boolean isTruthy(IObject condition) {
        if (condition == NULL) return false;
        if (condition == TRUE) return true;
        if (condition == FALSE) return false;

        return true;
    }

    private boolean isError(IObject object) {
        return object != null && object instanceof ErrorObject;
    }

}
