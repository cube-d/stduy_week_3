package com.cubed.reflection.source3;

import java.lang.reflect.Method;

import com.cubed.reflection.data.TestObject;

public class Source3 {
	public static void main(String[] args) {
		try {

			Class c;

			// Step 1

			// case 1
			c = Class.forName("java.lang.String");

			// case 2
			c = int.class;

			// case 3 기본형의 경우 (Integer 와 같은) Wrapper 에 기정의된 TYPE을 사용한다.
			c = Integer.TYPE;

			// Step 2
			Method m[] = c.getDeclaredMethods();

			// Step 3
			System.out.println(m[0].toString());

		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
