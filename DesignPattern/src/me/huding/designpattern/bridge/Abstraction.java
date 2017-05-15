package me.huding.designpattern.bridge;

public abstract class Abstraction {
	private Implementor imp;

	public Abstraction(Implementor imp) {
		super();
		this.imp = imp;
	}
	
	public void action(){
		imp.action();
	}
}
