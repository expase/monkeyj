package com.github.monkeyj;

import java.util.Arrays;
import java.util.List;

public class Utils {
    public static boolean hasItem(List items) {
        return items != null && items.size() > 0;
    }

    public static  String strJoin(List items,String separator) {
        StringBuilder buf = new StringBuilder();
        for(int i = 0;i < items.size(); i++) {
            buf.append(items.get(i));
            if(i < items.size() - 1) buf.append(separator);
        }

        return buf.toString();
    }

    public static void main(String[] args) {
        System.out.println(strJoin(Arrays.asList("1","2","3"), ", "));
    }
}
