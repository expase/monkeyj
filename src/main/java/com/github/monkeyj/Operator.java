package com.github.monkeyj;

import com.github.monkeyj.value.*;

import java.util.HashMap;
import java.util.Map;

import static com.github.monkeyj.value.BooleanObject.*;
import static com.github.monkeyj.value.ErrorObject.error;

public abstract class Operator<T extends IObject> {
    public abstract IObject visit(T right);
    public abstract IObject visit(T left,T right);



    public static final class BangOperator extends Operator<IObject> {
        public IObject visit(IObject right) {
            if(right instanceof NullObject) return TRUE;
            if(!(right instanceof BooleanObject)) return FALSE;

            BooleanObject booleanObject = (BooleanObject)right;

            return booleanObject == TRUE ? FALSE : TRUE;
        }

        public IObject visit(IObject left, IObject right) {
            throw new UnsupportedOperationException();
        }
    }


    public static final class MinusOperator extends Operator<IObject> {
        public IObject visit(IObject right) {
            if(!(right instanceof IntegerObject)) return error("unknown operator: -%s", right.getType());
            return new IntegerObject(-((IntegerObject)right).getValue());
        }

        public IObject visit(IObject left, IObject right) {
            int leftValue = ((IntegerObject)left).getValue();
            int rightValue = ((IntegerObject)right).getValue();
            return new IntegerObject(leftValue - rightValue);
        }
    }

    public static final class AddOperator extends Operator<IObject> {
        public IObject visit(IObject right) {
            throw new UnsupportedOperationException();
        }

        public IObject visit(IObject left, IObject right) {
            if(left instanceof IntegerObject && right instanceof IntegerObject) {
                return new IntegerObject(((IntegerObject)left).getValue() + ((IntegerObject)right).getValue());
            } else if (left instanceof StringObject && right instanceof StringObject) {
                return new StringObject(((StringObject)left).getValue() + ((StringObject)right).getValue());
            } else {
                return error("type mismatch for %s - %s", left.getType(), right.getType());
            }

        }
    }

    public static final class MultiplyOperator extends Operator<IntegerObject> {
        public IObject visit(IntegerObject right) {
            throw new UnsupportedOperationException();
        }

        public IObject visit(IntegerObject left, IntegerObject right) {
            return new IntegerObject(left.getValue() * right.getValue());
        }
    }

    public static final class DivideOperator extends Operator<IntegerObject> {
        public IObject visit(IntegerObject right) {
            throw new UnsupportedOperationException();
        }

        public IObject visit(IntegerObject left, IntegerObject right) {
            return new IntegerObject(left.getValue() / right.getValue());
        }
    }

    public static final class LessOperator extends Operator<IntegerObject> {
        @Override
        public IObject visit(IntegerObject right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IObject visit(IntegerObject left, IntegerObject right) {
            return getBooleanObject(left.getValue() < right.getValue());
        }

    }

    public static final class GreatOperator extends Operator<IntegerObject> {
        @Override
        public IObject visit(IntegerObject right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IObject visit(IntegerObject left, IntegerObject right) {
            return getBooleanObject(left.getValue() > right.getValue());
        }

    }

    public static final class EqualOperator extends Operator {
        @Override
        public IObject visit(IObject right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IObject visit(IObject left, IObject right) {
            if(left instanceof IntegerObject && right instanceof IntegerObject) {
                return getBooleanObject(((IntegerObject)left).getValue() == ((IntegerObject)right).getValue());
            } else if (left instanceof BooleanObject && right instanceof BooleanObject) {
                return getBooleanObject(left != right);
            }
            return NullObject.NULL;
        }

    }

    public static final class NotEqualOperator extends Operator {
        @Override
        public IObject visit(IObject right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IObject visit(IObject left, IObject right) {
            if(left instanceof IntegerObject && right instanceof IntegerObject) {
                return getBooleanObject(((IntegerObject)left).getValue() != ((IntegerObject)right).getValue());
            } else if (left instanceof BooleanObject && right instanceof BooleanObject) {
                return getBooleanObject(left != right);
            }
            return NullObject.NULL;
        }

    }

    public static final class AndOperator extends Operator<BooleanObject> {
        @Override
        public IObject visit(BooleanObject right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IObject visit(BooleanObject left, BooleanObject right) {
            return getBooleanObject(left.getValue() && right.getValue());
        }
    }

    public static final class OrOperator extends Operator<BooleanObject> {
        @Override
        public IObject visit(BooleanObject right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IObject visit(BooleanObject left, BooleanObject right) {
            return getBooleanObject(left.getValue() || right.getValue());
        }
    }

    private final static Map<String,Operator> operators = new HashMap<String, Operator>();


    static {
        operators.put("!", new BangOperator());
        operators.put("+", new AddOperator());
        operators.put("-", new MinusOperator());
        operators.put("*", new MultiplyOperator());
        operators.put("/", new DivideOperator());

        operators.put("<", new LessOperator());
        operators.put(">", new GreatOperator());
        operators.put("==", new EqualOperator());
        operators.put("!=", new NotEqualOperator());

        operators.put("&&", new AndOperator());
        operators.put("||", new OrOperator());
    }

    public static Operator getOperator(String operator) {
        return   operators.get(operator);


    }


}
