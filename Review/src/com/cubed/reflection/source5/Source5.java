package com.cubed.reflection.source5;

import java.lang.reflect.Method;

import com.cubed.reflection.data.TestObject;

public class Source5 {
	public static void main(String[] args) {

		try {
			
			Class cls = Class.forName("com.cubed.reflection.data.TestObject");			
			Method methlist[] = cls.getDeclaredMethods();
			
			for (int i = 0; i < methlist.length; i++) {
				Method m = methlist[i];
				
				System.out.println("name  = " + m.getName());
				System.out.println("decl class = " + m.getDeclaringClass());

				Class pvec[] = m.getParameterTypes();

				for (int j = 0; j < pvec.length; j++)
					System.out.println(" param #" + j + " " + pvec[j]);

				Class evec[] = m.getExceptionTypes();

				for (int j = 0; j < evec.length; j++)
					System.out.println("exc #" + j + " " + evec[j]);
				
				System.out.println("return type = " + m.getReturnType());
				System.out.println("-----");

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
