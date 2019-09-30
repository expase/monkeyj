package com.github.monkeyj.value;

import com.github.monkeyj.Context;
import com.github.monkeyj.Utils;
import com.github.monkeyj.ast.BlockStatement;
import com.github.monkeyj.ast.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Function implements IObject {
    private List<Identifier> parameters = new ArrayList<>();
    private BlockStatement body;
    private Context context;

    public Function(List<Identifier> params, Context context,BlockStatement body) {
        this.parameters = params;
        this.body = body;
        this.context = context;
    }

    @Override
    public String getType() {
        return "function";
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("fn(");
        buf.append(Utils.strJoin(parameters, ", "));
        buf.append(") {\n");
        buf.append(body.toString());
        buf.append("\n}");
        return buf.toString();
    }

    public List<Identifier> getParameters() {
        return parameters;
    }

    public BlockStatement getBody() {
        return body;
    }

    public Context getContext() {
        return context;
    }
}
