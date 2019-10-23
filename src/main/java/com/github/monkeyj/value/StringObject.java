package com.github.monkeyj.value;

public class StringObject implements IObject {
    private String value;

    public StringObject(String value) {
        this.value = value;
    }
    @Override
    public String getType() {
        return "string";
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return "String(" + value + ")";
    }
}
