package me.huding.hunter.app;

public class Department {
	
	String name;
	
	String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Department [name=" + name + ", value=" + value + "]";
	}
	
	
}
