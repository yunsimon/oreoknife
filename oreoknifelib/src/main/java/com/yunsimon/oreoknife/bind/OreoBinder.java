package com.yunsimon.oreoknife.bind;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yunsimon on 2018/6/6.
 */

public class OreoBinder {
    static final Map<Class<?>, IBinder<Object>> BINDERS = new LinkedHashMap<Class<?>, IBinder<Object>>();
    public static final String SUFFIX = "$$Oreo";

    public static void bind(Object obj) {
        Class<?> objClass = obj.getClass();
        IBinder classBinder = findBinderForClass(objClass);
        if (classBinder != null) {
            classBinder.bind(obj);
        }
    }

    private static IBinder findBinderForClass(Class<?> objClass) {
        IBinder iBinder = BINDERS.get(objClass);
        if(iBinder != null)
            return iBinder;
        try {
            String className = objClass.getName();
            Class<?> binderClass = Class.forName(className + SUFFIX);
            iBinder = (IBinder) binderClass.newInstance();
            BINDERS.put(objClass, iBinder);
        }catch (Exception e){
            e.printStackTrace();
        }
        return iBinder;
    }
}
