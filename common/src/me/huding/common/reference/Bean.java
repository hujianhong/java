package me.huding.common.reference;

public class Bean {
	String name;
	
	public Bean(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("finalizing ..." + name);
		super.finalize();
	}

	@Override
	public String toString() {
		return "Bean [name=" + name + "]";
	}
	
	
	
}
