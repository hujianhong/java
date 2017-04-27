package me.huding.designpattern.decorator;

public abstract class Decorator implements Component {
	
	private Component component;
	
	public Decorator(Component component) {
		this.component = component;
	}

	@Override
	public void action() {
		this.component.action();
	}

}
