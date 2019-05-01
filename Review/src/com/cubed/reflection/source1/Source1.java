package com.cubed.reflection.source1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.cubed.reflection.data.TestObject;

public class Source1 {
	public static void main(String[] args) {

		Class c = TestObject.class;
		// Class c = Class.forName("클래스이름");

		Method[] m = c.getMethods();

		Field[] f = c.getFields();
		Constructor[] cs = c.getConstructors();
		Class[] inter = c.getInterfaces();
		Class superClass = c.getSuperclass();
	}
}
