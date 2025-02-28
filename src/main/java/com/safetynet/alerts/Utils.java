package com.safetynet.alerts;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

public class Utils {

    private static <T1, T2> void _copyPublicField(Field f, T1 src, T2 dst) {
        try {
            f.set(dst, f.get(src));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static <T, T1, T2> void _copyPrivateField(Class<T> cls, Method set, T1 src, T2 dst) {
        try {
            String name = set.getName();
            if (name.startsWith("set")) {
                Method get = cls.getMethod(name.replaceFirst("set", "get"));
                set.invoke(dst, get.invoke(src));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static <T, T1 extends T, T2 extends T> void copyFields(Class<T> cls, T1 src, T2 dst) {
        for (Field field : cls.getFields()) {
            _copyPublicField(field, src, dst);
        }
        for (Method set : cls.getMethods()) {
            _copyPrivateField(cls, set, src, dst);
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
        for (Method set : cls.getDeclaredMethods()) {
            _copyPrivateField(cls, set, src, dst);
        }
    }

    public static <T> boolean equals(Class<T> cls, T a, T b) {
        for (Field field : cls.getFields()) {
            try {
                if (field.get(a) != field.get(b)) {
                    return false;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        for (Method get : cls.getMethods()) {
            try {
                if (!get.getName().startsWith("get") &&
                    !get.getName().startsWith("is"))
                    continue;
                if (get.invoke(a) != get.invoke(b)) {
                    return false;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public static <T> boolean equals(Class<T> cls, Iterable<T> a, Iterable<T> b) {
        Iterator<T> ia = a.iterator();
        Iterator<T> ib = b.iterator();
        while (ia.hasNext() && ib.hasNext()) {
            if (!equals(cls, ia.next(), ib.next())) {
                return false;
            }
        }
        return true;
    }

}
