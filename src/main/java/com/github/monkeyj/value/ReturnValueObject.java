package com.github.monkeyj.value;

public class ReturnValueObject implements IObject {
    public ReturnValueObject(IObject value) {
        this.value = value;
    }

    private IObject value;

    public IObject getValue() {
        return value;
    }

    @Override
    public String getType() {
        return "return";
    }
}
