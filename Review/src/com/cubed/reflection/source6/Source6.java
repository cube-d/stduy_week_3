package com.cubed.reflection.source6;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Source6 {
	public static void main(String[] args) {

		try {

			Class cls = Class.forName("com.cubed.reflection.data.TestObject");
			Constructor ctorlist[] = cls.getDeclaredConstructors();

			for (int i = 0; i < ctorlist.length; i++) {

				Constructor ct = ctorlist[i];

				System.out.println("name = " + ct.getName());
				System.out.println("class = " + ct.getDeclaringClass());

				Class pvec[] = ct.getParameterTypes();
				for (int j = 0; j < pvec.length; j++)
					System.out.println("param #" + j + " " + pvec[j]);

				Class evec[] = ct.getExceptionTypes();
				for (int j = 0; j < evec.length; j++)
					System.out.println("exc #" + j + " " + evec[j]);

				System.out.println("-----");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
