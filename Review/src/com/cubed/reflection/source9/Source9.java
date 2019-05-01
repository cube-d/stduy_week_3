package com.cubed.reflection.source9;

import java.lang.reflect.Constructor;

public class Source9 {
	public static void main(String[] args) {

		try {

			Class cls = Class.forName("com.cubed.reflection.data.TestObject");

			Class partypes[] = new Class[2];

			partypes[0] = Double.TYPE;
			partypes[1] = Integer.TYPE;

			Constructor ct = cls.getConstructor(partypes);

			Object arglist[] = new Object[2];

			arglist[0] = new Double(23);
			arglist[1] = new Integer(37);

			Object retobj = ct.newInstance(arglist);
			
			System.out.println(retobj);

		} catch (Throwable e) {

			System.err.println(e);

		}

	}
}
