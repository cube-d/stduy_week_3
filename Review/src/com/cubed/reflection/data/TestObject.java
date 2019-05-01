package com.cubed.reflection.data;

public class TestObject {
	private String name;
	private int age;
	public double d;
	
	public static final int i = 37;

	public TestObject() {
	}
	
	public TestObject(double d, int i) {
		
	}

	protected TestObject(int i, double d) {
		System.out.println("i : " + i + ", d : " + d);
	}

	private int f1(Object p, int x) throws NullPointerException {

		if (p == null)
			throw new NullPointerException();

		return x;
	}

	public int add(int a, int b) {
		return a + b;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "TestObject [name=" + name + ", age=" + age + "]";
	}

}
