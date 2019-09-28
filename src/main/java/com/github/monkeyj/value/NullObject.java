package com.github.monkeyj.value;

public class NullObject implements IObject {
    public final static  NullObject NULL = new NullObject();
    public Object getValue() {
        return null;
    }

    public String toString() {
        return "NULL";
    }

    @Override
    public String getType() {
        return "null";
    }
}
