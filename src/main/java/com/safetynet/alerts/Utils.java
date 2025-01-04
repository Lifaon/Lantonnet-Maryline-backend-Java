package com.safetynet.alerts;

import java.lang.reflect.Field;

public class Utils {

    public static <T, T1 extends T, T2 extends T> void copyFields(Class<T> cls, T1 src, T2 dst) {
        for (Field field : cls.getFields()) {
            try {
                field.set(dst, field.get(src));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static <T, T1 extends T, T2 extends T> void copyDeclaredFields(Class<T> cls, T1 src, T2 dst) {
        for (Field field : cls.getDeclaredFields()) {
            try {
                field.set(dst, field.get(src));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
