package com.github.monkeyj.value;

import java.util.List;

@FunctionalInterface
public interface BuiltinFunction {
    public IObject func(List<IObject> args);
}
