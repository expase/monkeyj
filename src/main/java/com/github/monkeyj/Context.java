package com.github.monkeyj;

import com.github.monkeyj.value.IObject;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, IObject> store = new HashMap<>();
    private Context parent;
    public Context(Context parent) {
        this.parent = parent;
    }
    public IObject get(String name) {

        if(!store.containsKey(name) && parent != null) {
            return parent.get(name);
        }

        return store.get(name);
    }

    public IObject set(String name,IObject value) {
        store.put(name, value);
        return value;
    }

    public Context extend() {
        return new Context(this);
    }

}
