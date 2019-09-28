package com.github.monkeyj.value;

public class ErrorObject implements IObject {
    private String errorMessage;

    public ErrorObject(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getType() {
        return "error";
    }

    public String toString() {
        return "Error(" + errorMessage + ")";
    }
    public static final ErrorObject error(String format,Object... args) {
        return new ErrorObject(String.format(format, args));
    }
}
