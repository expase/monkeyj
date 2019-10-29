package com.github.monkeyj.value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.monkeyj.value.ErrorObject.error;

public class Builtin implements IObject {

    private BuiltinFunction fn;

    private Builtin(BuiltinFunction fn) {
        this.fn = fn;
    }

    public IObject execute(List<IObject> args) {
        return fn.func(args);
    }

    @Override
    public String getType() {
        return "builtin";
    }

    public static Map<String,Builtin> builtins = new HashMap<>();

    static {
        builtins.put("len", new Builtin( (args) -> {
            if(args.size() != 1) {
                return error("wrong number of arguments.got=%d,expected=1", args.size());
            }
            IObject arg = args.get(0);
            switch (arg.getType()) {
                case "string":
                    return new IntegerObject(((StringObject)arg).getValue().length());
                case "array":
                    return new IntegerObject(((Array)arg).getElements().size());
                default:
                    return error("argument to 'len' not supported, got %s", arg.getType());
            }
        }));
    }

    public static boolean isBuiltin(String name) {
        return builtins.containsKey(name);
    }

    public static Builtin getBuiltin(String name) {
        return builtins.get(name);
    }
}
