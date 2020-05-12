package be.belfius.games.domain;

public class Out3Objects <T,V, W> {
	private T obj1;
	private V obj2;
	private W obj3;
	
	public Out3Objects() {
		super();
	}
	
	public Out3Objects(T obj1, V obj2, W obj3) {
		this.obj1=obj1;
		this.obj2=obj2;
		this.obj3=obj3;
	}


	public T getObj1() {
		return obj1;
	}


	public void setObj1(T obj1) {
		this.obj1 = obj1;
	}


	public V getObj2() {
		return obj2;
	}


	public void setObj2(V obj2) {
		this.obj2 = obj2;
	}
	
	public W getObj3() {
		return obj3;
	}


	public void setObj3(W obj3) {
		this.obj3 = obj3;
	}


}
