package com.cubed.reflection.source4;

import java.lang.reflect.Method;

import com.cubed.reflection.data.TestObject;

public class Source4 {
	public static void main(String[] args) {

		try {
			
			Class cls = Class.forName("com.cubed.reflection.data.TestObject");
			
			boolean b1 = cls.isInstance(new Integer(37));
			System.out.println(b1);

			boolean b2 = cls.isInstance(new TestObject());
			System.out.println(b2);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
