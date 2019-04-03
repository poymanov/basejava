package com.basejava;

import com.basejava.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Resume.class.getDeclaredMethod("toString");
        System.out.println(method.invoke(new Resume("Test Name")));
    }
}
