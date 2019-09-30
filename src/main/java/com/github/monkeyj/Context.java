package com.github.monkeyj;

import com.github.monkeyj.value.IObject;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, IObject> store = new HashMap<>();

    public IObject get(String name) {
        return store.get(name);
    }

    public IObject set(String name,IObject value) {
        store.put(name, value);
        return value;
    }
}
