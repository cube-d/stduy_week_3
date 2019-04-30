# 1. Java Reflection
## 1. Java Reflection 정의
* 리플렉션이란 객체를 통해 클래스의 정보를 분석해 내는 프로그램 기법.
* 리플렉션은 구체적인 클래스 타입을 알지 못해도, 그 클래스의 메소드, 타입, 변수들을 접근할 수 있도록 해주는 자바 API
* 투영, 반사 라는 사전적인 의미를 지님
* [리플렉션 JAVA 문서](https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/package-summary.html)


## 2. 리플렉션 원리
* 자바에서는 모든 .class 파일 하나당 java.lang.Class 객체 하나씩 생성
* Class는 모든 .class들의 정보를 가지고 있으며 .class파일에 같이 저장된다
* .class들은 이 클래스를 최초로 사용하는 시점에서 동적으로 ClassLoader을 통해 JVM에 로드된다.
* 최초로 사용하는 시점이라면 해당 .class에서 static을 최초로 사용할때를 말한다. (생성자도 static 메소드이다. 그렇기 때문에 new를 하게 되면 로드된다고 보면 된다. 이를 동적 로딩이라 한다.) 
* .class의 정보와 Class객체는 JVM에 Run Time Data Area의 Method Area에 저장된다. 
* 이러한 정보들을 java.lang.reflect 에서 접근할 수 있게 도와 준다.

## 3. java.lang.reflect의 기능
* 이 패키지에서는 Field, Method, Constructor같은 class들이 있고, Constructor를 통해 새로운 객체를 생성할 수 있다.
* getter method와 setter method를 통해 Field의 값을 읽거나 수정할 수 있다. 
* invoke method를 통해 method를 호출한다.
* 그 외에도 parameter, return type, modifier 등 class의 관련된 모든 정보를 알아올 수 있고 조작할 수 있다. 


## 4. 사용법
### 1. 기본 사용 
```
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
    ...
}

```

### 2. 포함된 메소드 이름 출력

```
package com.cubed.reflection.source2;

import java.lang.reflect.Method;

import com.cubed.reflection.data.TestObject;

public class Source2 {
	public static void main(String[] args) {
		try {
			Class c = TestObject.class;
			Method m[] = c.getDeclaredMethods();
			for (int i = 0; i < m.length; i++)
				System.out.println(m[i].toString());
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}

```
* com.cubed.reflection.data.TestObject 의 메소드리스트를 출력한다.
* Class.forName 을 통해서 클래스를 로딩하고 getDeclaredMethods 을 통해서 클래스에서 정의한 메소드리스트를 얻는다. java.lang.reflect.Method  는 단일 메쏘드를 나타내는 클래스이다.


### 3. Reflection 을 사용하기 위한 단계
* Method 와 같은 클래스를 사용하기 위해서는 세가지 스텝을 밟아야 한다
```

package com.cubed.reflection.source3;

import java.lang.reflect.Method;


public class Source3 {
	public static void main(String[] args) {
		try {
			
			Class c;
			
			// Step 1 첫번째는 수정하기를 원하는 클래스의 java.lang.Class 객체를 얻어야 한다
			
			// case 1
			c = Class.forName("java.lang.String");
			
			// case 2
			c = int.class;
			
			// case 3 기본형의 경우 (Integer 와 같은) Wrapper 에 기정의된 TYPE을 사용한다. 
			c = Integer.TYPE;
			
			
			// Step 2 두번째 스텝은 getDeclaredMethods와 같은 메소드를 Call 해서, 클래스에 정의된 모든 메소드의 리스트를 얻는다.
			Method m[] = c.getDeclaredMethods();
			
			// Step 3 세번째 스텝은 정보 수정을 위해 Reflection API 를 이용한다.
			System.out.println(m[0].toString());
			
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
```

### 4. Reflection 사용한 type check

```
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

// 결과
false
true

```

* TestObject 의 클래스객체가 만들어진다. 그리고 class instance objects 가 TestObject 의 인스턴스인지 체크한다. 
* Integer(37) 은 아니지만, new TestObject() 는 True 이다.


### 5. 클래스 메소드 찾기
* reflection 의 가장 기초적인 쓰임은 클래스에서 정의한 메소드가 무엇인지 찾아내는 것
```
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

```

* getDeclaredMethods 을 통해서 메소드 리스트를 조회한다
* getDeclaredMethods 대신에 getMethods 를 사용하면 상속된 메소드 대한 정보를 얻을 수 있다.

```
// 결과

name  = toString
decl class = class com.cubed.reflection.data.TestObject
return type = class java.lang.String
-----
name  = getName
decl class = class com.cubed.reflection.data.TestObject
return type = class java.lang.String
-----
name  = setName
decl class = class com.cubed.reflection.data.TestObject
 param #0 class java.lang.String
return type = void
-----
name  = f1
decl class = class com.cubed.reflection.data.TestObject
 param #0 class java.lang.Object
 param #1 int
exc #0 class java.lang.NullPointerException
return type = int
-----
name  = getAge
decl class = class com.cubed.reflection.data.TestObject
return type = int
-----
name  = setAge
decl class = class com.cubed.reflection.data.TestObject
 param #0 int
return type = void
-----
```

### 6. 생성자 정보 찾기


```
package com.cubed.reflection.source6;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.cubed.reflection.data.TestObject;

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

// 결과
name = com.cubed.reflection.data.TestObject
class = class com.cubed.reflection.data.TestObject
-----
name = com.cubed.reflection.data.TestObject
class = class com.cubed.reflection.data.TestObject
param #0 int
param #1 double
-----

```
* 생성자는 리턴타입이 없기떄문에 표현 X


### 7,Class Fields 찾기
```
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
// 결과
name = name
decl class = class com.cubed.reflection.data.TestObject
type = class java.lang.String
modifiers = private
-----
name = age
decl class = class com.cubed.reflection.data.TestObject
type = int
modifiers = private
-----

```

* modifier는 'private int' 와 같은 필트멤버를 표현하기위한 reflection class 이다.
* modifier 는 숫자로 표현된다. Modifier.toString 은 'final' 앞의 'static' 과 같은 선언순서의 문자열표현을 리턴한다.


### 8. 이름으로 메소드 실행
```
package com.cubed.reflection.source8;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.cubed.reflection.data.TestObject;

public class Source8 {
	public static void main(String[] args) {

		try {

			Class cls = Class.forName("com.cubed.reflection.data.TestObject");

			Class partypes[] = new Class[2];

			partypes[0] = Integer.TYPE;
			partypes[1] = Integer.TYPE;

			Method test = cls.getMethod("add", partypes);

			TestObject testObject = new TestObject();

			Object arglist[] = new Object[2];

			arglist[0] = new Integer(37);
			arglist[1] = new Integer(47);

			Object retobj = test.invoke(testObject, arglist);

			Integer retval = (Integer) retobj;

			System.out.println(retval.intValue());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

// 결과
84
```
* 프로그램은 add 라는 메소드를 실행시
* 실행시까지 add 메소드를 실행할지 못한다고 가정해보자
* 메소드 이름은 실행시간에 알수있다. 
* getMethod 는 클래스에서 두개의 숫자 파라미터와 해당 이름을 가진 메소드를 찾아낸다.