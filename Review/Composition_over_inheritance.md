# 1. 계승하는 대신 구현하라
## 1. 계승(상속)의 문제점
* 상위 클래스의 코드가 수정되면 하위 클래스의 코드도 수정해야하는 경우가 많음.
* 상위 클래스 변경에 의한 캡슐화 원칙 위반
    * 캡슐화 원칙 -> 정확히는 정보 은닉을 해침
    * 구체적인 것에 대한 의존도를 낮춤 -> 기능의 교체나 변경을 쉽게 만듬
    * 서로를 모르게 하는 정보 은닉이 객체를 유연하게 만듬
    * 상의 클래스와의 관계 및 의존성은 자식 객체의 캡슐화 (정보은닉)를 해침

---
* 구체적인 사례는 아래 코드를 참고

```
import java.util.Collection;
import java.util.HashSet;

class CubeDHashSet<E> extends HashSet<E> {
	
	private int addCount = 0;

	public CubeDHashSet() {
	}

	public CubeDHashSet(int initCap, float loadFactor) {
		super(initCap, loadFactor);
	}

	@Override
	public boolean add(E e) {
		addCount++;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		addCount += c.size();
		return super.addAll(c);
	}

	public int getAddCount() {
		return addCount;
	}
}
```

```
    // 메인 함수
    Set<String> s = new CubeDHashSet<>();
    s.addAll(Arrays.asList("A","B","C"));
    System.out.println(s.getAddCount());
```

* 메인함수에서 3이라는 숫자가 나와야 정상이지만 실제 출력시 6이라는 숫자가 나온다.
* addAll 에서 addCount 에 3을 더한 후 super.addAll(c) 이 호출 된다.
* 실제 super 클래스의 addAll 내부는 아래와 같다.
```
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c)
            if (add(e))
                modified = true;
        return modified;
    }
```
* 결국 add를 3번 더 호출하게 됨으로 결과는 6을 호출하게 된다.
___

## 2. 문제점을 피하는 방법

### 1. 계승하는 대신 구현하라
* 상위 클래스를 상속하는 대신에 상위 클래스를 참조하는 private 필드를 하나 만드는 것이다.
* 이러한 방법을 구성(composition)이라고 한다.
* 새로운 클래스의 메서드들은 기존 클래스에서 필요한 메서드를 호출하면 된다
* composition은 기존 클래스의 세부 구현과 상관 없기 때문에 기존 클래스가 수정되도 영향을 미치지 않는다.

```
public class CubeDSet<E> implements Set<E> {

	// Set 객체
	private final Set<E> s;

	public ForwardingSet(Set<E> s) {
		this.s = s;
	}

	public boolean add(E e) {
		return s.add(e);
	}
	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return s.addAll(c);
	}

    ...
}


public class ForwardingCubeDSet<E> extends CubeDSet<E> {
	private int addCount = 0;

	public ForwardingCubeDSet(Set<E> s) {
		super(s);
	}

	@Override
	public boolean add(E e) {
		addCount++;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		addCount += c.size();
		return super.addAll(c);
	}
}
```

* 다른 Set 객체를 포장하고 있기 때문이다 ForwardingCubeDSet 클래스는 계승 대신 구성을 사용하는 포장(wrapper) 클래스이다. 
* CubeDSet 클래스는 재사용 가능한 전달(forwarding) 클래스이다
* 위와 같이 구현하면 안정적이고 유연성도 좋다. 또한 Set 인터페이스가 있어 더 안정적이다.

### 2, 상속을 위한 설계와 문서를 갖추거나, 그럴 수 없다면 상속을 금지하라
* 재정의 가능 메서드(public이나 protected로 선언된 모든 메서드)에 대해서 내부적으로 어떻게 사용하는 지(내부 동작 원리) 반드시 문서에 남겨야 한다.
* 아래는 java.util.AbstractCollection remove 메서드의 주석이다.
```
/**
     * {@inheritDoc}
     *
     * <p>This implementation iterates over the collection looking for the
     * specified element.  If it finds the element, it removes the element
     * from the collection using the iterator's remove method.
     *
     * <p>Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if the iterator returned by this
     * collection's iterator method does not implement the <tt>remove</tt>
     * method and this collection contains the specified object.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     */
    public boolean remove(Object o) {
        Iterator<E> it = iterator();
        if (o==null) {
            while (it.hasNext()) {
                if (it.next()==null) {
                    it.remove();
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (o.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }
```
* 위 주석을 보면 iterate 메서드를 재정의하면 remove가 영향을 받는 다는 걸 알 수 있다.
* 반대로 첫 번째 예제에서 HashSet의 하위 클래스를 만드는데 add를 재정의하면 addAll에 무슨 일이 생길지 알 수 없다

### 3. 추상 클래스 대신 인터페이스를 사용하라
* 인터페이스는 다양한 구현이 가능한 자료형을 정의할 수 있고 포장 클래스를 통해 안전하면서도 강력한 기능 개선이 가능하다

### 4. 인터페이스보다 추상 클래스로 정의하면 좋은 점
* 추상 클래스가 더 발전시키기 쉽다
* API에 새로운 메서드를 추가하고 싶다면 추상 클래스는 기본적인 구현 메서드를 담으면 된다 하지만 인터페이스의 경우 메서드를 추가하면 해당 API를 구현한 다른 클라이언트 코드들은 오류를 발생시킬 것이다. ([java 8 default 메소드](https://github.com/jjunhoo/stduy_week_1/blob/master/2019-04-14%20%EC%A0%95%EB%A6%AC.md#1-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4-default-method) 참고)



