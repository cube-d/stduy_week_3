package com.cubed.reflection.source10;

import java.lang.reflect.Field;

import com.cubed.reflection.data.TestObject;

public class Source10 {
	public static void main(String[] args) {

		try {

			Class cls = Class.forName("com.cubed.reflection.data.TestObject");

			Field fld = cls.getField("d");

			TestObject testField = new TestObject();

			System.out.println("d = " + testField.d);

			fld.setDouble(testField, 12.34);

			System.out.println("d = " + testField.d);

		} catch (Throwable e) {

			System.err.println(e);

		}

	}
}
