package com.github.monkeyj.value;

public class BooleanObject implements IObject {
    public final static BooleanObject TRUE = new BooleanObject(true);
    public final static BooleanObject FALSE = new BooleanObject(false);
    private boolean value;
    public BooleanObject(boolean value) {
        this.value = value;
    }
    public boolean getValue() {
        return value;
    }
    public String toString() {
        return "BooleanObject(" + value + ")";
    }

    public static final BooleanObject getBooleanObject(boolean nativeValue) {
        return nativeValue ? TRUE :FALSE;
    }

    @Override
    public String getType() {
        return "boolean";
    }
}
