package com.safetynet.alerts;

import java.lang.reflect.Field;

public class Utils {

    public static <T> void copyFields(Class<T> cls,  T src, T dst) {
        for (Field field : cls.getFields()) {
            try {
                field.set(dst, field.get(src));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
