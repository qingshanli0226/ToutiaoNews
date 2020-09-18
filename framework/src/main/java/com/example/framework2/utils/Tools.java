package com.example.framework2.utils;

import android.util.Log;

public class Tools {
    public static String remove(String str) {
        String[] split = str.split("\\\\");
        StringBuilder stringBuffer = new StringBuilder();
        for (String s : split) {
            int i = s.indexOf("\"{");
            if (i == -1) {
                stringBuffer.append(s);
            } else {
                String replace = s.replace("\"{", "{");
                stringBuffer.append(replace);
            }
        }
        String s = stringBuffer.toString();
        String replace = null;
        int j = s.indexOf("}\"");
        if (j != -1) {
            replace = s.replace("}\"", "}");
        }
        return replace;
    }
}
