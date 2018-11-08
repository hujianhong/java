package me.huding.designpattern.decorator;

public class ConcreteDecorator extends Decorator {

	public ConcreteDecorator(Component component) {
		super(component);
	}
	
	
	private void method(){
		// TODO something
	}


	@Override
	public void action() {
		this.method();
		super.action();
	}
	
	

}
