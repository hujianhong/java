package me.huding.designpattern.templatemethod;

public abstract class AbstractClass {
	
	// 基本方法
	protected abstract void doAction();
	
	// 模版方法
	public void action(){
		this.doAction();
	}

}
