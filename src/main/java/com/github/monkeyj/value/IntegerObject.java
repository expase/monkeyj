package com.github.monkeyj.value;

public class IntegerObject implements IObject {
    private int value;

    public IntegerObject(int value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public String toString() {
        return "IntegerObject(" + value+")";
    }
}
