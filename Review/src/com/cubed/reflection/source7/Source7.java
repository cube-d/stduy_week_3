package com.cubed.reflection.source7;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Source7 {
	public static void main(String[] args) {

		try {

			Class cls = Class.forName("com.cubed.reflection.data.TestObject");
			Field fieldlist[] = cls.getDeclaredFields();

			for (int i = 0; i < fieldlist.length; i++) {
				Field fld = fieldlist[i];

				System.out.println("name = " + fld.getName());
				System.out.println("decl class = " + fld.getDeclaringClass());
				System.out.println("type = " + fld.getType());

				int mod = fld.getModifiers();
				System.out.println("modifiers = " + Modifier.toString(mod));
				System.out.println("-----");

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
