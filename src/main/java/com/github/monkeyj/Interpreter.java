package com.github.monkeyj;


import com.github.monkeyj.ast.*;
import com.github.monkeyj.value.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.monkeyj.value.BooleanObject.FALSE;
import static com.github.monkeyj.value.BooleanObject.TRUE;
import static com.github.monkeyj.value.Builtin.getBuiltin;
import static com.github.monkeyj.value.Builtin.isBuiltin;
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

    public IObject visit(StringLiteral si,Context context) {
        return new StringObject(si.getValue());
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

        if(result != null) {
            return result;
        }

        if(isBuiltin(identifier.getValue())) {
            return getBuiltin(identifier.getValue());
        }

        return error("identifier not found : %s", identifier.getValue());
    }

    public IObject visit(ReturnStatement statement, Context context) {
        IObject value = statement.getReturnValue().accept(this, context);
        return isError(value) ? value : new ReturnValueObject(value);
    }

    public IObject visit(CallExpression call, Context context) {
        IObject function = call.getFunction().accept(this, context);
        if (isError(function)) return function;

        List<IObject> argValues = evalExpressions(call.getArguments(), context);
        if(argValues.size() == 1 && isError(argValues.get(0))) return argValues.get(0);
        return applyCall(function, argValues);

    }

    private List<IObject> evalExpressions(List<Expression> expressions,Context context ) {
        List<IObject> result = new ArrayList<>();
        for (Expression expression : expressions) {
            IObject value = expression.accept(this, context);
            if (isError(value)) {
                return Arrays.asList(value);
            }
            result.add(value);
        }
        return result;
    }

    private IObject applyCall(IObject fn, List<IObject> args) {
        switch (fn.getType()) {
            case "function": {
                Function function = (Function) fn;
                Context functionContext = extendFunctionContext(function, args);

                IObject evaluated = function.getBody().accept(this, functionContext);

                return unwrapReturnValue(evaluated);
            }
            case "builtin": {
                Builtin builtin = (Builtin)fn;
                return builtin.execute(args);
            }
            default:
                return error("not a function: %s", fn.getType());
        }
    }

    private IObject unwrapReturnValue(IObject evaluated) {
        if(evaluated instanceof ReturnValueObject) {
            return ((ReturnValueObject)evaluated).getValue();
        }

        return evaluated;
    }

    private Context extendFunctionContext(Function function, List<IObject> args) {
        Context context = function.getContext().extend();
        int index = 0;
        for(Identifier param : function.getParameters()) {
            context.set(param.getValue(), args.get(index++));
        }

        return context;
    }

    @Override
    public IObject visit(ArrayLiteral array, Context context) {
        List<IObject> elements = evalExpressions(array.getElements(), context);
        if(elements.size() == 1 && isError(elements.get(0))) return elements.get(0);
        return new Array(elements);
    }

    @Override
    public IObject visit(IndexExpression expr, Context context) {
        IObject lft = expr.getLeft().accept(this, context);
        if(isError(lft)) return lft;
        IObject idx = expr.getIndex().accept(this, context);

        return evalIndexExpression(lft, idx);
    }

    private IObject evalIndexExpression(IObject lft,IObject idx) {
        if(lft instanceof Array && idx instanceof IntegerObject) {
            return evalArrayIndexExpression((Array)lft, (IntegerObject)idx);
        }

        return error("index operator not supported %s", lft.getType());
    }

    private IObject evalArrayIndexExpression(Array array,IntegerObject index) {
        int idx = index.getValue();
        if(idx < 0 || idx > array.getElements().size() - 1) return NULL;

        return array.getElements().get(idx);
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
