package com.github.monkeyj.value;

import com.github.monkeyj.Utils;

import java.util.ArrayList;
import java.util.List;

public class Array implements IObject {
    private List<IObject> elements = new ArrayList<>();

    public Array(List<IObject> elements) {
        this.elements = elements;
    }

    @Override
    public String getType() {
        return "array";
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[");
        buf.append(Utils.strJoin(elements, ", "));
        buf.append("]");

        return buf.toString();
    }

    public List<IObject> getElements() {
        return elements;
    }
}
